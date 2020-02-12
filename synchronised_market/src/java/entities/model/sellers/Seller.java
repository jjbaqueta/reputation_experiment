package entities.model.sellers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import entities.model.Offer;
import entities.model.Product;
import entities.model.SimpleAgent;
import entities.model.behaviors.Behavior;
import entities.services.ProductsFacade;
import enums.CriteriaType;
import environments.Market;
import jason.asSyntax.Literal;
import reputationModels.Reputation;

public abstract class Seller extends SimpleAgent
{
	protected Set<Product> productsForSale;	// Items for sale
	private int saleMadeCount;				// Number of sale that were performed
	private int saleLostCount;				// Number of sale that were lost
	
	private List<Reputation> reputations;
	
	/*
	 * This constructor initializes the list of products sold by seller
	 * @param name Seller's name
	 * @param amountOfItems Integer value that represents the number of products that the seller can sell
	 * @param priceMargin A double value that defines the maximum price variation
	 * @param qualityMargin A double value that defines the maximum quality variation
	 * @param deliveryMargin A double value that defines the maximum delivery time variation
	 * @param availableProducts List of Products available to sell
	 */
	public Seller(String name, int amountOfItems, double priceMargin, double qualityMargin, double deliveryMargin,  List<Product> availableProducts) 
	{
		super.setName(name);
		productsForSale = ProductsFacade.getNoRadomSubsetFrom(amountOfItems, availableProducts);
		saleMadeCount = 0;
		saleLostCount = 0;
		
		reputations = new ArrayList<Reputation>();
		
		Reputation reputation = new Reputation(this, 0);
		reputation.setReputationRatings(CriteriaType.PRICE.getValue(), -1.0);
		reputation.setReputationRatings(CriteriaType.QUALITY.getValue(), -1.0);
		reputation.setReputationRatings(CriteriaType.DELIVERY.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.PRICE.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.QUALITY.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.DELIVERY.getValue(), -1.0);
		reputations.add(reputation);
		
		setMySellConditions(priceMargin, qualityMargin, deliveryMargin);
		defineProductsSalesBehavior();	
	}
	
	public Seller(String name, int amountOfItems, double priceMargin, double qualityMargin, double deliveryMargin) 
	{
		super.setName(name);
		productsForSale = new LinkedHashSet<Product>();
		saleMadeCount = 0;
		saleLostCount = 0;
		
		reputations = new ArrayList<Reputation>();
		
		Reputation reputation = new Reputation(this, 0);
		reputation.setReputationRatings(CriteriaType.PRICE.getValue(), -1.0);
		reputation.setReputationRatings(CriteriaType.QUALITY.getValue(), -1.0);
		reputation.setReputationRatings(CriteriaType.DELIVERY.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.PRICE.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.QUALITY.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.DELIVERY.getValue(), -1.0);
		reputations.add(reputation);
		
		setMySellConditions(priceMargin, qualityMargin, deliveryMargin);
		defineProductsSalesBehavior();	
	}
	
	public void sell(Product product)
	{
		productsForSale.add(product);
	}
	
	public abstract void defineProductsSalesBehavior();
	
	/*
	 * This method is an abstract method which must be implemented by all sellers that extend this class.
	 * For more details about the types of sellers: @see{GoodSeller, NeutralSeller, BadSeller}
	 * it is used to compute the price, quality and delivery penalties on a contracted service by a given buyer
	 * @param agreedOffer initial contract's terms defined between a buyer and the seller during the proposal phase
	 * @return a new contract in literal format with the real delivery conditions, which may or not be according to initial contract
	 */
	public Offer computeContractConditions(Offer agreedOffer) 
	{
		Product product = getProductFromName(agreedOffer.getProduct());
		
		if(product != null)
		{
			Behavior pBehavior = product.getSalesBehaviorPrice();
			Behavior qBehavior = product.getSalesBehaviorQuality();
			Behavior dBehavior = product.getSalesBehaviorDelivery();
			
			double realPrice = agreedOffer.getProduct().getPrice() * (1 + pBehavior.getbehaviorValueFor(Market.TOTAL_RESQUESTS - agreedOffer.getCnpid()));
			double realQuality = agreedOffer.getProduct().getQuality() * (1 - qBehavior.getbehaviorValueFor(Market.TOTAL_RESQUESTS - agreedOffer.getCnpid()));
			double realDeliveryTime = (int) (agreedOffer.getProduct().getDeliveryTime() * (1 + dBehavior.getbehaviorValueFor(Market.TOTAL_RESQUESTS - agreedOffer.getCnpid())));
			
			return (Offer) new Offer(agreedOffer.getProduct().getName(), realPrice, realQuality, (int) realDeliveryTime, agreedOffer.getSeller());
		}
		
		return null;
	}
	
	/*
	 * This method is used by seller to change the products attributes according to his own sell conditions
	 * @param priceFactor percentage of increase/decrease for the price attribute
	 * @param priceQuality percentage of increase/decrease for the quality attribute
	 * @param priceDelivery percentage of increase/decrease for the delivery time attribute
	 */
	private void setMySellConditions(double priceFactor, double qualityFactor, double deliveryFactor)
	{
		System.out.println(priceFactor);
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
	
	public Product getProductFromName(Product p) 
	{
		for (Product product : productsForSale)
		{
			if (product.equals(p))
				return product;
		}
		return null;
	}
	
	public Set<Product> getProductsForSale()
	{
		return this.productsForSale;
	}
	
	public int getSaleMadeCount() 
	{
		return saleMadeCount;
	}

	public void increaseSaleMade() 
	{
		this.saleMadeCount++;
	}

	public int getSaleLostCount() 
	{
		return saleLostCount;
	}

	public void increaseSaleLost() 
	{
		this.saleLostCount++;
	}

	public List<Reputation> getReputations() {
		return reputations;
	}

	public void addRep(Reputation reputation) {
		reputations.add(reputation);
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
