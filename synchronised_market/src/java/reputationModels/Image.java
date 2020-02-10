package reputationModels;

import java.util.List;

import entities.model.sellers.Seller;
import entities.services.MarketFacade;
import enums.CriteriaType;
import environments.Market;
import jason.asSyntax.Literal;

public class Image extends Rating
{
	private Seller seller;
	private String productName;		//role
	
	public Image(Seller seller, String productName, long time) 
	{
		super(time);
		
		this.seller = seller;
		this.productName = productName;
	}
	
	public Seller getSeller() 
	{
		return seller;
	}
	
	public void setSeller(Seller seller) 
	{
		this.seller = seller;
	}
	
	public String getProductName() 
	{
		return productName;
	}
	
	public void setProductName(String productName) 
	{
		this.productName = productName;
	}
	
	/*
	 * This method translates an image in literal format to an Object
	 * Literal format:"image(seller,product_name,time,[price,quality,delivery])"
	 * @param impList A string that represents the image in literal format
	 * @return An Image in Object format
	 */
	public static Image parseImage(String strImage)
	{	
		String[] attributes = strImage.split("image\\(|\\,\\[|\\]\\)");	//output: [0]: seller,product_name,time; [1]: price,quality,delivery
		String[] informations = attributes[1].split("\\,");						//output: [0]: seller; [1]: product_name; [2]: time
		String[] ratings = attributes[2].split("\\,");							//output: [0]: price; [1]: quality; [2]: delivery

		Seller seller = Market.sellers[MarketFacade.getSellerIdFrom(informations[0])];
		String productName = informations[1];
		long time = Long.parseLong(informations[2]);
		
		double ratingPrice = Double.parseDouble(ratings[0]);
		double ratingQuality = Double.parseDouble(ratings[1]);
		double ratingDelivery = Double.parseDouble(ratings[2]);
		
		Image img = new Image(seller, productName, time);
		
		img.setRatings(CriteriaType.PRICE.getValue(), ratingPrice);
		img.setRatings(CriteriaType.QUALITY.getValue(), ratingQuality);
		img.setRatings(CriteriaType.DELIVERY.getValue(), ratingDelivery);
		
		return img;
	}
	
	/*
	 * This method convert an image to a literal.
	 * Each literal is defined as follow: image(seller,product_name,time,[price,quality,delivery])
	 * @return a literal
	 */
	public Literal getReputationAsLiteral()
	{
		List<Criteria> list = ReputationModel.criteria;
		String str = "image("+ seller.getName() + "," + productName + "," + time + ",[";
		
		for(int i = 0; i < list.size() - 1; i++)
		{
			str += scores.get(list.get(i).getName());
			str += ",";
		}
		str += scores.get(list.get(list.size() - 1).getName());
		str += "])";
		
		return Literal.parseLiteral(str);
	}
	
	@Override
	public String toString() {
		return "Image [seller=" + seller + ", productName=" + productName + ", time=" + time + ", imageRatings="
				+ scores + "]";
	}
}