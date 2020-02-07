package actions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import enums.CriteriaType;
import environments.Market;
import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import reputationModels.Reputation;

public class sellerSaveReputation extends DefaultInternalAction
{
	private static final long serialVersionUID = 1L;

	/*
	 * This method may change the initial conditions of a contract defined between a seller and a buyer according to seller's type
	 * The contract informations are passed from the array args:
	 * - args[0]: reputation profile
	 * - args[1]: R
	 */	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{	
		try
		{	
			// Parsing the received reputation
			Reputation rep = Reputation.parseReputation(args[0].toString());
			writeRepStatus(rep);
			
			//Returns the result as Term
			return un.unifies(Literal.parseLiteral("none"), args[1]);		
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
	
	/*
	 * This method writes in the output file 'sales.txt' the current sale state of each seller
	 * @param seller represents the seller who will be write his sale state
	 * @param time current time, it is used to sort the writing events
	 */
	public void writeRepStatus(Reputation rep)
	{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Market.fileRep, true));
			writer.append(rep.getAgent().getName() + ";" + 
						  rep.getTime() + ";" + 
						  rep.getReputationRatings().get(CriteriaType.PRICE.getValue()) + ";" +
					      rep.getReputationRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
					      rep.getReputationRatings().get(CriteriaType.DELIVERY.getValue()) + ";" +
					      rep.getReliabilityRatings().get(CriteriaType.PRICE.getValue()) + ";" +
					      rep.getReliabilityRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
					      rep.getReliabilityRatings().get(CriteriaType.DELIVERY.getValue()) + "\n");			     
		    writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}