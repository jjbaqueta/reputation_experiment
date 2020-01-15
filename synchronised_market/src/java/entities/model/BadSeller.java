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
		double realDeliveryTime = offerAgreement.getDeliveryTime();
		
		/*
		 * Compute the probability of change the initial contract
		 * In this case, 1/2
		 */
		int probFactor = rand.nextInt(1);
		
		if(probFactor == 0)
		{
			realPrice = offerAgreement.getPrice() * (1 + (0.2 * rand.nextInt(1)));
			realQuality = offerAgreement.getQuality() - (offerAgreement.getQuality() * (0.2 * rand.nextInt(1)));
			realDeliveryTime = offerAgreement.getDeliveryTime() * (1 + (0.3 * rand.nextInt(1)));
		}
		
		return Literal.parseLiteral("offer(" + offerAgreement.getProduct() + "," + realPrice + "," + realQuality + "" + realDeliveryTime + ")");
	}
}
