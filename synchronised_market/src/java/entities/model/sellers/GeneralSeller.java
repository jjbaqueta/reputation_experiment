package entities.model.sellers;

import java.util.List;

import entities.model.Offer;
import entities.model.Product;
import entities.model.behaviors.Behavior;
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

	/*
	 * This method computes the probability of seller changes the conditions from initial contract
	 * In this case, there is a probability of 80% of seller changes all of contract's conditions
	 * @param agreedOffer initial contract's terms defined between a buyer and the seller during the proposal phase
	 * @return a new contract in literal format with the real delivery conditions, which may or not be according to initial contract
	 */
	@Override
	public Offer computeContractConditions(Offer agreedOffer) 
	{	
		Product product = getProductFromName(agreedOffer.getProduct());
		
		if(product != null)
		{
			Behavior pBehavior = product.getSalesBehaviorPrice();
			Behavior qBehavior = product.getSalesBehaviorQuality();
			Behavior dBehavior = product.getSalesBehaviorDelivery();
			
			double realPrice = agreedOffer.getProduct().getPrice() * (1 + pBehavior.getbehaviorValueFor(agreedOffer.getCnpid()));
			double realQuality = agreedOffer.getProduct().getQuality() * (1 - qBehavior.getbehaviorValueFor(agreedOffer.getCnpid()));
			double realDeliveryTime = (int) (agreedOffer.getProduct().getDeliveryTime() * (1 + dBehavior.getbehaviorValueFor(agreedOffer.getCnpid())));
			
			return (Offer) new Offer(agreedOffer.getProduct().getName(), realPrice, realQuality, (int) realDeliveryTime, agreedOffer.getSeller());
		}
		
		return null;
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