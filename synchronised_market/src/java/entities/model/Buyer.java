package entities.model;

import java.util.List;
import java.util.Random;

import enums.NegotiationStatus;
import enums.Product;

public class Buyer {
	
	private NegotiationStatus negStatus;	//if CLOSE, the buyer cannot longer to buy
	
	public Buyer()
	{
		startNegotiation();;
	}
	
	/*
	 * This method picks an item randomly from a given product list
	 * @param products A List<Product> that contains all products available to buy 
	 * @return String the name of the product picked from the list
	 */
	public String whatToBuy(List<Product> products)
	{
		Random rand = new Random();
		return products.get(rand.nextInt(products.size())).name().toLowerCase();
	}
	
	/*
	 * This method defines the end of negotiations
	 */
	public void finishNegotiation()
	{
		negStatus = NegotiationStatus.CLOSE;
	}
	
	/*
	 * This method defines the begin of negotiations
	 */
	public void startNegotiation()
	{
		negStatus = NegotiationStatus.OPEN;
	}

	public NegotiationStatus getNegotiationStatus() {
		return negStatus;
	}	
}
