package entities.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import entities.services.ProductsFacade;
import jason.asSyntax.Literal;

public abstract class Seller extends SimpleAgent
{
	// Items for sale
	private Set<Product> productsForSale;
	
	/*
	 * This constructor initializes the list of products sold by seller
	 * @param name Seller's name
	 * @param amountOfItems Integer value that represents the number of products that the seller can sell
	 * @param availableProducts List of Products available to sell
	 */
	public Seller(String name, int amountOfItems, List<Product> availableProducts) 
	{
		super.setName(name);
		productsForSale = ProductsFacade.getSubsetFrom(amountOfItems, availableProducts);
		
		// A seller may define his own sell conditions, up to 20% the more than products original conditions
		Random rand = new Random();
		double priceFactor = 0.2 * rand.nextDouble();
		double qualityFactor = 0.2 * rand.nextDouble();
		double deliveryFactor = 0.2 * rand.nextDouble();
		setMySellConditions(priceFactor, qualityFactor, deliveryFactor);
	}
	
	/*
	 * This method is an abstract method which must be implemented by all sellers that extend this class.
	 * For more details about the types of sellers: @see{GoodSeller, NeutralSeller, BadSeller}
	 * it is used to compute the price, quality and delivery penalties on a contracted service by a given buyer
	 * @param agreedOffer initial contract's terms defined between a buyer and the seller during the proposal phase
	 * @return a new contract in literal format with the real delivery conditions, which may or not be according to initial contract
	 */
	public abstract Literal computeContractConditions(Offer agreedOffer);
	
	/*
	 * This method is used by seller to change the products attributes according to his own sell conditions
	 * @param priceFactor percentage of increase/decrease for the price attribute
	 * @param priceQuality percentage of increase/decrease for the quality attribute
	 * @param priceDelivery percentage of increase/decrease for the delivery time attribute
	 */
	private void setMySellConditions(double priceFactor, double qualityFactor, double deliveryFactor)
	{
		for(Product product : productsForSale)
		{
			product.setPrice(product.getPrice() * (1 + priceFactor));							//increase price
			product.setQuality(product.getQuality() * (1 - qualityFactor));						//decrease quality
			product.setDeliveryTime((int) (product.getDeliveryTime() * (1 + deliveryFactor)));	//increase delivery time
		}
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
			saleList.add(Literal.parseLiteral("sell("+product.getName().toLowerCase()+","+product.getPrice()+","+product.getQuality()+","+product.getDeliveryTime()+")"));
		
		return saleList;
	}
	
	public Set<Product> getProductsForSale()
	{
		return this.productsForSale;
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
