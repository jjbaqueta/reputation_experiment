package entities.model;

/*
 * This class implements an Offer
 * Sellers can do offers for products
 * Offers must be defined considering the evaluation criteria
 */

public class Offer {
	
	private String product;			// Product's name
	private Double price;			// Criteria 1: product's price
	private Double quality;			// Criteria 2: product's quality
	private Integer deliveryTime;	// Criteria 3: delivery time
	private String seller;			// Who sells the product
	
	public Offer(String product, Double price, Double quality, Integer deleveryTime, String seller) {
			
		this.product = product;
		this.price = price;
		this.quality = quality;
		this.deliveryTime = deleveryTime;
		this.seller = seller;
	}

	public String getProduct() {
		return product;
	}

	public Double getPrice() {
		return price;
	}

	public Double getQuality() {
		return quality;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public String getSeller() {
		return seller;
	}

	/*
	 * This method translates an offer(belief) to an offer(object)
	 * Belief format:"offer(p(product_name,product_price,product_quality,product_delivery),seller_name)"
	 * @param strOffer A string that represents the input belief
	 * @return An object offer 
	 */
	public static Offer parseOffer(String strOffer)
	{
		String[] attributes = strOffer.split("offer\\(p\\(|\\)*\\,|\\)");;
		
		String productName = attributes[1];
		Double price =  Double.parseDouble(attributes[2]);
		Double quality = Double.parseDouble(attributes[3]);
		Integer delivery = Integer.parseInt(attributes[4]);
		String sellerName = attributes[5];
		
		return new Offer(productName, price, quality, delivery, sellerName);
	}
	
	
	/*
	 * This method translates an proposal(belief) to an offer(object)
	 * Belief format:"p(product_name,product_price,product_quality,product_delivery)"
	 * @param strOffer A string that represents the input belief
	 * @param sellerName A string that represents the seller
	 * @return An object offer 
	 */
	public static Offer parseProposal(String strOffer, String sellerName)
	{
		String[] attributes = strOffer.split("p\\(|\\)")[1].split("\\,");
		
		String pName = attributes[0];
		double pPrice = Double.parseDouble(attributes[1]);
		double pQuality = Double.parseDouble(attributes[2]);
		int pDelivery = Integer.parseInt(attributes[3]);
		
		return new Offer(pName, pPrice, pQuality, pDelivery, sellerName);
	}
	
	@Override
	public String toString() {
		return "Offer [product=" + product + ", price=" + price + ", quality=" + quality + ", deleveryTime="
				+ deliveryTime + ", seller=" + seller + "]";
	}
}
