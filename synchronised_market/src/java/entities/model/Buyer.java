package entities.model;

import java.util.List;
import java.util.Random;

import enums.Product;

public class Buyer {
	
	private boolean buying;		//indicates if buyer is buying at the moment
	private boolean stopBuying; //if true, indicates that buyer cannot longer to buy
	
	public Buyer()
	{
		buying = false;
		stopBuying = false;
	}
	
	/*
	 * This method picks an item randomly from a given product list
	 * @param products A List<Product> that contains all products available to buy 
	 * @return String the name of the product picked from the list
	 */
	public String whatToBuy(List<Product> products)
	{
		buying = true;
		Random rand = new Random();
		return products.get(rand.nextInt(products.size())).name().toLowerCase();
	}
	
	/*
	 * This method defines the end of the simulation
	 */
	public void finishNegotiation()
	{
		this.stopBuying = true;
	}
	
	public boolean checkNegotiationStatus() {
		return stopBuying;
	}
	
	public boolean isBuying() {
		return buying;
	}
}
