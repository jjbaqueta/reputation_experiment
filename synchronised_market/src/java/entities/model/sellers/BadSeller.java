package entities.model.sellers;

import java.util.List;
import java.util.Random;

import entities.model.Offer;
import entities.model.Product;
import jason.asSyntax.Literal;

/*
 * This Class implements a BadSeller
 * This kind of seller may change ALL conditions from initial contract that it was defined between a buyer and the seller
 * See contract conditions implementation in: @see #computeContractConditions(Offer agreedOffer) 
 */
public class BadSeller extends Seller
{
	// Constructor
	public BadSeller(String name, int amountOfItems, List<Product> products) 
	{
		super(name, amountOfItems, products);
	}

	/*
	 * This method computes the probability of seller changes the conditions from initial contract
	 * In this case, there is a probability of 80% of seller changes all of contract's conditions
	 * @param agreedOffer initial contract's terms defined between a buyer and the seller during the proposal phase
	 * @return a new contract in literal format with the real delivery conditions, which may or not be according to initial contract
	 */
	@Override
	public Literal computeContractConditions(Offer agreedOffer) 
	{
		Random rand = new Random();
		
		double realPrice = agreedOffer.getPrice();
		double realQuality = agreedOffer.getQuality();
		int realDeliveryTime = agreedOffer.getDeliveryTime();

		int probFactor = rand.nextInt(10);

		if(probFactor < 8)
		{		
			realPrice = agreedOffer.getPrice() * (1 + 0.5 * rand.nextDouble());							//up to 50% of increasing
			realQuality = agreedOffer.getQuality() * (1 - 0.2 * rand.nextDouble());						//up to 20% of decreasing
			realDeliveryTime = (int) (agreedOffer.getDeliveryTime() * (1 + 0.5 * rand.nextDouble()));	//up to 50% of increasing
		}
		
		return Literal.parseLiteral("p(" + agreedOffer.getProduct() + "," + realPrice + "," + realQuality + "," + realDeliveryTime + ")");
	}
}
