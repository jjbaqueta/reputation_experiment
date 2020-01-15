package entities.model;

import java.util.List;

public class QualityOrientedBuyer extends Buyer{

	public QualityOrientedBuyer(String name, int amountOfItems, List<Product> products) {
		super(name, amountOfItems, products);
		setMyPreferences();
	}

	@Override
	public void setMyPreferences() {
		setPreferenceByPrice(0);
		setPreferenceByQuality(1);
		setPreferenceByDelivery(0);
	}
}
