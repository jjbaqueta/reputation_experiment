package entities.model;

import java.util.List;

/*
 * This Class implements a PriceOrientedBuyer
 * On a purchase, this kind of buyer only cares about the product's price: @see #setMyPreferences()
 */
public class PriceOrientedBuyer extends Buyer
{
	// Constructor
	public PriceOrientedBuyer(String name, int amountOfItems, List<Product> products) 
	{
		super(name, amountOfItems, products);
		setMyPreferences();
	}

	/*
	 * This method defines the buyer's preferences in relation to price, quality and delivery
	 * The price is defined as priority
	 */
	@Override
	public void setMyPreferences() 
	{
		setPreferenceByPrice(1);
		setPreferenceByQuality(0);
		setPreferenceByDelivery(0);
	}

}
