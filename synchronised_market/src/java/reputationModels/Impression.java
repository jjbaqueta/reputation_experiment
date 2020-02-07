package reputationModels;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import entities.model.SimpleAgent;
import entities.model.buyers.Buyer;
import entities.model.sellers.Seller;
import entities.services.MarketFacade;
import enums.CriteriaType;
import environments.Market;
import jason.asSyntax.Literal;

public class Impression {
	private SimpleAgent appraiser;
	private SimpleAgent appraised;
	private long time;
	private Dictionary<String, Object> ratings;
	
	public Impression(SimpleAgent appraiser, SimpleAgent appraised, long time) 
	{
		this.appraiser = appraiser;
		this.appraised = appraised;
		this.time = time;
		this.ratings = new Hashtable<String, Object>();
	}
	
	public <T> void setRating(String criteriaName, T value)
	{
		ratings.put(criteriaName, value);
	}
	
	public SimpleAgent getAppraiser() {
		return appraiser;
	}

	public SimpleAgent getAppraised() {
		return appraised;
	}

	public long getTime() {
		return time;
	}

	public Dictionary<String, Object> getRatings() {
		return ratings;
	}
	
	/*
	 * This method translates an list of impressions (literal) to an list of objects
	 * Literal format:"[imp(buyer,agent,time,rating),imp(buyer,agent,time,rating), ...]
	 * @param impList A string that represents the input literal
	 * @return An List of impressions
	 */
	public static Impression parseImpression(String strImpression)
	{	
		String[] attributes = strImpression.split("imp\\(|\\,\\[|\\]\\)");	//output: [0]: buyer,agent,time; [1]: price,quality,delivery
		String[] informations = attributes[1].split("\\,");					//output: [0]: buyer; [1]: agent; [2]: time
		String[] ratings = attributes[2].split("\\,");						//output: [0]: price; [1]: quality; [2]: delivery

		Buyer buyer = Market.buyers[MarketFacade.getBuyerIdFrom(informations[0])];
		Seller seller = Market.sellers[MarketFacade.getSellerIdFrom(informations[1])];
		long time = Long.parseLong(informations[2]);
		
		double ratingPrice = Double.parseDouble(ratings[0]);
		double ratingQuality = Double.parseDouble(ratings[1]);
		double ratingDelivery = Double.parseDouble(ratings[2]);
		
		Impression imp = new Impression(buyer, seller , time);
		
		imp.setRating(CriteriaType.PRICE.getValue(), ratingPrice);
		imp.setRating(CriteriaType.QUALITY.getValue(), ratingQuality);
		imp.setRating(CriteriaType.DELIVERY.getValue(), ratingDelivery);
		
		return imp;
	}
	
	/*
	 * This method convert an impression to a belief.
	 * Each belief is defined as follow: imp(appraiser, appraised, time, [rating1, rating2, rating3, ...])
	 * @return a belief
	 */
	public Literal getImpressionAsLiteral()
	{
		List<Criteria> list = ReputationModel.criteria;
		String strCriteria = "[";
		
		for(int i = 0; i < list.size() - 1; i++)
		{
			strCriteria += ratings.get(list.get(i).getName());
			strCriteria += ",";
		}
		strCriteria += ratings.get(list.get(list.size() - 1).getName());
		strCriteria += "]";
		
		return Literal.parseLiteral("imp("+appraiser.getName()+","+appraised.getName()+","+time+","+strCriteria+")");
	}
	
	@Override
	public String toString() {
		
		List<Criteria> list = ReputationModel.criteria;
		String strRatings = ", ratings={";
		
		for(int i = 0; i < list.size(); i++)
		{
			strRatings += "(key=" + list.get(i).getName() + " : value=" + ratings.get(list.get(i).getName())+")";
		}
		strRatings += "}";
		
		return "Impression [appraiser=" + appraiser.getName() + ", appraised=" + appraised.getName() + ", time=" + time + strRatings + "]";
	}

	
}
