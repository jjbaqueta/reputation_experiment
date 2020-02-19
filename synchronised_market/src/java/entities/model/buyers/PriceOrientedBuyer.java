package entities.model.buyers;

import java.util.List;

import entities.model.Product;

/*
 * This Class implements a PriceOrientedBuyer
 * On a purchase, this kind of buyer only cares about the product's price: @see #setMyPreferences()
 */
public class PriceOrientedBuyer extends Buyer
{
	/* 
	 * Constructor
	 * @param name String value that represents the buyer's name
	 * @param products List of desired products
	 */
	public PriceOrientedBuyer(String name, List<Product> products) 
	{
		super(name, products);
		
		this.preferenceByPrice = 1;
		this.preferenceByQuality = 0;
		this.preferenceByDelivery = 0;
	}
}
