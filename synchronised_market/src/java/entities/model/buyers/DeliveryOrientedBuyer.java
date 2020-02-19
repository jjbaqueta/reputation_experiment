package entities.model.buyers;

import java.util.List;

import entities.model.Product;

/*
 * This Class implements a DeliveryOrientedBuyer
 * On a purchase, this kind of buyer only cares about the product's delivery time: @see #setMyPreferences()
 */
public class DeliveryOrientedBuyer extends Buyer
{
	/* 
	 * Constructor
	 * @param name String value that represents the buyer's name
	 * @param products List of desired products
	 */
	public DeliveryOrientedBuyer(String name, List<Product> products) 
	{
		super(name, products);
		
		this.preferenceByPrice = 0;
		this.preferenceByQuality = 0;
		this.preferenceByDelivery = 1;
	}
}