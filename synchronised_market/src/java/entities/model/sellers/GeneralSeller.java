package entities.model.sellers;

import java.util.List;

import entities.model.Offer;
import entities.model.Product;
import entities.model.behaviors.Behavior;
import jason.asSyntax.Literal;

/*
 * This Class implements a GeneralSeller
 * This kind of seller has his shopping profile defined from a specific behavior
 */
public class GeneralSeller extends Seller
{
	private Behavior priceBehavior;
	private Behavior qualityBehavior;
	private Behavior deliveryBehavior;
	
	// Constructor
	public GeneralSeller(String name, int amountOfItems, double priceMargin, double qualityMargin, double deliveryMargin, List<Product> availableProducts) 
	{
		super(name, amountOfItems, priceMargin, qualityMargin, deliveryMargin, availableProducts);
	}

	/*
	 * This method computes the probability of seller changes the conditions from initial contract
	 * In this case, there is a probability of 80% of seller changes all of contract's conditions
	 * @param agreedOffer initial contract's terms defined between a buyer and the seller during the proposal phase
	 * @return a new contract in literal format with the real delivery conditions, which may or not be according to initial contract
	 */
	@Override
	public Literal computeContractConditions(Offer agreedOffer) 
	{	
		double realPrice = agreedOffer.getPrice() * (1 + priceBehavior.getbehaviorValueFor(agreedOffer.getCnpid()));
		double realQuality = agreedOffer.getQuality() * (1 - qualityBehavior.getbehaviorValueFor(agreedOffer.getCnpid()));
		double realDeliveryTime = (int) (agreedOffer.getDeliveryTime() * (1 + deliveryBehavior.getbehaviorValueFor(agreedOffer.getCnpid())));
		
		return Literal.parseLiteral("p(" + agreedOffer.getProduct() + "," + realPrice + "," + realQuality + "," + realDeliveryTime + ")");
	}

	public void setPriceBehavior(Behavior priceBehavior) 
	{
		this.priceBehavior = priceBehavior;
	}

	public void setQualityBehavior(Behavior qualityBehavior) 
	{
		this.qualityBehavior = qualityBehavior;
	}

	public void setDeliveryBehavior(Behavior deliveryBehavior) 
	{
		this.deliveryBehavior = deliveryBehavior;
	}
}