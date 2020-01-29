package entities.model;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import entities.services.MarketFacade;
import jason.asSyntax.Literal;

public abstract class Buyer extends SimpleAgent
{
	// Items to buy
	private Stack<Literal> productsToBuy;
	
	/* 
	 * Buying preferences factors
	 * These factors are used by buyer during the partner choice phase
	 * These factors are used by buyer to choose the product that better meets its needs during a negotiation.
	 */
	private double preferenceByPrice;
	private double preferenceByQuality;
	private double preferenceByDelivery;
	
	/*
	 * Constructor
	 * @param name String value that represents the buyer's name
	 * @param amountOfItems Integer value that represents the number of products that the buyer wants buying
	 * @param availableProducts List of Products available to sell
	 */
	public Buyer(String name, int amountOfItems, List<Product> availableProducts)
	{
		super.setName(name);
		productsToBuy = new Stack<Literal>();
		addItemsToBuy(amountOfItems, availableProducts);
	}
	
	/*
	 * This method is an abstract method which must be implemented by all buyers that extend this class.
	 * For more details about the types of buyers: @see{PriceOrientedBuyer, QualityOrientedBuyer, DeliveryOrientedBuyer, GeneralOrientedBuyer}
	 * It is used to define the buyer's preferences in relation to price, quality and delivery (According to buyer type)
	 */
	public abstract void setMyPreferences();
	
	/*
	 * This method adds new items to the buying stack
	 * @param amountOfItems Integer value that represents the number of products that the buyer wants buying 
	 * @param availableProducts List of Products available to sell
	 */
	private void addItemsToBuy(int amountOfItems, List<Product> availableProducts)
	{
		while(amountOfItems > 0)
		{
			productsToBuy.push(MarketFacade.whatToBuy(availableProducts));
			amountOfItems--;
		}
	}
	
	/*
	 * This method checks if the buyer wants continue buying
	 * @return false, case the buying stack is empty, otherwise true
	 */
	public boolean hasBuyingDesire()
	{
		return !productsToBuy.isEmpty();
	}
	
	/*
	 * This method defines the price preference value
	 * @param preferenceByPrice Double value that represents the price preference factor [0.0, 1.0]
	 */
	public void setPreferenceByPrice(double preferenceByPrice) 
	{
		this.preferenceByPrice = preferenceByPrice;
	}
	
	/*
	 * This method defines the quality preference value
	 * @param preferenceByQuality Double value that represents the quality preference factor [0.0, 1.0]
	 */
	public void setPreferenceByQuality(double preferenceByQuality) 
	{
		this.preferenceByQuality = preferenceByQuality;
	}
	
	/*
	 * This method defines the delivery time preference value
	 * @param preferenceByDelivery Double value that represents the delivery preference factor [0.0, 1.0]
	 */
	public void setPreferenceByDelivery(double preferenceByDelivery) 
	{
		this.preferenceByDelivery = preferenceByDelivery;
	}

	public double getPreferenceByPrice() 
	{
		return preferenceByPrice;
	}

	public double getPreferenceByQuality() 
	{
		return preferenceByQuality;
	}

	public double getPreferenceByDelivery() 
	{
		return preferenceByDelivery;
	}

	public Stack<Literal> getProductsToBuy() 
	{
		return productsToBuy;
	}
	
	/*
	 * This method returns the buyer's preferences in literal format
	 * The literal form of the preferences is: pref(price_preference,quality_preference,delivery_preference)
	 */
	public Literal getPreferencesAsLiteral()
	{
		return Literal.parseLiteral("pref("+ preferenceByPrice + "," + preferenceByQuality + "," + preferenceByDelivery +")");
	}

	@Override
	public String toString() 
	{
		String strStack = productsToBuy.stream()
                .map(p -> String.valueOf(p))
                .collect(Collectors.joining(", "));
		
		return "Buyer [name=" + this.getName() + ", preferenceByPrice=" + preferenceByPrice + ", preferenceByQuality=" + preferenceByQuality + ", preferenceByDelivery=" + preferenceByDelivery
				+ ", desires={" + strStack + "}"; 
	}
}
