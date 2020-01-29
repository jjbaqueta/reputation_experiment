package entities.services;

import entities.model.Product;
import enums.ProductClassType;
import enums.ProductDefault;

/*
 * This class implements the Design Pattern Builder considering the Product entity
 */

public class ProductBuilder 
{
	private String name;
	private Double price;
	private Double quality;
	private Integer deliveryTime;
	
	public ProductBuilder (String productName, ProductClassType productClass) throws Exception
	{
		ProductDefault product =  ProductDefault.valueOf(productName);
				
		switch(productClass)
		{
			case ECONOMY:
				economyVersionOf(product);
				break;
			
			case INTERMEDIATE:
				orderIntermediateVersionOf(product);
				break;
				
			case GOOD:
				orderGoodVersionOf(product);
				break;
				
			case GREAT:
				orderGreatVersionOf(product);
				break;
				
			case LUXURIOUS:
				orderLuxuriousVersionOf(product);
				break;
			
			default:
				throw new Exception("Product's name is invalid");
		}
	}
	
	/* 
	 * This method returns an economy version of product specified (specification of product to be built)
	 * @param baseProduct an enum that represents the most simple version of the product (default version)
	 */
	private void economyVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice();
		this.quality = baseProduct.getQuality();
		this.deliveryTime = baseProduct.getDeliveryTime();
	}
	
	/*
	 * This method returns an intermediate version of product specified (specification of product to be built)
	 * @param baseProduct an enum that represents the most simple version of the product (default version)
	 */
	private void orderIntermediateVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice() * 1.2;
		this.quality = 0.3;
		this.deliveryTime = (int) (baseProduct.getDeliveryTime()  - (baseProduct.getDeliveryTime() * 0.1));
	}
	
	/*
	 * This method returns a good version of product specified (specification of product to be built)
	 * @param baseProduct an enum that represents the most simple version of the product (default version)
	 */
	private void orderGoodVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice() * 1.5;
		this.quality = 0.5;
		this.deliveryTime = (int) (baseProduct.getDeliveryTime()  - (baseProduct.getDeliveryTime() * 0.15));
	}
	
	/*
	 * This method returns a great version of product specified (specification of product to be built)
	 * @param baseProduct an enum that represents the most simple version of the product (default version)
	 */
	private void orderGreatVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice() * 2;
		this.quality = 0.8;
		this.deliveryTime = (int) (baseProduct.getDeliveryTime()  - (baseProduct.getDeliveryTime() * 0.2));
	}
	
	/*
	 * This method returns a luxurious version of product specified (specification of product to be built)
	 * @param baseProduct an enum that represents the most simple version of the product (default version)
	 */
	private void orderLuxuriousVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice() * 3;
		this.quality = 1.0;
		this.deliveryTime = (int) (baseProduct.getDeliveryTime()  - (baseProduct.getDeliveryTime() * 0.25));
	}
	
	/*
	 * This method builds a product specification
	 * @return a product
	 */
	public Product build()
	{
		return new Product(name, price, quality, deliveryTime);
	}
}
