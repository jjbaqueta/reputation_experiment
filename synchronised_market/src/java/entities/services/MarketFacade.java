package entities.services;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import entities.model.Product;
import jason.asSyntax.Literal;

public class MarketFacade {
	
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
}
