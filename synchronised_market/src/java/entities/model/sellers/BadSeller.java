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
	public BadSeller(String name, double priceMargin, double qualityMargin, double deliveryMargin, List<Product> products) 
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
				product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
				product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
				product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
			} 
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}	
	}
}
