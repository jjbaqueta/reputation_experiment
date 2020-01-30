package entities.services;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import entities.model.Buyer;
import entities.model.Product;
import entities.model.Seller;
import environments.Market;
import jason.asSyntax.Literal;

/*
 * This class implements a set of operations regarding to Market (main environment)
 */
public abstract class MarketFacade 
{	
    private static AtomicInteger seqId = new AtomicInteger();	//identity sellers and buyers of unique way
	
	/*
	 * This method selects an item randomly from the available product list
	 * The literal returned has the follow form: buy(request_id,product_name)
	 * @param products List of products 
	 * @return a belief about the product that will be bought
	 */
	public static Literal whatToBuy(List<Product> availableProducts) 
	{
		Random rand = new Random();
		Product p = availableProducts.get(rand.nextInt(availableProducts.size()));
		return Literal.parseLiteral("buy("+ seqId.getAndIncrement() + "," + p.getName().toLowerCase() + ")");
	}
	
	/*
	 * This method shows all information about sellers and buyers including their products for sale and to buying, respectively
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
				System.out.println("   -> " + order);

			System.out.println("");
		}
		
		System.out.println("\n--------------------- SELLERS --------------------\n");
		
		for(Seller seller : Market.sellers)
		{
			System.out.println("Seller's name: " + seller.getName());
			System.out.println("List of products for sale: ");
			
			for(Product product: seller.getProductsForSale())
				System.out.println("   -> " + product);

			System.out.println("");
		}
	}
	
	/*
	 * This method returns the index of a buyer stored in the static array of buyers, for more details @see Market
	 * @param name String value that represents the buyer's name
	 * @return the index of buyer  
	 */
	public static int getBuyerIdFrom(String name) {
		if(name.equals("buyer"))
			return 0;
		else
			return Integer.parseInt(name.split("buyer")[1]) - 1;
	}
	
	/*
	 * This method returns the index of a seller stored in the static array of sellers, for more details @see Market
	 * @param name String value that represents the seller's name
	 * @return the index of seller  
	 */
	public static int getSellerIdFrom(String name) {
		if(name.equals("seller"))
			return 0;
		else
			return Integer.parseInt(name.split("seller")[1]) - 1;
	}
	
	/*
	 * This method checks if there is some buyer in buying process
	 * If all buyers ends their purchases, the market can close
	 * @return false there is at least one buyer buying, otherwise return true
	 */
	public static boolean isMarketEnd()
	{	
		for(Buyer buyer : Market.buyers)
		{
			if(buyer.isBuying())
				return false;
		}
		
		return true;
	}
}
