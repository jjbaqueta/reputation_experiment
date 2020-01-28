package entities.model;

import java.util.List;

public class GeneralOrientedBuyer extends Buyer{
	
	public GeneralOrientedBuyer(String name, double pricePreference, double qualityPreference, double deliveryPreference, int amountOfItems, List<Product> products) {
		super(name, amountOfItems, products);
		setPreferenceByPrice(pricePreference);
		setPreferenceByQuality(qualityPreference);
		setPreferenceByDelivery(deliveryPreference);
	}

	@Override
	public void setMyPreferences() {
		//not implemented
	}
}