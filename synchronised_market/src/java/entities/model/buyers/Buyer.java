package entities.model.buyers;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import entities.model.Product;
import entities.model.SimpleAgent;
import entities.services.MarketFacade;
import jason.asSyntax.Literal;
import reputationModels.Image;

public abstract class Buyer extends SimpleAgent
{
	// Items to buy
	protected Stack<Literal> productsToBuy;
	
	/* 
	 * Buying preferences factors
	 * These factors are used by buyer during the partner choice phase
	 * These factors are used by buyer to choose the product that better meets its needs during a negotiation.
	 */
	protected double preferenceByPrice;
	protected double preferenceByQuality;
	protected double preferenceByDelivery;
	
	protected int purchaseCompleteCount;
	protected int purchaseAbortedCount;
	
	protected boolean buying;
	
	protected Image image;
	
	/* Constructors ****************************/
	
	/*
	 * Creates the wishList from a list of product already defined 
	 * @param name String value that represents the buyer's name
	 * @param wishList List of Products to be purchased
	 */
	public Buyer(String name, List<Product> wishList)
	{
		super.setName(name);
		
		productsToBuy = new Stack<Literal>();
		
		buying = true;
		purchaseCompleteCount = 0;
		purchaseAbortedCount = 0;
		
		for(Product product : wishList)
		{
			productsToBuy.push(Literal.parseLiteral(
					"buy("+ MarketFacade.seqId.getAndIncrement() + "," + product.getName().toLowerCase() + ")"));
		}
	}
	
	/* Getters ****************************/
	
	public double getPreferenceByPrice() {return preferenceByPrice;}

	public double getPreferenceByQuality() {return preferenceByQuality;}

	public double getPreferenceByDelivery() {return preferenceByDelivery;}
	
	public int getPurchaseCompleteCount() {return purchaseCompleteCount;}
	
	public int getPurchaseAbortedCount() {return purchaseAbortedCount;}
	
	public Stack<Literal> getProductsToBuy() {return productsToBuy;}
	
	public Image getImage() {return image;}
	
	public boolean hasBuyingDesire() {return !productsToBuy.isEmpty();}
	
	public boolean isBuying() {return buying;}
	

	
	/* Setters ****************************/
	
	public void setImage(Image image) {this.image = image;}
	
	public void setEndOfActivities(){this.buying = false;}
	
	public void increasePurchaseCompleteCount() {this.purchaseCompleteCount++;}

	public void increasePurchaseAbortedCount() {this.purchaseAbortedCount++;}
	
	
	/* General methods **********************/
	
	/*
	 * This method returns the buyer's preferences in literal format
	 * The literal form of the preferences is: pref(price_preference,quality_preference,delivery_preference)
	 */
	public Literal getPreferencesAsLiteral()
	{
		return Literal.parseLiteral(
				"pref("+ preferenceByPrice + "," + 
						 preferenceByQuality + "," + 
						 preferenceByDelivery +")");
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
