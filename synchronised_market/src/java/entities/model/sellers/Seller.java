package entities.model.sellers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.model.Offer;
import entities.model.Product;
import entities.model.SimpleAgent;
import entities.model.behaviors.Behavior;
import entities.services.ReputationFacade;
import jason.asSyntax.Literal;
import reputationModels.Reputation;

public abstract class Seller extends SimpleAgent
{
	protected List<Product> productsForSale;
	protected List<Reputation> reputationsLog;
	
	protected int saleMadeCount;					
	protected int saleLostCount;
	
	
	/* Constructors ****************************/
	
	/*
	 * This constructor initializes the list of products sold by seller
	 * @param name Seller's name
	 * @param priceMargin A double value that defines the maximum price variation
	 * @param qualityMargin A double value that defines the maximum quality variation
	 * @param deliveryMargin A double value that defines the maximum delivery time variation
	 * @param products List of products available for sale
	 */
	public Seller(String name, double priceMargin, double qualityMargin, double deliveryMargin,  List<Product> products) 
	{
		super.setName(name);
		
		reputationsLog = new ArrayList<Reputation>();
		reputationsLog.add(ReputationFacade.generateMaxRepTuple(this, 0));
		
		saleMadeCount = 0;
		saleLostCount = 0;
		
		productsForSale = products;

		defineProductsAttributes(priceMargin, qualityMargin, deliveryMargin);	
		defineProductsSalesBehavior();	
	}
	
	
	/* Getters ****************************/
	
	public List<Product> getProductsForSale() {return this.productsForSale;}
	
	public List<Reputation> getReputations() {return reputationsLog;}
	
	public int getSaleMadeCount() {return saleMadeCount;}

	public int getSaleLostCount() {return saleLostCount;}

	
	/* Setters ****************************/
	
	public void increaseSaleMade() {this.saleMadeCount++;}
	
	public void increaseSaleLost() {this.saleLostCount++;}
	
	public void addReputationInLog(Reputation reputation) {reputationsLog.add(reputation);}
	
	
	/* General methods **********************/
	
	public abstract void defineProductsSalesBehavior();
	
	/*
	 * This method is used by seller to change the products attributes according to his own sell conditions
	 * @param priceFactor percentage of increase/decrease for the price attribute
	 * @param priceQuality percentage of increase/decrease for the quality attribute
	 * @param priceDelivery percentage of increase/decrease for the delivery time attribute
	 */
	private void defineProductsAttributes(double priceFactor, double qualityFactor, double deliveryFactor)
	{
		for(Product product : productsForSale)
		{
			product.setPrice(product.getPrice() * (1 + priceFactor));							//increase price
			product.setQuality(product.getQuality() * (1 - qualityFactor));						//decrease quality
			product.setDeliveryTime((int) (product.getDeliveryTime() * (1 + deliveryFactor)));	//increase delivery time
		}
	}
	
	/*
	 * This method is an abstract method which must be implemented by all sellers that extend this class.
	 * For more details about the types of sellers: @see{GoodSeller, NeutralSeller, BadSeller}
	 * it is used to compute the price, quality and delivery penalties on a contracted service by a given buyer
	 * @param productName Name of product
	 * @param seriesSize Size of temporal series used to compute the sales behavior of product
	 * @param seriesElement Instant of time within temporal series 
	 * @return A new offer is product exist, otherwise null
	 */
	public Offer recalculateContractConditions(String productName, int seriesSize, long seriesElemet) 
	{
		Product product = searchProductByName(productName);
		
		if(product != null)
		{		
			Behavior pBehavior = product.getSalesBehaviorPrice();
			Behavior qBehavior = product.getSalesBehaviorQuality();
			Behavior dBehavior = product.getSalesBehaviorDelivery();
			
			double realPrice = product.getPrice() * 
					(1 + pBehavior.getbehaviorValueFor((seriesSize - (int) seriesElemet) + 1));
			
			double realQuality = product.getQuality() * 
					(1 - qBehavior.getbehaviorValueFor((seriesSize - (int) seriesElemet) + 1));
			
			int realDeliveryTime = (int) (product.getDeliveryTime() * 
					(1 + dBehavior.getbehaviorValueFor((seriesSize - (int) seriesElemet) + 1)));
			
			return (Offer) new Offer(productName, realPrice, realQuality, realDeliveryTime, this);
		}
		
		return null;
	}
	
	/*
	 * This method search a product by its name from the list of product for sale
	 * @param productName name of product to be found
	 * @return a reference to product if it exist, otherwise 0
	 */
	
	public Product searchProductByName(String productName) 
	{
		for (Product product : productsForSale)
		{	
			if (product.getName().equals(productName))
				return product;
		}
		return null;
	}
	
	/*
	 * This method convert the set of products sold by seller into a sale list in literal format.
	 * The literal form of an item from a sale list is: sell(product_name,product_price,product_quality,product_delivery)
	 * @return a sale list with all sale items in literal format
	 */
	public List<Literal> getProductsAsLiteralList()
	{
		List<Literal> saleList = new ArrayList<Literal>();
		
		for(Product product : productsForSale)
			saleList.add(Literal.parseLiteral(
					"sell("+product.getName().toLowerCase()+"," +
							product.getPrice()+"," +
							product.getQuality()+"," +
							product.getDeliveryTime()+")"));
		
		return saleList;
	}

	@Override
	public String toString() 
	{
		String strProducts = productsForSale.stream()
                .map(p -> String.valueOf(p))
                .collect(Collectors.joining(", "));
		
		return "Seller [name=" + this.getName() + ", products={" + strProducts + "}"; 
	}
}
