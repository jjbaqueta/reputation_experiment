package entities.model.sellers;

import java.util.List;

import entities.model.Offer;
import entities.model.Product;
import entities.model.behaviors.Behavior;

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
//	@Override
//	public Offer computeContractConditions(Offer agreedOffer) 
//	{	
//		double realPrice = agreedOffer.getProduct().getPrice() * (1 + priceBehavior.getbehaviorValueFor(agreedOffer.getCnpid()));
//		double realQuality = agreedOffer.getProduct().getQuality() * (1 - qualityBehavior.getbehaviorValueFor(agreedOffer.getCnpid()));
//		double realDeliveryTime = (int) (agreedOffer.getProduct().getDeliveryTime() * (1 + deliveryBehavior.getbehaviorValueFor(agreedOffer.getCnpid())));
//		
//		return (Offer) new Offer(agreedOffer.getProduct().getName(), realPrice, realQuality, (int) realDeliveryTime, agreedOffer.getSeller());
//	}
	
	@Override
	public Offer computeContractConditions(Offer agreedOffer) 
	{	
		Behavior pBehavior = agreedOffer.getProduct().getSalesBehaviorPrice();
		Behavior qBehavior = agreedOffer.getProduct().getSalesBehaviorQuality();
		Behavior dBehavior = agreedOffer.getProduct().getSalesBehaviorDelivery();
		
		double realPrice = agreedOffer.getProduct().getPrice() * (1 + pBehavior.getbehaviorValueFor(agreedOffer.getCnpid()));
		double realQuality = agreedOffer.getProduct().getQuality() * (1 - qBehavior.getbehaviorValueFor(agreedOffer.getCnpid()));
		double realDeliveryTime = (int) (agreedOffer.getProduct().getDeliveryTime() * (1 + dBehavior.getbehaviorValueFor(agreedOffer.getCnpid())));
		
		return (Offer) new Offer(agreedOffer.getProduct().getName(), realPrice, realQuality, (int) realDeliveryTime, agreedOffer.getSeller());
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