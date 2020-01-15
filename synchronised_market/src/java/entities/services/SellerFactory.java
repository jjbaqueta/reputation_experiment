package entities.services;

import java.util.List;

import entities.model.BadSeller;
import entities.model.GoodSeller;
import entities.model.NeutralSeller;
import entities.model.Product;
import entities.model.Seller;
import enums.SellerType;

public class SellerFactory {
	
	public static Seller getSeller(SellerType type, String name, int amountOfItems, List<Product> products) throws Exception{
		switch(type) {
		case BAD:
			return new BadSeller(name, amountOfItems, products);
		case NEUTRAL:
			return new NeutralSeller(name, amountOfItems, products);
		case GOOD:
			return new GoodSeller(name, amountOfItems, products);
		default:
			throw new Exception("Seller's type is not allowed");
		}
	}
}
