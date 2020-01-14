package entities.services;

import entities.model.Product;
import enums.ProductClassType;
import enums.ProductDefault;

/*
 * This class implements the Builder Design Pattern considering the Product entity
 */

public class ProductBuilder {
	
	private String name;
	private Double price;
	private Double quality;
	private Integer deliveryTime;
	
	public ProductBuilder (String productName, ProductClassType productClass) throws Exception
	{
		ProductDefault defProd =  ProductDefault.valueOf(productName);
				
		switch(productClass)
		{
			case ECONOMY:
				orderEconomyVersionOf(defProd);
				break;
			
			case INTERMEDIATE:
				orderIntermediateVersionOf(defProd);
				break;
				
			case GOOD:
				orderGoodVersionOf(defProd);
				break;
				
			case GREAT:
				orderGreatVersionOf(defProd);
				break;
				
			case LUXURIOUS:
				orderLuxuriousVersionOf(defProd);
				break;
			
			default:
				throw new Exception("Product's name is invalid");
		}
	}
	
	/* 
	 * This method specifies an economy version of product specified
	 * Default values details: @see enums.ProductDefault
	 * @param baseProduct the most simple version of the product (default version)
	 */
	private void orderEconomyVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice();
		this.quality = baseProduct.getQuality();
		this.deliveryTime = baseProduct.getDeliveryTime();
	}
	
	/*
	 * This method specifies an intermediate version of product specified
	 * Default values details: @see enums.ProductDefault
	 * @param baseProduct the most simple version of the product (default version)
	 * @return a specification of product to be built
	 */
	private void orderIntermediateVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice() * 1.2;
		this.quality = 0.3;
		this.deliveryTime = (int) (baseProduct.getDeliveryTime()  - (baseProduct.getDeliveryTime() * 0.1));
	}
	
	/*
	 * This method specifies a good version of product specified
	 * Default values details: @see enums.ProductDefault
	 * @param baseProduct the most simple version of the product (default version)
	 * @return a specification of product to be built
	 */
	private void orderGoodVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice() * 1.5;
		this.quality = 0.5;
		this.deliveryTime = (int) (baseProduct.getDeliveryTime()  - (baseProduct.getDeliveryTime() * 0.15));
	}
	
	/*
	 * This method specifies a great version of product specified
	 * Default values details: @see enums.ProductDefault
	 * @param baseProduct the most simple version of the product (default version)
	 * @return a specification of product to be built
	 */
	private void orderGreatVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice() * 2;
		this.quality = 0.8;
		this.deliveryTime = (int) (baseProduct.getDeliveryTime()  - (baseProduct.getDeliveryTime() * 0.2));
	}
	
	/*
	 * This method specifies a luxurious version of product specified
	 * Default values details: @see enums.ProductDefault
	 * @param baseProduct the most simple version of the product (default version)
	 * @return a specification of product to be built
	 */
	private void orderLuxuriousVersionOf(ProductDefault baseProduct)
	{
		this.name = baseProduct.name();
		this.price = baseProduct.getPrice() * 3;
		this.quality = 1.0;
		this.deliveryTime = (int) (baseProduct.getDeliveryTime()  - (baseProduct.getDeliveryTime() * 0.25));
	}
	
	/*
	 * This method builds a product
	 * @return a product
	 */
	public Product build()
	{
		return new Product(name, price, quality, deliveryTime);
	}
}
