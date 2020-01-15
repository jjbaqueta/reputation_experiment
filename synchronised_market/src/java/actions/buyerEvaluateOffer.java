package actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import entities.model.Buyer;
import entities.model.Offer;
import environments.Market;
import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Atom;
import jason.asSyntax.Term;

public class buyerEvaluateOffer extends DefaultInternalAction{

	private static final long serialVersionUID = 1L;
	
	/*
	 * This method is used by buyer to decide if either accept or reject offers
	 * The data about offers are passed from array args:
	 * - args[0]: Contains a list with all offers received by buyer
	 * - args[1]: Contains the buyer's name
	 * - args[2]: Return the name of seller that presented the best offer 
	 */
	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{
		try
		{	
			// Get the index of buyer
			int index = 0;
			
			if(!args[1].toString().equals("buyer"))
				index = Integer.parseInt(args[1].toString().split("buyer")[1]);

			Buyer buyer = Market.buyers[index];
			
			// Parsing the list of arguments : args[0]
			String[] str_offers = args[0].toString().split("\\[offer\\(p\\(|\\),offer\\(p\\(|\\)\\]");
			
			String[] attributes;			
			List<Offer> offers = new ArrayList<Offer>();		
			
			for(int i = 1; i < str_offers.length; i++)
		    {
				attributes = str_offers[i].split("\\)*\\,");
				offers.add(new Offer(attributes[0],						// Product's name
									 Double.parseDouble(attributes[1]), // Product's price
									 Double.parseDouble(attributes[2]), // Product's quality
									 Integer.parseInt(attributes[3]), 	// Delivery time
									 attributes[4])						// Seller's name
			);}
			
			// Computing who is the best seller and return him
			String seller = computeBestOfferByRelevance(offers, buyer.getPreferenceByPrice(), buyer.getPreferenceByQuality(), buyer.getPreferenceByDelivery()).getSeller();
			
			System.out.println("name: "+ args[1] + "," + buyer.getPreferenceByPrice() + "," + buyer.getPreferenceByQuality() + "," + buyer.getPreferenceByDelivery());
			
			return un.unifies(new Atom(seller), args[2]);	
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
	 * The computations is done from a cost function that it is adjusted considering the trade-off among the evaluation criterias
	 * @param offers List of offers received
	 * @param priceWeight Relevance factor of price on best offer computation, 0.0 minimum, 1.0 maximum 
	 * @param qualityWeight Relevance factor of quality on best offer computation, 0.0 minimum, 1.0 maximum
	 * @param timeWeight Relevance factor of delivery time on best offer computation, 0.0 minimum, 1.0 maximum
	 * @return best seller's name
	 */
	private Offer computeBestOfferByRelevance(List<Offer> offers, double priceWeight, double qualityWeight, double timeWeight)
	{	
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
		
		// Defining the best offer
		int minIdx = IntStream.range(0, ratings.length)
				.reduce((i, j) -> ratings[i] > ratings[j] ? j : i)
				.getAsInt();
		
		return offers.get(minIdx);
	}
}