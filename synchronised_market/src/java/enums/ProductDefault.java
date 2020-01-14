package enums;

/*
 * This Enum contains the default attributes of each product
 */
public enum ProductDefault {
	TV(1500.0, 0.2, 20), 
	DESKTOP(2000.0, 0.2, 15),
	NOTEBOOK(2500.0, 0.2, 20),
	SMARTPHONE(1500.0, 0.2, 20),
	TABLET(500.0, 0.2, 15);
	
	double price;
	double quality;
	int deliveryTime;
	
	/*
	 * @param price A double that represents the value paid on the product
	 * @param quality A double (at least 0.0 and at most 1.0), it represents the quality of product
	 * @param deliveryTime An integer that represents the total of days needed to product arrives to the client's house 
	 */
	ProductDefault(double price, double quality, int deliveryTime)
	{
		this.price = price;
		this.quality = quality;
		this.deliveryTime = deliveryTime;
		
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public double getQuality()
	{
		return quality;
	}
	
	public int getDeliveryTime()
	{
		return deliveryTime;
	}
}
