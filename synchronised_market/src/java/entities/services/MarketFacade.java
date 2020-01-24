package entities.services;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import entities.model.Buyer;
import entities.model.Product;
import entities.model.Seller;
import environments.Market;
import jason.asSyntax.Literal;

public abstract class MarketFacade {
	
    private static AtomicInteger seqId = new AtomicInteger();	//id generator
	
	/*
	 * This method picks an item randomly from the product list
	 * The format of return is: buy(request_id,product_name)
	 * @param products List of products 
	 * @return a belief about the product that will be bought
	 */
	public static Literal whatToBuy(List<Product> products) {
		Random rand = new Random();
		Product p = products.get(rand.nextInt(products.size()));
		return Literal.parseLiteral("buy("+ seqId.getAndIncrement() + "," + p.getName().toLowerCase() + ")");
	}
	
	/*
	 * This method shows all information about sellers and buyers including their products for sale and buying
	 */
	public static void showBuyersAndSellersInformations()
	{
		System.out.println("\n--------------------- BUYERS --------------------\n");
		for(Buyer buyer : Market.buyers)
		{
			System.out.println("Buyer's name: " + buyer.getName());
			System.out.println("Preferences: {" + "price: " + buyer.getPreferenceByPrice() + ", quality: " + buyer.getPreferenceByQuality() + ", delivery: " + buyer.getPreferenceByDelivery() + "}");
			System.out.println("Orders list {format: buy(CNPId, product)}: ");
			
			for(Literal order: buyer.getProductsToBuy())
			{
				System.out.println("   -> " + order);
			}
			System.out.println("");
		}
		
		System.out.println("\n--------------------- SELLERS --------------------\n");
		for(Seller seller : Market.sellers)
		{
			System.out.println("Seller's name: " + seller.getName());
			System.out.println("List of products for sale: ");
			
			for(Product product: seller.getProductsForSale())
			{
				System.out.println("   -> " + product);
			}
			System.out.println("");
		}
	}
	
	public static int getBuyerIdFrom(String name) {
		if(name.equals("buyer"))
			return 0;
		else
			return Integer.parseInt(name.split("buyer")[1]) - 1;
	}
	
	public static int getSellerIdFrom(String name) {
		if(name.equals("seller"))
			return 0;
		else
			return Integer.parseInt(name.split("seller")[1]) - 1;
	}
}
