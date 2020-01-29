package enums;

/*
 * This Enum contains the default values of the attributes for each product
 */
public enum ProductDefault 
{
	TV(1500.0, 0.2, 20), 
	DESKTOP(2000.0, 0.2, 15),
	NOTEBOOK(2500.0, 0.2, 20),
	SMARTPHONE(1500.0, 0.2, 20),
	TABLET(500.0, 0.2, 15);
	
	//Attributes
	double price;
	double quality;
	int deliveryTime;
	
	/*
	 * This method defines the values for each attribute from this enum
	 * @param price A double that represents the value paid on a product
	 * @param quality A double (at least 0.0 and at most 1.0), it represents the quality of product
	 * @param deliveryTime An integer that represents the total of days required for the product to arrive at the client's house 
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
