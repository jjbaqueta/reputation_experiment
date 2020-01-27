package actions;

import java.util.List;

import entities.model.Impression;
import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import reputationModels.ReGret;

public class buyerCalculateReputation extends DefaultInternalAction{

	private static final long serialVersionUID = 1L;
	
	/*
	 * This method computes the reputation of a given seller from a list of impressions about him.
	 * The arguments are defined as follow:
	 * - args[0]: List of impressions, literal format: imp(Buyer,Agent,Time,[Price,Quality,Delivery])
	 * - args[1]: Return Agent's reputation, format rep(Agent,R_price,R_quality,R_delivery,L_price,L_quality,L_delivery); R: reputation, L: reliability
	 */	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{
		try
		{				
			// Translating impressions in Literal format to an Object list 
			List<Impression> impressions = Impression.parseImpressionList(args[0].toString());			

			// Getting current time.
			long currentTime = System.currentTimeMillis();
			
			// Using ReGret model.
			double[] reputations = ReGret.computeSubjectiveReputation(currentTime, impressions);					
			double[] reliabilities = ReGret.computeReliability(reputations, currentTime, impressions);
			
			return un.unifies(Literal.parseLiteral("rep("+impressions.get(0).getAppraised().getName()+
					","+reputations[0]+","+reputations[1]+","+reputations[2]+
					","+reliabilities[0]+","+reliabilities[1]+","+reliabilities[2]+")"), args[1]);	
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
