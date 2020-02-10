package entities.services;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import entities.model.Product;
import enums.BehaviorPattern;
import enums.ProductClassType;
import enums.ProductDefault;

/*
 * This class implements a set of operations regarding to products
 */
public abstract class ProductsFacade 
{	
	/*
	 * This method generates a list which contains only TVs
	 * @return a list of TVs 
	 */
	public static List<Product> generateListOfTVs() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
		
		for(int i = 0; i < 5; i++)
		{
			products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
			products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.INTERMEDIATE).build());
			products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.GOOD).build());
			products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.GREAT).build());
			products.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.LUXURIOUS).build());
		}
			
		return products;
	}
	
	/*
	 * This method generates a list which contains only notebooks
	 * @return a list of notebooks 
	 */
	public static List<Product> generateListOfNotebooks() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
		
		for(int i = 0; i < 5; i++)
		{	
			products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.ECONOMY).build());
			products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.INTERMEDIATE).build());
			products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.GOOD).build());
			products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.GREAT).build());
			products.add(new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.LUXURIOUS).build());
		}
			
		return products;
	}
	
	/*
	 * This method generates a list which contains only desktop PCs
	 * @return a list of desktop PCs 
	 */
	public static List<Product> generateListOfDesktops() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
		
		for(int i = 0; i < 5; i++)
		{
			products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
			products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.INTERMEDIATE).build());
			products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.GOOD).build());
			products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.GREAT).build());
			products.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.LUXURIOUS).build());
		}
			
		return products;
	}
	
	/*
	 * This method generates a list which contains only smartphones
	 * @return a list of smartphones 
	 */
	public static List<Product> generateListOfSmartphones() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
		
		for(int i = 0; i < 5; i++)
		{
			products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.ECONOMY).build());
			products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.INTERMEDIATE).build());
			products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.GOOD).build());
			products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.GREAT).build());
			products.add(new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.LUXURIOUS).build());
		}
		
		return products;
	}
	
	/*
	 * This method generates a which that contains only tablets
	 * @return a list of tablets 
	 */
	public static List<Product> generateListOfTablets() throws Exception
	{
		List<Product> products = new ArrayList<Product>();
		
		for(int i = 0; i < 5; i++)
		{
			products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.ECONOMY).build());
			products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.INTERMEDIATE).build());
			products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.GOOD).build());
			products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.GREAT).build());
			products.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.LUXURIOUS).build());
		}
		
		return products;
	}
	
	/*
	 * This method generates a list which contains all possible combinations of products
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
	 * This method generates a list which contains all possible combinations of products
	 * @return a complete list of products 
	 */
	public static List<Product> customListOfProducts(int controlFactor) throws Exception
	{
		List<Product> products = new ArrayList<Product>();
		
		Product p1 = new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build();
		
		p1.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, controlFactor));
		p1.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, controlFactor));
		p1.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, controlFactor));
		
		products.add(p1);
		
		Product p2 = new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build();
		
		p2.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, controlFactor));
		p2.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, controlFactor));
		p2.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, controlFactor));
		
		products.add(p2);
		
		Product p3 = new ProductBuilder(ProductDefault.NOTEBOOK.name(), ProductClassType.ECONOMY).build();
		
		p3.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.EXPONENTIAL_INCREASING, controlFactor));
		p3.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.EXPONENTIAL_INCREASING, controlFactor));
		p3.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.EXPONENTIAL_INCREASING, controlFactor));
		
		products.add(p3);
		
		Product p4 = new ProductBuilder(ProductDefault.SMARTPHONE.name(), ProductClassType.ECONOMY).build();
		
		p4.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.PARABLE_INC_DEC, controlFactor));
		p4.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.PARABLE_INC_DEC, controlFactor));
		p4.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.PARABLE_INC_DEC, controlFactor));
		
		products.add(p4);
		
		Product p5 = new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.ECONOMY).build();
		
		p5.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_DECREASING, controlFactor));
		p5.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.EXPONENTIAL_DECREASING, controlFactor));
		p5.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.PARABLE_DEC_INC, controlFactor));
		
		products.add(p5);
		
		return products;
	}
	
    /*
     * This method generates a subset of products from available list of products 
	 * @param amountOfItems Integer value that represents the number of products that the buyer wants buying
	 * @param availableProducts List of Products available to sell
     * @return A set of products of size equals the amountOfItems parameter
     */
    public static Set<Product> getSubsetFrom(int amountOfItems, List<Product> availableProducts)
    {	
    	if(availableProducts.size() >= amountOfItems)
    	{
    		Set<Product> productsForSell = new LinkedHashSet<Product>();
	    	
	    	Random rand = new Random();
	    	
	    	while(amountOfItems > 0)
	    	{
	    		if(productsForSell.add(availableProducts.get(rand.nextInt(availableProducts.size()))))
	    			amountOfItems--;
	    	}	    		    	
	    	return productsForSell;
    	}
    	else return null;
    }
    
	
    /*
     * This method generates a subset of products from available list of products (always picks the three last items from list)
	 * @param amountOfItems Integer value that represents the number of products that the buyer wants buying
	 * @param availableProducts List of Products available to sell
     * @return A set of products of size equals the amountOfItems parameter
     */
    public static Set<Product> getNoRadomSubsetFrom(int amountOfItems, List<Product> availableProducts)
    {	
    	if(availableProducts.size() > amountOfItems)
    	{
    		Set<Product> productsForSell = new LinkedHashSet<Product>();
	    	
	    	while(amountOfItems > 0)
	    	{
	    		if(productsForSell.add(availableProducts.get(amountOfItems - 1)))
	    			amountOfItems--;
	    	}	    		    	
	    	return productsForSell;
    	}
    	else return null;
    }
    
	/*
     * This method shows a list of products
     * @param products A list of products
     */
    public static void showProducts(List<Product> products)
    {
    	System.out.println("\n--------------------- PRODUCTS AVAILABLE (DEFAULT) --------------------\n");
    	
    	for(Product p : products)
    		System.out.println("   -> " + p);
    }
}
