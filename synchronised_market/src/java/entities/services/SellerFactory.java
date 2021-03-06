package entities.services;

import java.util.List;

import entities.model.Product;
import entities.model.sellers.BadSeller;
import entities.model.sellers.GeneralSeller;
import entities.model.sellers.GoodSeller;
import entities.model.sellers.Seller;
import enums.SellerType;

/*
 * This class implements a factory of sellers
 * This design pattern instances a new seller according to a type informed
 * For more details about the types of sellers: @see{GoodSeller, NeutralSeller, BadSeller} 
 */
public class SellerFactory 
{	/*
	 * This method implements the factory of sellers
	 * @param type An enum that specifies the type of buyer
	 * @param name String value that represents the buyer's name
	 * @param amountOfItems Integer value that represents the number of products that the buyer wants buying
	 * @param availableProducts List of Products available to sell
	 * @return A Seller
	 */
	public static Seller getSeller(SellerType type, String name, double priceMargin, double qualityMargin, double deliveryMargin, List<Product> products) throws Exception
	{
		switch(type) 
		{
			case BAD:
				return new BadSeller(name, priceMargin, qualityMargin, deliveryMargin, products);
				
			case GENERAL:
				return new GeneralSeller(name, priceMargin, qualityMargin, deliveryMargin, products);
				
			case GOOD:
				return new GoodSeller(name, priceMargin, qualityMargin, deliveryMargin, products);
				
			default:
				throw new Exception("Seller's type is not allowed");
		}
	}
}
