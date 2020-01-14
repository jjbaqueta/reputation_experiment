package enums;

/*
 * This Enum is used to create variations on the default attributes from a product
 * The price, quality and delivery time of a given product are computed according to its class
 * The default values for products attributes can be seen in {@link enums.ProductDefault}  
 */

public enum ProductClassType {
	ECONOMY,		// keeps default attributes 
	INTERMEDIATE,	// Price increase up to 20%, quality on 30% and delivery time reduce in 10% 
	GOOD,			// Price increase up to 50%, quality on 50% and delivery time reduce in 15%
	GREAT,			// Price increase up to 100%, quality on 80% and delivery time reduce in 20%
	LUXURIOUS		// Price increase up to 200%, quality on 100% and delivery time reduce in 25%
}
