package enums;

/*
 * This Enum is used to create variations on the default attributes of a product
 * The price, quality and delivery time of a given product are defined according to its class
 * The default values of attributes of a specific product can be seen in {@link enums.ProductDefault}  
 */

public enum ProductClassType 
{
	ECONOMY,		// keeps the default attributes 
	INTERMEDIATE,	// price increases up to 20%, quality increases up to 30% and delivery time is reduced up to 10% 
	GOOD,			// price increases up to 50%, quality increases up to 50% and delivery time is reduced up to 15%
	GREAT,			// price increases up to 100%, quality increases up to 80% and delivery time is reduced up to 20%
	LUXURIOUS		// price increases up to 200%, quality increases up to 100% and delivery time is reduced up to 25%
}
