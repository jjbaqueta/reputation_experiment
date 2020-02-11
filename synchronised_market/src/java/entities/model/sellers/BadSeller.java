package entities.model.sellers;

import java.util.List;

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
	public BadSeller(String name, int amountOfItems, double priceMargin, double qualityMargin, double deliveryMargin, List<Product> availableProducts) 
	{
		super(name, amountOfItems, priceMargin, qualityMargin, deliveryMargin, availableProducts);
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
