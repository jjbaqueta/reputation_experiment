package entities.model;

import entities.services.MarketFacade;
import environments.Market;

/*
 * This class implements an Offer
 * Sellers can offer products to buyers, it turn, buyers can accept an offer according to his preferences
 */
public class Offer 
{
	private String product;			// Product's name
	private Double price;			// Criteria 1: product's price
	private Double quality;			// Criteria 2: product's quality
	private Integer deliveryTime;	// Criteria 3: delivery time
	private Seller seller;			// Who sells the product
	
	public Offer(String product, Double price, Double quality, Integer deleveryTime, Seller seller) 
	{		
		this.product = product;
		this.price = price;
		this.quality = quality;
		this.deliveryTime = deleveryTime;
		this.seller = seller;
	}

	public String getProduct() 
	{
		return product;
	}

	public Double getPrice() 
	{
		return price;
	}

	public Double getQuality() 
	{
		return quality;
	}

	public Integer getDeliveryTime() 
	{
		return deliveryTime;
	}

	public Seller getSeller() 
	{
		return seller;
	}

	/*
	 * This method translates an offer in literal format to an Offer (object)
	 * The literal form of the offer is: offer(p(product_name,product_price,product_quality,product_delivery),seller_name)
	 * @param strOffer A string that represents the offer in literal format
	 * @return An Offer 
	 */
	public static Offer parseOffer(String strOffer)
	{
		String[] attributes = strOffer.split("offer\\(p\\(|\\)*\\,|\\)");;
		
		String productName = attributes[1];
		Double price =  Double.parseDouble(attributes[2]);
		Double quality = Double.parseDouble(attributes[3]);
		Integer delivery = Integer.parseInt(attributes[4]);
		Seller seller = Market.sellers[MarketFacade.getSellerIdFrom(attributes[5])];
		
		return new Offer(productName, price, quality, delivery, seller);
	}	
	
	/*
	 * This method creates an Offer from a product (in literal format) and its respective seller
	 * The literal form of a product is:"p(product_name,product_price,product_quality,product_delivery)"
	 * @param strProduct A string that represents the product in literal format
	 * @param sellerName A string that represents the seller's name
	 * @return An Offer
	 */
	public static Offer parseProposal(String strProduct, Seller seller)
	{
		String[] attributes = strProduct.split("p\\(|\\)")[1].split("\\,");
		
		String pName = attributes[0];
		double pPrice = Double.parseDouble(attributes[1]);
		double pQuality = Double.parseDouble(attributes[2]);
		int pDelivery = Integer.parseInt(attributes[3]);
		
		return new Offer(pName, pPrice, pQuality, pDelivery, seller);
	}
	
	@Override
	public String toString() 
	{
		return "Offer [product=" + product + ", price=" + price + ", quality=" + quality + ", deleveryTime="
				+ deliveryTime + ", seller=" + seller.getName() + "]";
	}
}
