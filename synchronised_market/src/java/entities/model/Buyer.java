package entities.model;

import java.util.List;
import java.util.Stack;

import entities.services.MarketFacade;
import jason.asSyntax.Literal;

public abstract class Buyer extends SimpleAgent{
	
	private Stack<Literal> productsToBuy;
	
	private double preferenceByPrice;
	private double preferenceByQuality;
	private double preferenceByDelivery;
	
	public Buyer(String name, int amountOfItems, List<Product> products)
	{
		super.setName(name);
		productsToBuy = new Stack<Literal>();
		addItemsToBuy(amountOfItems, products);
	}
	
	/*
	 * This method adds new items to the buying stack from buyer
	 * @param amountOfItems number of product orders that will be added within buying stack 
	 * @param products List of available products for sale
	 */
	private void addItemsToBuy(int amountOfItems, List<Product> products)
	{
		while(amountOfItems > 0)
		{
			productsToBuy.push(MarketFacade.whatToBuy(products));
			amountOfItems--;
		}
	}
	
	/*
	 * This method removes the product order on the top of the buying stack
	 */
	public void removeItemBought()
	{
		productsToBuy.pop();
	}
	
	/*
	 * This method checks if the buyer wants continue buying
	 * @return false, case the buying stack is empty, otherwise true
	 */
	public boolean hasBuyingDesire()
	{
		return !productsToBuy.isEmpty();
	}

	public Stack<Literal> getProductsToBuy() {
		return productsToBuy;
	}

	public double getPreferenceByPrice() {
		return preferenceByPrice;
	}

	public void setPreferenceByPrice(double preferenceByPrice) {
		this.preferenceByPrice = preferenceByPrice;
	}

	public double getPreferenceByQuality() {
		return preferenceByQuality;
	}

	public void setPreferenceByQuality(double preferenceByQuality) {
		this.preferenceByQuality = preferenceByQuality;
	}

	public double getPreferenceByDelivery() {
		return preferenceByDelivery;
	}

	public void setPreferenceByDelivery(double preferenceByDelivery) {
		this.preferenceByDelivery = preferenceByDelivery;
	}

	public void setProductsToBuy(Stack<Literal> productsToBuy) {
		this.productsToBuy = productsToBuy;
	}
	
	public Literal getPreferencesAsLiteral()
	{
		return Literal.parseLiteral("pref("+ preferenceByPrice + "," + preferenceByQuality + "," + preferenceByDelivery +")");
	}
	
	/*
	 * This method must be used to define the buyer's preferences in relation to price, quality and delivery
	 */
	public abstract void setMyPreferences();
}
