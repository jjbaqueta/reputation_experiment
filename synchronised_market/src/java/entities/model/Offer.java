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

	
	@Override
	public String toString() {
		return "Offer [product=" + product + ", price=" + price + ", quality=" + quality + ", deleveryTime="
				+ deliveryTime + ", seller=" + seller + "]";
	}
}
