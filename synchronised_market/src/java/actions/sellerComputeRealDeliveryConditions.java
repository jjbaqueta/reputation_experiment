package actions;

import entities.model.Offer;
import entities.model.Seller;
import entities.services.MarketFacade;
import environments.Market;
import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public class sellerComputeRealDeliveryConditions extends DefaultInternalAction{

	private static final long serialVersionUID = 1L;

	/*
	 * This method may generate changes on conditions of a contract according to seller's type
	 * The contract's data are passed from array args:
	 * - args[0]: seller's name
	 * - args[1]: offer used to define an agreement between the seller and buyer
	 * - args[2]: offer adjusted according to conditions defined by seller
	 */	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{	
		try
		{	
			// Get the index of the seller
			int index = MarketFacade.getSellerIdFrom(args[0].toString());
			Seller seller = Market.sellers[index];
			
			// Parsing the offer received
			String[] attributes = args[1].toString().split("p\\(|\\)")[1].split("\\,");
			
			String pName = attributes[0];
			double pPrice = Double.parseDouble(attributes[1]);
			double pQuality = Double.parseDouble(attributes[2]);
			int pDelivery = Integer.parseInt(attributes[3]);
			
			Offer offer = new Offer(pName, pPrice, pQuality, pDelivery, seller.getName());
						
			//Returns the result as Term
			return un.unifies(seller.computeRealDeliveryConditions(offer), args[2]);		
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new JasonException("The internal action must receive one argument!");
		}
		catch(Exception e)
		{
			throw new JasonException(e.getMessage());
		}
	}
}