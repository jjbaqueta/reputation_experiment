package entities.model;

import java.util.List;

public class PriceOrientedBuyer extends Buyer{

	public PriceOrientedBuyer(String name, int amountOfItems, List<Product> products) {
		super(name, amountOfItems, products);
		setMyPreferences();
	}

	@Override
	public void setMyPreferences() {
		setPreferenceByPrice(1);
		setPreferenceByQuality(0);
		setPreferenceByDelivery(0);
	}

}
