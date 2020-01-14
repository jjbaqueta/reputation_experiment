package entities.model;

import java.util.ArrayList;
import java.util.List;

import entities.services.ProductsFacade;
import jason.asSyntax.Literal;

public class Seller extends SimpleAgent implements SellerContract{
	
	private List<Literal> productsForSale;
	
	public Seller(String name) {
		super.setName(name);
	}

	/*
	 * This method generates list of products to sale considering a specific amount of products.
	 * Each product from the list is translate to a belief in the follow format: sell(p(product_name, product_price, product_quality, product_delivery_time))
	 * @param amountOfItems An integer values that represents the amount of products sold
	 * @param products List of products available to sell
	 * @return A list of literals in belief format
	 */
	@Override
	public List<Literal> whatToSell(int amountOfItems, List<Product> products) {
		
		List<Literal> itemsForSale = new ArrayList<Literal>();  
		
		for(Product p : ProductsFacade.getSubsetFrom(amountOfItems, products))
		{
			itemsForSale.add(Literal.parseLiteral("sell("+p.getName().toLowerCase()+","+p.getPrice()+","+p.getQuality()+","+p.getDeliveryTime()+")"));
		}
		return itemsForSale;
	}

	@Override
	public Literal computeRealDeliveryConditions(Offer offer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * This method initialize the list of products sold by seller
	 * @param amountOfItems An integer values that represents the amount of products sold
	 * @param products List of products available to sell
	 */
	public void setProductsForSale(int amountOfItems, List<Product> products)
	{
		productsForSale = whatToSell(amountOfItems, products); 
	}
	
	public List<Literal> getProductsForSale()
	{
		return this.productsForSale;
	}
	
}
