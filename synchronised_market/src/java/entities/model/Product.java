package entities.model;

import entities.model.behaviors.Behavior;
import jason.asSyntax.Literal;

/*
 * This class implements a Product
 * The products are the base elements to negotiation between buyers and sellers 
 */
public class Product implements Cloneable
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
		this.name = name.toUpperCase();
		this.price = price;
		this.quality = quality;
		this.deliveryTime = deliveryTime;
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
	
	public void setProductBehaviors(Behavior priceBehavior, Behavior qualityBehavior, Behavior deliveryBehavior)
	{
		this.salesBehaviorPrice = priceBehavior;
		this.salesBehaviorPrice = qualityBehavior;
		this.salesBehaviorPrice = deliveryBehavior;
	}

	public Literal getProductAsLiteral()
	{
		return Literal.parseLiteral("p(" + name.toLowerCase() + "," + price + "," + quality + "," + deliveryTime + ")");
	}
	
	@Override
	public String toString() 
	{
		return "Product [name=" + name + ", price=" + price + ", quality=" + quality + ", deliveryTime=" + deliveryTime
				+ "]";
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		return result;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
            return this;
        }
    }
}
