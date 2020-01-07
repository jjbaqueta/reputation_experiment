package entities.services;

import java.util.Random;

import enums.Product;
import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

public class sellerDefineOfferPrice extends DefaultInternalAction{

	private static final long serialVersionUID = 1L;

	/*
	 * This method generate a random price for a specific product based on a given range (min and max)
	 * An offer is composed by:
	 * - args[0]: product's name
	 * - args[1]: computed price (return)
	 */
	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{
		try
		{
			//gets the arguments as Terms		
			StringTerm productName = (StringTerm) args[0];
			Random r = new Random();
			
			double basePrice = Product.valueOf(productName.toString().toUpperCase()).getPrice();
			double price = basePrice + ((basePrice * 1.4) - basePrice) * r.nextDouble();
						
			//return the result as Term
			return un.unifies(new NumberTermImpl(price), args[1]);		
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