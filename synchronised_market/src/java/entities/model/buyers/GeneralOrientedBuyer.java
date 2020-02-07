package entities.model.buyers;

import java.util.List;

import entities.model.Product;

/*
 * This Class implements a GeneralOrientedBuyer
 * On a purchase, this kind of buyer may care about different preferences: @see #setMyPreferences()
 */
public class GeneralOrientedBuyer extends Buyer
{
	/* 
	 * Constructor
	 * @param name String value that represents the buyer's name
	 * @param pricePreference Double value that represents the price preference factor [0.0, 1.0]
	 * @param qualityPreference Double value that represents the quality preference factor [0.0, 1.0]
	 * @param deliveryPreference Double value that represents the delivery preference factor [0.0, 1.0]
	 * @param amountOfItems Integer value that represents the number of products that the buyer wants buying
	 * @param availableProducts List of Products available to sell
	 */
	public GeneralOrientedBuyer(String name, double pricePreference, double qualityPreference, double deliveryPreference, int amountOfItems, List<Product> products) 
	{
		super(name, amountOfItems, products);
		setPreferenceByPrice(pricePreference);
		setPreferenceByQuality(qualityPreference);
		setPreferenceByDelivery(deliveryPreference);
	}

	/*
	 * For this kind of buyer this method is not implemented, since his preferences are defined directly in his construct
	 */
	@Override
	public void setMyPreferences() 
	{
		//not implemented
	}
}