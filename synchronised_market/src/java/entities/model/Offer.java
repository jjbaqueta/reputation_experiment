package entities.model;

import entities.model.sellers.Seller;
import entities.services.MarketFacade;
import environments.Market;
import jason.asSyntax.Literal;

/*
 * This class implements an Offer
 * Sellers can offer products to buyers, it turn, buyers can accept an offer according to his preferences
 */
public class Offer 
{
	private Product product;		// Product at offer
	private Seller seller;			// Who sells the product
	private int cnpid;				// Offer's cnpid
	private boolean acceptByReputation;
	private boolean acceptByImage;
	
	public Offer(String product, Double price, Double quality, Integer deleveryTime, Seller seller) 
	{		
		this.product = new Product(product, price, quality, deleveryTime);
		this.seller = seller;
		this.cnpid = 0;
		this.acceptByReputation = true;
		this.acceptByImage = true;
	}

	public Product getProduct() 
	{
		return product;
	}

	public Seller getSeller() 
	{
		return seller;
	}

	public int getCnpid() {
		return cnpid;
	}

	public void setCnpid(int cnpid) {
		this.cnpid = cnpid;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public boolean isAcceptByReputation() {
		return acceptByReputation;
	}

	public void setAcceptByReputation(boolean acceptByReputation) {
		this.acceptByReputation = acceptByReputation;
	}

	public boolean isAcceptByImage() {
		return acceptByImage;
	}

	public void setAcceptByImage(boolean acceptByImage) {
		this.acceptByImage = acceptByImage;
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
	
	public Literal getOfferAsLiteral()
	{
		return Literal.parseLiteral("offer(p(" + product.getName() + "," + 
												 product.getPrice() + "," + 
												 product.getQuality() + "," + 
												 product.getDeliveryTime() + "),"+ 
												 seller.getName() +")");
	}
	
	@Override
	public String toString() 
	{
		return "Offer [product=" + product.getName() + 
					", price=" + product.getPrice() + 
					", quality=" + product.getQuality() + 
					", deleveryTime=" + product.getDeliveryTime() + 
					", seller=" + seller.getName() + "]";
	}
}
