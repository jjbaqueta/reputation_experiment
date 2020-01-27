package entities.model;

import java.util.List;
import java.util.Random;

import jason.asSyntax.Literal;

public class BadSeller extends Seller{

	public BadSeller(String name, int amountOfItems, List<Product> products) {
		super(name, amountOfItems, products);
	}

	@Override
	public Literal computeRealDeliveryConditions(Offer offerAgreement) {
		
		Random rand = new Random();
		
		double realPrice = offerAgreement.getPrice();
		double realQuality = offerAgreement.getQuality();
		int realDeliveryTime = offerAgreement.getDeliveryTime();
		
		/*
		 * Compute the probability of change the initial contract
		 * In this case, there is a probability of 80% of seller changes one or more agreement's conditions
		 */
		int probFactor = rand.nextInt(10);

		if(probFactor < 9)
		{		
			realPrice = offerAgreement.getPrice() * (1 + 0.8 * rand.nextDouble());
			realQuality = offerAgreement.getQuality() * (1 - 0.4 * rand.nextDouble());
			realDeliveryTime = (int) (offerAgreement.getDeliveryTime() * (1 + 0.8 * rand.nextDouble()));
		}
		
		return Literal.parseLiteral("p(" + offerAgreement.getProduct() + "," + realPrice + "," + realQuality + "," + realDeliveryTime + ")");
	}
}
