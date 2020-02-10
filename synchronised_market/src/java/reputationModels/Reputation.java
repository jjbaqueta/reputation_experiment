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
	 * This method convert an impression to a belief.
	 * Each belief is defined as follow: imp(appraiser, appraised, time, [rating1, rating2, rating3, ...])
	 * @return a belief
	 */
	public Literal getReputationAsLiteral()
	{
		List<Criteria> list = ReputationModel.criteria;
		
		String str = "rep("+ agent.getName() + "," + time + ",";
		
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
