package entities.model;

import java.util.List;
import java.util.Random;
import java.util.Set;

import enums.Product;
import jason.asSyntax.Literal;

public class Seller {
	
	public Seller() {}
	
	public Literal whatToSell(int amountOfItems, List<Product> products)
	{
		Set<Product> forSell = Product.getItemsForSale(amountOfItems, products);
		
		if(forSell != null)
		{
			Product[] auxArray = new Product[forSell.size()];
		    forSell.toArray(auxArray);
			return setPricesForProducts(auxArray);
		}
		else
			throw new NullPointerException();
	}
	
	private Literal setPricesForProducts(Product products[])
	{
		Random r = new Random();
		String belief = "sell([";
		double basePrice, price = 0.0;
		
		for(int i = 0; i < products.length; i++)
		{
			basePrice = Product.valueOf(products[i].name()).getPrice();
			price = basePrice + ((basePrice * 1.4) - basePrice) * r.nextDouble();
			
			if(i < products.length - 1)
				belief += "p(" + products[i].name().toLowerCase() + ", " + price + "), "; 
		}
		
		belief += "p(" + products[products.length - 1].name().toLowerCase() + ", " + price + ")])";
			
		return Literal.parseLiteral(belief);
	}
	
}
