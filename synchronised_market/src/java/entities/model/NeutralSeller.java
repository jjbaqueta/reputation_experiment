package entities.model;

import java.util.List;
import java.util.Random;

import jason.asSyntax.Literal;

public class NeutralSeller extends Seller{

	public NeutralSeller(String name, int amountOfItems, List<Product> products) {
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
		 * In this case, there is a probability of 50% of the seller changes one of conditions from contract
		 * However, the probability of a specific condition to change is approximately 16%
		 */
		int probFactor = rand.nextInt(6);
		
		if(probFactor == 0)
		{
			realPrice = offerAgreement.getPrice() * (1 + 0.8 * rand.nextDouble());
		}
		else if(probFactor == 1)
		{
			realQuality = offerAgreement.getQuality() * (1 - 0.4 * rand.nextDouble());
		}
		else if (probFactor == 2)
		{
			realDeliveryTime = (int) (offerAgreement.getDeliveryTime() * (1 + 0.8 * rand.nextDouble()));
		}
		
		return Literal.parseLiteral("p(" + offerAgreement.getProduct() + "," + realPrice + "," + realQuality + "," + realDeliveryTime + ")");
	}

}
