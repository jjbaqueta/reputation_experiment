package actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import entities.model.Buyer;
import entities.model.Offer;
import entities.services.MarketFacade;
import environments.Market;
import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Atom;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Term;
import reputationModels.Reputation;

/*
 * This class implements an action of an agent of type buyer
 */
public class buyerFindBestOffer extends DefaultInternalAction
{
	private static final long serialVersionUID = 1L;
	
	/*
	 * This method is used by buyer to decide what is the best offer among all offers sent to him 
	 * From the reputation and preferences informations the buyer is able to decide which offer accepts or rejects
	 * The informations about offers are passed from the array args:
	 * - args[0]: buyer's name
	 * - args[1]: list of offers (every offers received by buyer)
	 * - args[2]: list of reputations (it may contain the reputation of each seller that sent a proposal)
	 * - args[3]: Return the name of seller who received the best evaluation score, considering his reputation and the buyer's preferences 
	 */
	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{
		try
		{	
			List<Offer> offers = new ArrayList<Offer>();
			List<Reputation> reputations = new ArrayList<Reputation>();
			
			// Get the index from buyer
			int index = MarketFacade.getBuyerIdFrom(args[0].toString());
			Buyer buyer = Market.buyers[index];
			
			// Parsing the list of offers : args[1]
			ListTerm offerTermList = (ListTerm) args[1];
			
			if(!offerTermList.isEmpty())
			{				
				for(Term t : offerTermList)
					offers.add(Offer.parseOffer(t.toString()));
			}
			else	
				return un.unifies(new Atom("none"), args[3]);
			
			// Parsing the list of reputations : args[2]
			ListTerm repTermList = (ListTerm) args[2];
			
			if(!repTermList.isEmpty())
			{
				for(Term t : repTermList)
					reputations.add(Reputation.parseReputation(t.toString()));
			}

			// Computing who is the best seller and return him
			String sellerName = computeBestOfferByRelevance(offers, reputations, buyer.getPreferenceByPrice(), buyer.getPreferenceByQuality(), buyer.getPreferenceByDelivery()).getSeller();

			if(sellerName == null)
				return un.unifies(new Atom("none"), args[3]);
			else
				return un.unifies(new Atom(sellerName), args[3]);	
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new JasonException("The internal action must receive four arguments!");
		}
		catch (ClassCastException e) {
			throw new JasonException("received arguments are out of format");
		}
		catch(Exception e)
		{
			throw new JasonException(e.getMessage());
		}
	}
	
	/*
	 * This method computes the best offer received by buyer
	 * The computation is done from a cost function which is adjusted considering the trade-off among the buyer's preferences
	 * @param offers List containing every offers received
	 * @param priceWeight Relevance of price factor on the best offer computation, 0.0 minimum, 1.0 maximum 
	 * @param qualityWeight Relevance of quality factor on the best offer computation, 0.0 minimum, 1.0 maximum
	 * @param timeWeight Relevance of delivery factor on the best offer computation, 0.0 minimum, 1.0 maximum
	 * @return name of the best seller, if exist the best offer, otherwise returns null
	 */
	private Offer computeBestOfferByRelevance(List<Offer> offers, List<Reputation> reputations, double priceWeight, double qualityWeight, double timeWeight)
	{	
		// Removing agents with reputation lower than 0.5
		for(Reputation rep : reputations)
		{
			if(!rep.checkReputation(priceWeight, qualityWeight, timeWeight))
			{
				offers.removeIf(offer -> offer.getSeller().equals(rep.getAgent().getName()));
			}
		}
		
		// Case it there is not offers (all seller were removed due to their low reputation)
		if(offers.isEmpty())
			return null;
		
		// Getting optimal values for each criteria
		double minPrice = Collections.min(offers, (offer1, offer2) -> offer1.getPrice().compareTo(offer2.getPrice())).getPrice();
		double maxQuality = Collections.max(offers, (offer1, offer2) -> offer1.getQuality().compareTo(offer2.getQuality())).getQuality();
		int minTime = Collections.min(offers, (offer1, offer2) -> offer1.getDeliveryTime().compareTo(offer2.getDeliveryTime())).getDeliveryTime();
		
		// Rating array
		double[] ratings = new double[offers.size()]; 
		Arrays.fill(ratings, 0);
		
		// Normalization and computation of ratings of offers (from weighted average)
		for(int i = 0; i < offers.size(); i++)
		{
			ratings[i] += (offers.get(i).getPrice() / minPrice) * (priceWeight);
			ratings[i] -= (offers.get(i).getQuality() / maxQuality) * (qualityWeight);
			ratings[i] += ((double) offers.get(i).getDeliveryTime() / minTime) * (timeWeight);
			ratings[i] /= 3;
		}
		
		// Finding the best offer
		int minIdx = IntStream.range(0, ratings.length)
				.reduce((i, j) -> ratings[i] > ratings[j] ? j : i)
				.getAsInt();
		
		return offers.get(minIdx);
	}
}