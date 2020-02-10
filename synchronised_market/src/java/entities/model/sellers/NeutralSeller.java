package entities.model.sellers;

import java.util.List;
import java.util.Random;

import entities.model.Offer;
import entities.model.Product;

/*
 * This Class implements a NeutralSeller
 * This kind of seller may change ONLY ONE of conditions from initial contract that it was defined between a buyer and the seller
 * See contract conditions implementation in: {@link #computeContractConditions(Offer agreedOffer)} 
 */
public class NeutralSeller extends Seller
{
	// Constructor
	public NeutralSeller(String name, int amountOfItems, List<Product> products) 
	{
		super(name, amountOfItems, products);
	}

	/*
	 * This method computes the probability of seller changes the conditions from initial contract
	 * In this case, there is a probability of 50% of the seller changes one of contract's conditions
	 * However, the probability of a specific condition to change is approximately 16%
	 * @param agreedOffer initial contract's terms defined between a buyer and the seller during the proposal phase
	 * @return a new contract in literal format with the real delivery conditions, which may or not be according to initial contract
	 */
	@Override
	public Offer computeContractConditions(Offer agreedOffer) 
	{
		Random rand = new Random();
		
		double realPrice = agreedOffer.getProduct().getPrice();
		double realQuality = agreedOffer.getProduct().getQuality();
		int realDeliveryTime = agreedOffer.getProduct().getDeliveryTime();
		
		int probFactor = rand.nextInt(6);
		
		if(probFactor == 0)
			realPrice = agreedOffer.getProduct().getPrice() * (1 + 0.8 * rand.nextDouble());							//up to 80% of increasing
		
		else if(probFactor == 1)
			realQuality = agreedOffer.getProduct().getQuality() * (1 - 0.4 * rand.nextDouble());						//up to 40% of decreasing

		else if (probFactor == 2)
			realDeliveryTime = (int) (agreedOffer.getProduct().getDeliveryTime() * (1 + 0.8 * rand.nextDouble()));	//up to 80% of increasing
		
		return (Offer) new Offer(agreedOffer.getProduct().getName(), realPrice, realQuality, (int) realDeliveryTime, agreedOffer.getSeller());
	}

}
