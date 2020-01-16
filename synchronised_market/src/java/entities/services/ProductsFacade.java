package entities.services;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import entities.model.Product;
import enums.ProductClassType;
import enums.ProductDefault;

public class ProductsFacade {
	
	/*
	 * This method generates a list that contains only tvs
	 * @return a list of tvs 
	 */
	public static List<Product> generateListOfTVs() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
			
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.LUXURIOUS).build());
			
		return products;
	}
	
	/*
	 * This method generates a list that contains only notebooks
	 * @return a list of notebooks 
	 */
	public static List<Product> generateListOfNotebooks() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
			
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.LUXURIOUS).build());
			
		return products;
	}
	
	/*
	 * This method generates a list that contains only desktops
	 * @return a list of desktops 
	 */
	public static List<Product> generateListOfDesktops() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
			
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.LUXURIOUS).build());
			
		return products;
	}
	
	/*
	 * This method generates a list that contains only smartphones
	 * @return a list of smartphones 
	 */
	public static List<Product> generateListOfSmartphones() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
			
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.LUXURIOUS).build());
			
		return products;
	}
	
	/*
	 * This method generates a list that contains only tablets
	 * @return a list of tablets 
	 */
	public static List<Product> generateListOfTablets() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
			
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.LUXURIOUS).build());
			
		return products;
	}
	
	/*
	 * This method generates a list that contains all possible combinations of products
	 * @return a complete list of products 
	 */
	public static List<Product> generateCompleteListOfProducts() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
			
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.LUXURIOUS).build());

		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.LUXURIOUS).build());
		
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.LUXURIOUS).build());
		
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.LUXURIOUS).build());
		
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.ECONOMY).build());
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.INTERMEDIATE).build());
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.GOOD).build());
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.GREAT).build());
		products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.LUXURIOUS).build());
		
		return products;
	}
	
    /*
     * This method generates a subset of products from a list of products 
     * @param amountOfItems size of subset generated, the value this parameter must be at most equals to the size of list of products
     * @param products list of products used to create the subset 
     * @return A set of products of size equals the amountOfItems parameter
     */
    public static Set<Product> getSubsetFrom(int amountOfItems, List<Product> products)
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
    
	/*
     * This method shows the products from a list
     * @param products A list of Products
     */
    public static void showProducts(List<Product> products)
    {
    	System.out.println("\n--------------------- PRODUCTS AVAILABLE (DEFAULT) --------------------\n");
    	
    	for(Product p : products)
    	{
    		System.out.println("   -> " + p);
    	}
    }
}
