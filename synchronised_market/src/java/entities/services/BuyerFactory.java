package entities.services;

import java.util.List;

import entities.model.Buyer;
import entities.model.DeliveryOrientedBuyer;
import entities.model.PriceOrientedBuyer;
import entities.model.Product;
import entities.model.QualityOrientedBuyer;
import enums.BuyerType;

public class BuyerFactory {
	
	public static Buyer getBuyer(BuyerType type, String buyerName, int amountOfItems, List<Product> products) throws Exception
	{
		switch(type)
		{
			case PRICE_ORIENTED:
				return new PriceOrientedBuyer(buyerName, amountOfItems, products);
			case QUALITY_ORIENTED:
				return new QualityOrientedBuyer(buyerName, amountOfItems, products);
			case DELIVERY_ORIENTED:
				return new DeliveryOrientedBuyer(buyerName, amountOfItems, products);
			default:
				throw new Exception("Buyer's type is not allowed");
		}
	}
}
