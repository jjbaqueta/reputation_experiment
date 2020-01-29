package entities.model;

import java.util.List;

/*
 * This Class implements a DeliveryOrientedBuyer
 * On a purchase, this kind of buyer only cares about the product's delivery time: @see #setMyPreferences()
 */
public class DeliveryOrientedBuyer extends Buyer
{
	// Constructor
	public DeliveryOrientedBuyer(String name, int amountOfItems, List<Product> products) 
	{
		super(name, amountOfItems, products);
		setMyPreferences();
	}

	/*
	 * This method defines the buyer's preferences in relation to price, quality and delivery
	 * The delivery time is defined as priority
	 */
	@Override
	public void setMyPreferences() 
	{
		setPreferenceByPrice(0);
		setPreferenceByQuality(0);
		setPreferenceByDelivery(1);
	}
}