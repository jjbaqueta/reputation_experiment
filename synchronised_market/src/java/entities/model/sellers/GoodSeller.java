package entities.model.sellers;

import java.util.List;

import entities.model.Offer;
import entities.model.Product;
import entities.services.BehaviorFactory;
import enums.BehaviorPattern;
import environments.Market;

/*
 * This Class implements a GoodSeller
 * This kind of seller doesn't change the contract's conditions
 * See contract conditions implementation in: @see #computeContractConditions(Offer agreedOffer) 
 */
public class GoodSeller extends Seller{

	// Constructor
	public GoodSeller(String name, int amountOfItems, List<Product> products) 
	{
		super(name, amountOfItems, products);
	}

	/*
	 * This method returns the same conditions than those defined on initial contract
	 * @param agreedOffer initial contract's terms defined between a buyer and the seller during the proposal phase
	 * @return a new contract in literal format with the real delivery conditions, which may or not be according to initial contract
	 */
	@Override
	public Offer computeContractConditions(Offer agreedOffer) 
	{
		return (Offer) new Offer(agreedOffer.getProduct().getName(), 
								 agreedOffer.getProduct().getPrice(), 
								 agreedOffer.getProduct().getQuality(), 
								 agreedOffer.getProduct().getDeliveryTime(), 
								 agreedOffer.getSeller());
	}
	
	@Override
	public void defineProductsSalesBehavior() 
	{
		for(Product product : productsForSale)
		{
			try 
			{
				product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
				product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
				product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
			} 
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}	
	}

}
