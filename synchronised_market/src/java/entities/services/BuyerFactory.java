package entities.services;

import java.util.List;

import entities.model.Product;
import entities.model.buyers.Buyer;
import entities.model.buyers.DeliveryOrientedBuyer;
import entities.model.buyers.PriceOrientedBuyer;
import entities.model.buyers.QualityOrientedBuyer;
import enums.BuyerType;

/*
 * This class implements a factory of buyers
 * This design pattern instances a new buyer according to a type informed
 * For more details about the types of buyers: @see{PriceOrientedBuyer, QualityOrientedBuyer, DeliveryOrientedBuyer, GeneralOrientedBuyer} 
 */
public class BuyerFactory 
{	
	/*
	 * This method implements the factory of buyers
	 * @param type An enum that specifies the type of buyer
	 * @param name String value that represents the buyer's name
	 * @param amountOfItems Integer value that represents the number of products that the buyer wants buying
	 * @param availableProducts List of Products available to sell
	 * @return A Buyer
	 */
	public static Buyer getBuyer(BuyerType type, String name, int amountOfItems, List<Product> availableProducts) throws Exception
	{
		switch(type)
		{
			case PRICE_ORIENTED:
				return new PriceOrientedBuyer(name, amountOfItems, availableProducts);
				
			case QUALITY_ORIENTED:
				return new QualityOrientedBuyer(name, amountOfItems, availableProducts);
				
			case DELIVERY_ORIENTED:
				return new DeliveryOrientedBuyer(name, amountOfItems, availableProducts);
				
			default:
				throw new Exception("Buyer's type is not allowed");
		}
	}
}
