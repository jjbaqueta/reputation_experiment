package entities.model.sellers;

import java.util.List;

import entities.model.Product;

/*
 * This Class implements a GeneralSeller
 * This kind of seller has his shopping profile defined from a specific behavior
 */
public class GeneralSeller extends Seller
{
	
	// Constructor
	public GeneralSeller(String name, double priceMargin, double qualityMargin, double deliveryMargin, List<Product> products) 
	{
		super(name, priceMargin, qualityMargin, deliveryMargin, products);
	}
	
	@Override
	public void defineProductsSalesBehavior() 
	{		
		/* 
		 * Not implemented
		 * This seller keep the original sales behavior of each product in its products list	
		 */
	}
}