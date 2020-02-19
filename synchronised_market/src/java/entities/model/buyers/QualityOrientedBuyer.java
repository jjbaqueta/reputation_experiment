package entities.model.buyers;

import java.util.List;

import entities.model.Product;

/*
 * This Class implements a QualityOrientedBuyer
 * On a purchase, this kind of buyer only cares about the product's quality: @see #setMyPreferences()
 */
public class QualityOrientedBuyer extends Buyer
{
	/* 
	 * Constructor
	 * @param name String value that represents the buyer's name
	 * @param products List of desired products
	 */
	public QualityOrientedBuyer(String name, List<Product> products) 
	{
		super(name, products);
		
		this.preferenceByPrice = 0;
		this.preferenceByQuality = 1;
		this.preferenceByDelivery = 0;
	}
}
