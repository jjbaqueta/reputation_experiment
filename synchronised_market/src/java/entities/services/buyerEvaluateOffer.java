package entities.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import entities.model.Offer;
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
	 * An offer is composed by:
	 * - Arcs[0]: seller's name
	 * - args[1]: product's name
	 * - args[2]: product's price
	 * - args[4]: anwser for offer
	 */
	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{
		try
		{		
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
			
			String seller = computeBestOfferByRelevance(offers, 0.0, 1.0, 0.5).getSeller();
			
			return un.unifies(new Atom(seller), args[1]);
			
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
	
	private Offer computeBestOfferByRelevance(List<Offer> offers, double priceWeight, double qualityWeight, double timeWeight)
	{	
		// Getting optimal values for each criteria
		double minPrice = Collections.min(offers, (offer1, offer2) -> offer1.getPrice().compareTo(offer2.getPrice())).getPrice();
		double maxQuality = Collections.max(offers, (offer1, offer2) -> offer1.getQuality().compareTo(offer2.getQuality())).getQuality();
		int minTime = Collections.min(offers, (offer1, offer2) -> offer1.getDeliveryTime().compareTo(offer2.getDeliveryTime())).getDeliveryTime();
		
		// rating array
		double[] ratings = new double[offers.size()];
		Arrays.fill(ratings, 0);
		
		// Normalization and computation of evaluation value	
		for(int i = 0; i < offers.size(); i++)
		{
			ratings[i] += (offers.get(i).getPrice() / minPrice) * (priceWeight);
			ratings[i] -= (offers.get(i).getQuality() / maxQuality) * (qualityWeight);
			ratings[i] += ((double) offers.get(i).getDeliveryTime() / minTime) * (timeWeight);
			ratings[i] /= 3;
		}
		
		// Show evaluation result ************
				int k = 0;
				for(double r : ratings)
				{
					System.out.println("rating: " + r + " offer: " + offers.get(k++));
				}
		// ***********************************
		
		// Defining the best offer
		int minIdx = IntStream.range(0, ratings.length)
				.reduce((i, j) -> ratings[i] > ratings[j] ? j : i)
				.getAsInt();
		
		return offers.get(minIdx);
	}
}