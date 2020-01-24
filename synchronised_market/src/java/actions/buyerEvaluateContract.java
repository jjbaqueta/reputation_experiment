package actions;

import entities.model.Buyer;
import entities.model.Impression;
import entities.model.Offer;
import entities.model.Seller;
import entities.services.MarketFacade;
import enums.CriteriaType;
import environments.Market;
import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public class buyerEvaluateContract extends DefaultInternalAction{

	private static final long serialVersionUID = 1L;

	/*
	 * This method may generate changes on conditions of a contract according to seller's type
	 * The contract's data are passed from array args:
	 * - args[0]: buyer's name
	 * - args[1]: seller's name
	 * - args[2]: original offer (proposal)
	 * - args[3]: current offer (contract)
	 * - args[4]: return the rating about the seller's behaviour 
	 */	
	@Override
	public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws JasonException 
	{		
		try
		{	
			// Get the index from buyer
			int index = MarketFacade.getBuyerIdFrom(args[0].toString());
			Buyer buyer = Market.buyers[index];
			
			// Get the index from seller
			index = MarketFacade.getSellerIdFrom(args[1].toString());
			Seller seller = Market.sellers[index];
			
			Offer proposal = Offer.parseProposal(args[2].toString(), seller.getName());
			Offer contract = Offer.parseProposal(args[3].toString(), seller.getName());
					
			return un.unifies(evaluateSeller(proposal, contract, buyer, seller).getImpressionAsLiteral(), args[4]);		
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
	 * This method computes a reputation value for each criterion defined on the reputation model
	 * @param proposal Initial offer defined by seller
	 * @param contract Real offer considering respective penalties (like delivery delay and quality reductions)
	 * @param buyer appraiser
	 * @param seller evaluated
	 * @return A impression from buyer with respect to seller (rating)
	 */
	private Impression evaluateSeller(Offer proposal, Offer contract, Buyer buyer, Seller seller)
	{
		double priceDiscrepancy = contract.getPrice() / proposal.getPrice();
		double qualityDiscrepancy = proposal.getQuality() / contract.getQuality();
		double deliveryDiscrepancy = (double) contract.getDeliveryTime() / proposal.getDeliveryTime();
		
		Impression impression = new Impression(buyer, seller, System.currentTimeMillis());
		
		impression.setRating(CriteriaType.PRICE.getValue(), getScore(priceDiscrepancy));
		impression.setRating(CriteriaType.QUALITY.getValue(), getScore(qualityDiscrepancy));
		impression.setRating(CriteriaType.DELIVERY.getValue(), getScore(deliveryDiscrepancy));
		
		return impression;
	}
	
	/*
	 * This method defines the score for each evaluation criterion
	 * The minimum score is 0 and the maximum score is 10.
	 * When variation is 1, it means that the contract hasn't been change, at least for the evaluated criterion.
	 * On the other hand, if variation is larger or equals to 2, it means the evaluated criterion has its value at least doubled.
	 * @param variation Normalized value that represents the discrepancy between the proposal and the contract
	 * @return rating value with respect to an evaluation criterion
	 */
	private double getScore (double variation)
	{	
		if(variation <= 1)
			return 10.0;					//maximum score
		else if(variation >= 2)
			return 0;						//minimum score
		else
			return -10 * (variation - 2);	//intermediate score (based on line equation)
	}
}
