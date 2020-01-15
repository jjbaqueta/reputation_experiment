package entities.model;

import java.util.List;

public class GeneralOrientedBuyer extends Buyer{
	
	public GeneralOrientedBuyer(int amountOfItems, List<Product> products, String name, double pricePreference, double qualityPreference, double deliveryPreference) {
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