package entities.model;

import java.util.List;

/*
 * This Class implements a QualityOrientedBuyer
 * On a purchase, this kind of buyer only cares about the product's quality: @see #setMyPreferences()
 */
public class QualityOrientedBuyer extends Buyer
{
	// Constructor
	public QualityOrientedBuyer(String name, int amountOfItems, List<Product> products) 
	{
		super(name, amountOfItems, products);
		setMyPreferences();
	}

	/*
	 * This method defines the buyer's preferences in relation to price, quality and delivery
	 * The quality is defined as priority
	 */
	@Override
	public void setMyPreferences() 
	{
		setPreferenceByPrice(0);
		setPreferenceByQuality(1);
		setPreferenceByDelivery(0);
	}
}
