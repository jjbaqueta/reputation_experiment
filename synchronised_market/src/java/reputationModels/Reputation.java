package reputationModels;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import entities.model.SimpleAgent;
import entities.services.MarketFacade;
import enums.CriteriaType;
import environments.Market;
import jason.asSyntax.Literal;

public class Reputation {
	private SimpleAgent agent;
	private Dictionary<String, Double> reputationRatings;
	private Dictionary<String, Double> reliabilityRatings;
	private long time;
	
	public Reputation(SimpleAgent agent, long time) 
	{
		this.agent = agent;
		this.reputationRatings = new Hashtable<String, Double>();
		this.reliabilityRatings = new Hashtable<String, Double>();
		this.time = time;
	}
	
	public void setReputationRatings(String criteriaName, Double value)
	{
		reputationRatings.put(criteriaName, value);
	}
	
	public void setReliabilityRatings(String criteriaName, Double value)
	{
		reliabilityRatings.put(criteriaName, value);
	}

	public SimpleAgent getAgent() {
		return agent;
	}

	public Dictionary<String, Double> getReputationRatings() {
		return reputationRatings;
	}

	public Dictionary<String, Double> getReliabilityRatings() {
		return reliabilityRatings;
	}
	
	public long getTime() {
		return time;
	}
	
	/*
	 * This method translates a reputation in literal format to an Object
	 * Literal format:"rep(seller,time,Rprice,Rquality,Rdelivery,Lprice,Lquality,Ldelivery)
	 * @param impList A string that represents the reputation in literal format
	 * @return A reputation in Object format
	 */

	public static Reputation parseReputation(String str)
	{	
		String[] attributes = str.split("rep\\(|\\)")[1].split("\\,");
		
		Reputation rep = new Reputation(Market.sellers[MarketFacade.getSellerIdFrom(attributes[0])], Long.parseLong(attributes[1]));
		
		rep.setReputationRatings(CriteriaType.PRICE.getValue(), Double.parseDouble(attributes[2]));
		rep.setReputationRatings(CriteriaType.QUALITY.getValue(), Double.parseDouble(attributes[3]));
		rep.setReputationRatings(CriteriaType.DELIVERY.getValue(), Double.parseDouble(attributes[4]));
		
		rep.setReliabilityRatings(CriteriaType.PRICE.getValue(), Double.parseDouble(attributes[5]));
		rep.setReliabilityRatings(CriteriaType.QUALITY.getValue(), Double.parseDouble(attributes[6]));
		rep.setReliabilityRatings(CriteriaType.DELIVERY.getValue(), Double.parseDouble(attributes[7]));
		
		return rep;
	}
	
	/*
	 * This method checks the reputation of a given seller
	 * @param priceWeight buyer's preference regarding to price
	 * @paramqualityWeight buyer's preference regarding to quality
	 * @param deliveryWeight buyer's preference regarding to weight
	 * @return If seller's reputation is bigger than 0.5 return true, otherwise, return false
	 */
	public boolean checkReputation(double priceWeight, double qualityWeight, double deliveryWeight)
	{
		double repPrice = convertInterval(reputationRatings.get(CriteriaType.PRICE.getValue())) * convertInterval(reliabilityRatings.get(CriteriaType.PRICE.getValue()));
		double repQuality = convertInterval(reputationRatings.get(CriteriaType.QUALITY.getValue())) * convertInterval(reliabilityRatings.get(CriteriaType.QUALITY.getValue()));
		double repDelivery = convertInterval(reputationRatings.get(CriteriaType.DELIVERY.getValue())) * convertInterval(reliabilityRatings.get(CriteriaType.DELIVERY.getValue()));
		
		double cutScore = 0.8;
		
		if(priceWeight == 1 && qualityWeight == 0 && deliveryWeight == 0)
			return repPrice >= cutScore ? true : false;
			
		else if(priceWeight == 0 && qualityWeight == 1 && deliveryWeight == 0)
			return repQuality >= cutScore ? true : false;
			
		else if(priceWeight == 0 && qualityWeight == 0 && deliveryWeight == 1)
			return repDelivery >= cutScore ? true : false;
			
		else if(priceWeight != 0 && qualityWeight == 0 && deliveryWeight != 0)
			return (repPrice + repDelivery)/2 >= cutScore ? true : false;
			
		else if(priceWeight == 0 && qualityWeight != 0 && deliveryWeight != 0)
			return (repQuality + repDelivery)/2 >= cutScore ? true : false;
			
		else if(priceWeight != 0 && qualityWeight != 0 && deliveryWeight == 0)
			return (repQuality + repPrice)/2 >= cutScore ? true : false;
		
		else
			return (repPrice + repQuality + repDelivery)/3 >= cutScore ? true : false;		
	}
	
	/*
	 * This method translates a given value belonging to the numeric interval [-1, 1] to interval [0, 1]
	 * @param x A Double value that represent the value within the numeric interval [-1, 1]
	 * @return a double value within the numeric interval [0, 1] 
	 */
	private double convertInterval(double x)
	{
		if(x <= -1)
			return 0;
		else if(x >= 1)
			return 1;
		else
			return (1 + x)/2;
	}
	
	/*
	 * This method convert an impression to a belief.
	 * Each belief is defined as follow: imp(appraiser, appraised, time, [rating1, rating2, rating3, ...])
	 * @return a belief
	 */
	public Literal getReputationAsLiteral()
	{
		List<Criteria> list = ReputationModel.criteria;
		String str = "("+ agent.getName() + "," + time + ",";
		
		for(int i = 0; i < list.size(); i++)
		{
			str += reputationRatings.get(list.get(i).getName());
			str += ",";
		}
		
		for(int i = 0; i < list.size() - 1; i++)
		{
			str += reliabilityRatings.get(list.get(i).getName());
			str += ",";
		}
		str += reliabilityRatings.get(list.get(list.size() - 1).getName());
		str += ")";
		
		return Literal.parseLiteral(str);
	}
	
	@Override
	public String toString() {
		
		List<Criteria> list = ReputationModel.criteria;
		String strReputation = ", reputation_ratings={";
		
		for(int i = 0; i < list.size(); i++)
		{
			strReputation += "(key=" + list.get(i).getName() + " : value=" + reputationRatings.get(list.get(i).getName())+")";
		}
		strReputation += "}";
		
		String strReliability = ", reliability ratings={";
		
		for(int i = 0; i < list.size(); i++)
		{
			strReliability += "(key=" + list.get(i).getName() + " : value=" + reliabilityRatings.get(list.get(i).getName())+")";
		}
		strReliability += "}";
		
		
		return "Reputation [agent=" + agent + ", time=" + time  + strReputation + strReliability + "]";
	}
}
