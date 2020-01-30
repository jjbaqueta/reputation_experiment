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

/*
 * This class implements an action of an agent of type seller
 */
public class sellerDefineDeliveryConditions extends DefaultInternalAction
{
	private static final long serialVersionUID = 1L;

	/*
	 * This method may change the initial conditions of a contract defined between a seller and a buyer according to seller's type
	 * The contract informations are passed from the array args:
	 * - args[0]: seller's name
	 * - args[1]: proposal used to define an agreement between the seller and buyer (initial contract)
	 * - args[2]: new contract which may be adjusted according seller's type (return)
	 */	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{	
		try
		{	
			// Get the index from seller
			int index = MarketFacade.getSellerIdFrom(args[0].toString());
			Seller seller = Market.sellers[index];
			
			// Parsing the offer received
			Offer offer = Offer.parseProposal(args[1].toString(), seller);
						
			//Returns the result as Term
			return un.unifies(seller.computeContractConditions(offer), args[2]);		
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