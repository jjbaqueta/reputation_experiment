package entities.model;

import java.util.List;

public class DeliveryOrientedBuyer extends Buyer{

	public DeliveryOrientedBuyer(String name, int amountOfItems, List<Product> products) {
		super(name, amountOfItems, products);
		setMyPreferences();
	}

	@Override
	public void setMyPreferences() {
		setPreferenceByPrice(0);
		setPreferenceByQuality(0);
		setPreferenceByDelivery(1);
	}
}