package entities.model.sellers;

import java.util.List;

import entities.model.Product;
import entities.services.BehaviorFactory;
import enums.BehaviorPattern;
import environments.Market;

/*
 * This Class implements a GoodSeller
 * This kind of seller doesn't change the contract's conditions
 * See contract conditions implementation in: @see #computeContractConditions(Offer agreedOffer) 
 */
public class GoodSeller extends Seller
{
	// Constructor
	public GoodSeller(String name, double priceMargin, double qualityMargin, double deliveryMargin, List<Product> products) 
	{
		super(name, priceMargin, qualityMargin, deliveryMargin, products);
	}
	
	@Override
	public void defineProductsSalesBehavior() 
	{
		for(Product product : productsForSale)
		{
			try 
			{
				product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.numberBuyingRequest));
				product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.numberBuyingRequest));
				product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.numberBuyingRequest));
			} 
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}	
	}
}
