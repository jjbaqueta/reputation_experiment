package entities.model;

import entities.model.behaviors.Behavior;
import jason.asSyntax.Literal;

/*
 * This class implements a Product
 * The products are the base elements to negotiation between buyers and sellers 
 */
public class Product 
{	
	private String name;
	private Double price;
	private Double quality;
	private Integer deliveryTime;
	private Behavior salesBehaviorPrice;
	private Behavior salesBehaviorQuality;
	private Behavior salesBehaviorDelivery;
	
	public Product(String name, Double price, Double quality, Integer deliveryTime) 
	{
		this.name = name;
		this.price = price;
		this.quality = quality;
		this.deliveryTime = deliveryTime;
		
		// select behavior in this part
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Double getPrice() 
	{
		return price;
	}

	public void setPrice(Double price) 
	{
		this.price = price;
	}

	public Double getQuality() 
	{
		return quality;
	}

	public void setQuality(Double quality) 
	{
		this.quality = quality;
	}

	public Integer getDeliveryTime() 
	{
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) 
	{
		this.deliveryTime = deliveryTime;
	}
	
	public void setSalesBehaviorPrice(Behavior salesBehaviorPrice) 
	{
		this.salesBehaviorPrice = salesBehaviorPrice;
	}
	
	public Behavior getSalesBehaviorPrice()
	{
		return this.salesBehaviorPrice;
	}
	
	public Behavior getSalesBehaviorQuality() {
		return salesBehaviorQuality;
	}

	public void setSalesBehaviorQuality(Behavior salesBehaviorQuality) {
		this.salesBehaviorQuality = salesBehaviorQuality;
	}

	public Behavior getSalesBehaviorDelivery() {
		return salesBehaviorDelivery;
	}

	public void setSalesBehaviorDelivery(Behavior salesBehaviorDelivery) {
		this.salesBehaviorDelivery = salesBehaviorDelivery;
	}

	public Literal getProductAsLiteral()
	{
		return Literal.parseLiteral("p(" + name + "," + price + "," + quality + "," + deliveryTime + ")");
	}
	
	@Override
	public String toString() 
	{
		return "Product [name=" + name + ", price=" + price + ", quality=" + quality + ", deliveryTime=" + deliveryTime
				+ "]";
	}
}
