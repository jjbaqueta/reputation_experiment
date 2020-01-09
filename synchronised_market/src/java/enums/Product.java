package enums;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/*
 * This enum contains all products and its respectively prices
 * The prices defined in this file are the lowest prices by product. 
 * This way, the minimum price of a product defined by a seller must be based on this file.
 */
public enum Product {
	TV(1400.0), 
	DESKTOP(2000.0),
	NOTEBOOK(2500.0),
	SMARTPHONE(1500.0),
	TABLET(500.0);
	
	double price;
	
	Product(double p)
	{
		price = p;
	}
	
	public double getPrice()
	{
		return price;
	}
	
    /*
     * This method shows the name and the price of each product of the list of products
     * @param products A list of Products
     */
    public static void showProducts(List<Product> products)
    {
    	System.out.println("-----------------------------------------");
    	System.out.println("Products registered - format: name(price)");
    	
    	for(Product p : products)
    	{
    		System.out.println(p.name().toLowerCase() + "(" + p.getPrice() + ")");
    	}
    	System.out.println("-----------------------------------------");
    }
    
    /*
     * This method generates from a list of products a subset of products for sale
     * @param amountOfItems Total the products that will be sold within subset
     * @param products Existing product list 
     * @return A set containing all products picked
     */
    public static Set<Product> getItemsForSale(int amountOfItems, List<Product> products)
    {	
    	if(products.size() > amountOfItems)
    	{
    		Set<Product> productsForSell = new LinkedHashSet<Product>();
	    	
	    	Random rand = new Random();
	    	
	    	while(amountOfItems > 0)
	    	{
	    		if(productsForSell.add(products.get(rand.nextInt(products.size()))))
	    			amountOfItems--;
	    	}	    		    	
	    	return productsForSell;
    	}
    	else return null;
    }
}
