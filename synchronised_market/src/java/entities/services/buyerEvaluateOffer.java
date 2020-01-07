package entities.services;

import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

public class buyerEvaluateOffer extends DefaultInternalAction{

	private static final long serialVersionUID = 1L;
	
	/*
	 * This method is used by buyer to decide if either accept or reject an offer
	 * An offer is composed by:
	 * - args[0]: seller's name
	 * - args[1]: product's name
	 * - args[2]: product's price
	 * - args[4]: anwser for offer
	 */
	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{
		try
		{
			//gets the arguments as Terms		
			StringTerm sellerName = (StringTerm) args[0];
			StringTerm productName = (StringTerm) args[1];
			NumberTerm price = (NumberTerm) args[2];
			
			//the decision taken is based on price value
			if(price.solve() > 500)
			{
				return un.unifies(new StringTermImpl("reject"), args[3]); 
			}
			else
			{
				System.out.println("offer accepted - from: " + sellerName +", by product: "+ productName);
				return un.unifies(new StringTermImpl("accept"), args[3]);
			}		
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
}