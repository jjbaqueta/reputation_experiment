package entities.model.sellers;

import java.util.List;

import entities.model.Product;
import entities.services.BehaviorFactory;
import enums.BehaviorPattern;
import enums.ProductDefault;
import environments.Market;

/*
 * This Class implements a GeneralSeller
 * This kind of seller has his shopping profile defined from a specific behavior
 */
public class GeneralSeller extends Seller
{
	
	// Constructor
	public GeneralSeller(String name, int amountOfItems, double priceMargin, double qualityMargin, double deliveryMargin, List<Product> availableProducts) 
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
				String productName = product.getName().toUpperCase();
				
				if (productName.contentEquals(ProductDefault.TV.name()))
				{
					product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
				}
				else if (productName.contentEquals(ProductDefault.DESKTOP.name()))
				{
					product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.TOTAL_RESQUESTS));
				}
				else if (productName.contentEquals(ProductDefault.NOTEBOOK.name()))
				{
					product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_DECREASING, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_DECREASING, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_DECREASING, Market.TOTAL_RESQUESTS));
				}
				else if (productName.contentEquals(ProductDefault.SMARTPHONE.name()))
				{
					product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.PARABLE_DEC_INC, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.PARABLE_DEC_INC, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.PARABLE_DEC_INC, Market.TOTAL_RESQUESTS));
				}
				else if (productName.contentEquals(ProductDefault.TABLET.name()))
				{
					product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.EXPONENTIAL_INCREASING, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.EXPONENTIAL_INCREASING, Market.TOTAL_RESQUESTS));
					product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.EXPONENTIAL_INCREASING, Market.TOTAL_RESQUESTS));
				}
				else
				{
					throw new Exception("Type of product it is not valid!");
				}
			} 
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}	
	}
}