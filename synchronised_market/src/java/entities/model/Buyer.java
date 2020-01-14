package entities.model;

import java.util.List;
import java.util.Random;
import java.util.Stack;

import jason.asSyntax.Literal;

public class Buyer extends SimpleAgent implements BuyerContract{
	
	private Stack<Literal> productsToBuy;
	
	public Buyer(String name)
	{
		super.setName(name);
		productsToBuy = new Stack<Literal>();
	}
	
	/*
	 * This method picks an item randomly from product list
	 * @param products List of products 
	 * @return belief about the product that will be bought
	 */
	@Override
	public Literal whatToBuy(List<Product> products) {
		Random rand = new Random();
		Product p = products.get(rand.nextInt(products.size()));
		return Literal.parseLiteral("buy(" + p.getName().toLowerCase() + ")");
	}
	
	/*
	 * This method adds new items to be buying
	 * @param amountOfItems number of items add within the stack of products 
	 * @param products List of products
	 */
	public void addItemsToBuy(int amountOfItems, List<Product> products)
	{
		while(amountOfItems > 0)
		{
			productsToBuy.push(whatToBuy(products));
			amountOfItems--;
		}
	}
	
	/*
	 * This method removes item on top of stack of buying
	 */
	public void removeItemBought()
	{
		productsToBuy.pop();
	}
	
	/*
	 * This method checks if the buyer still want to buy
	 * @return true if there is buying desire, otherwise false
	 */
	public boolean hasBuyingDesire()
	{
		return productsToBuy.isEmpty();
	}

	public Stack<Literal> getProductsToBuy() {
		return productsToBuy;
	}
}
