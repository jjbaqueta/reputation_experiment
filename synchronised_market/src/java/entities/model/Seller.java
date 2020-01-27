package entities.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import entities.services.ProductsFacade;
import jason.asSyntax.Literal;

public abstract class Seller extends SimpleAgent{
	
	private Set<Product> productsForSale;
	
	/*
	 * This constructor initializes the list of products sold by seller
	 * @param name Seller's name
	 * @param amountOfItems An integer values that represents the number of products sold
	 * @param products List of products available for sell
	 */
	public Seller(String name, int amountOfItems, List<Product> products) {
		super.setName(name);
		productsForSale = ProductsFacade.getSubsetFrom(amountOfItems, products);
		
		Random rand = new Random();
		double priceFactor = 0.2 * rand.nextDouble();
		double qualityFactor = 0.2 * rand.nextDouble();
		double deliveryFactor = 0.2 * rand.nextDouble();
		setMyConditionsOnProducts(priceFactor, qualityFactor, deliveryFactor);
	}
	
	/*
	 * This method is used by seller to change the products attributes according to his own sell factors
	 * @param priceFactor percentage increase/decrease of price attribute
	 * @param priceQuality percentage increase/decrease of quality attribute
	 * @param priceDelivery percentage increase/decrease of delivery time attribute
	 */
	private void setMyConditionsOnProducts(double priceFactor, double qualityFactor, double deliveryFactor)
	{
		for(Product product : productsForSale)
		{
			product.setPrice(product.getPrice() * (1 + priceFactor));
			product.setQuality(product.getQuality() - product.getQuality() * qualityFactor);
			product.setDeliveryTime( (int) (product.getDeliveryTime() * (1 + deliveryFactor)));
		}
	}
	
	/*
	 * This method convert the set of product of seller into a list of literals (beliefs).
	 * The beliefs are defined according to following format: sell(product_name, product_price, product_quality, product_delivery_time)
	 * @return a list of literals
	 */
	public List<Literal> getProductsAsLiteral()
	{
		List<Literal> beliefsList = new ArrayList<Literal>();
		
		for(Product p : productsForSale)
		{
			beliefsList.add(Literal.parseLiteral("sell("+p.getName().toLowerCase()+","+p.getPrice()+","+p.getQuality()+","+p.getDeliveryTime()+")"));
		}
		return beliefsList;
	}
	
	public Set<Product> getProductsForSale()
	{
		return this.productsForSale;
	}
	
	/*
	 * This method computes the penalties on a service contract
	 * @param offerAgreement contract's terms defined between the buyer and seller
	 * @return a belief about the product delivery conditions, which may or not be according to initial contract
	 */
	public abstract Literal computeRealDeliveryConditions(Offer offerAgreement);
}
