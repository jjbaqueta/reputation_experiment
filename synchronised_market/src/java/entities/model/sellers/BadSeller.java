package entities.model.sellers;

import java.util.List;
import java.util.Random;

import entities.model.Offer;
import entities.model.Product;
import entities.services.BehaviorFactory;
import enums.BehaviorPattern;
import environments.Market;

/*
 * This Class implements a BadSeller
 * This kind of seller may change ALL conditions from initial contract that it was defined between a buyer and the seller
 * See contract conditions implementation in: @see #computeContractConditions(Offer agreedOffer) 
 */
public class BadSeller extends Seller
{
	// Constructor
	public BadSeller(String name, int amountOfItems, List<Product> products) 
	{
		super(name, amountOfItems, products);
	}

	/*
	 * This method computes the probability of seller changes the conditions from initial contract
	 * In this case, there is a probability of 80% of seller changes all of contract's conditions
	 * @param agreedOffer initial contract's terms defined between a buyer and the seller during the proposal phase
	 * @return a new contract in literal format with the real delivery conditions, which may or not be according to initial contract
	 */
	@Override
	public Offer computeContractConditions(Offer agreedOffer) 
	{
		Random rand = new Random();
		
		double realPrice = agreedOffer.getProduct().getPrice();
		double realQuality = agreedOffer.getProduct().getQuality();
		int realDeliveryTime = agreedOffer.getProduct().getDeliveryTime();

		int probFactor = rand.nextInt(10);

		if(probFactor < 8)
		{		
			realPrice = agreedOffer.getProduct().getPrice() * (1 + 0.5 * rand.nextDouble());							//up to 50% of increasing
			realQuality = agreedOffer.getProduct().getQuality() * (1 - 0.2 * rand.nextDouble());						//up to 20% of decreasing
			realDeliveryTime = (int) (agreedOffer.getProduct().getDeliveryTime() * (1 + 0.5 * rand.nextDouble()));	//up to 50% of increasing
		}
		
		return (Offer) new Offer(agreedOffer.getProduct().getName(), realPrice, realQuality, (int) realDeliveryTime, agreedOffer.getSeller());
	}

	@Override
	public void defineProductsSalesBehavior() 
	{
		for(Product product : productsForSale)
		{
			try 
			{
				product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_INCREASING, Market.TOTAL_RESQUESTS));
				product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_INCREASING, Market.TOTAL_RESQUESTS));
				product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_INCREASING, Market.TOTAL_RESQUESTS));
			} 
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}	
	}
}
