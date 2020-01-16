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

		if(probFactor < 7)
		{
			realPrice = offerAgreement.getPrice() * (1 + (rand.nextDouble() * rand.nextInt(2)));
			realQuality = offerAgreement.getQuality() - (offerAgreement.getQuality() * (rand.nextDouble() * rand.nextInt(2)));
			realDeliveryTime = (int) (offerAgreement.getDeliveryTime() * (1 + (rand.nextDouble() * rand.nextInt(2))));
		}
		
		return Literal.parseLiteral("p(" + offerAgreement.getProduct() + "," + realPrice + "," + realQuality + "," + realDeliveryTime + ")");
	}
}
