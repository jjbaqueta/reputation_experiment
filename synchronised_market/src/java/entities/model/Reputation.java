package entities.model;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import entities.services.MarketFacade;
import enums.CriteriaType;
import environments.Market;
import jason.asSyntax.Literal;
import reputationModels.ReputationModel;

public class Reputation {
	private SimpleAgent agent;
	private Dictionary<String, Double> reputationRatings;
	private Dictionary<String, Double> reliabilityRatings;
	
	public Reputation(SimpleAgent agent) 
	{
		this.agent = agent;
		this.reputationRatings = new Hashtable<String, Double>();
		this.reliabilityRatings = new Hashtable<String, Double>();
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
	
	/*
	 * This method translates an list of impressions (literal) to an list of objects
	 * Literal format:"[imp(buyer,agent,time,rating),imp(buyer,agent,time,rating), ...]
	 * @param impList A string that represents the input literal
	 * @return An List of impressions
	 * [rep(seller4,0.2444515860142969,0.583149137613096,-0.06333333312844877,0.2853828490741238,0.45785767721015114,0.07793446241193858),rep(seller1,0.3390448465793256,0.6759981109795488,0.354347406886662,0.2863351384607712,0.5296134376785026,0.32038369171309056),rep(seller2,0.271439518030812,0.704459471782712,-0.04761904761904745,0.28931928731158096,0.7117123994234746,-0.021909189166472675)]
	 */
	public static Reputation parseReputation(String str)
	{	
		String[] attributes = str.split("rep\\(|\\)")[1].split("\\,");
		
		Reputation rep = new Reputation(Market.sellers[MarketFacade.getSellerIdFrom(attributes[0])]);
		
		rep.setReputationRatings(CriteriaType.PRICE.getValue(), Double.parseDouble(attributes[1]));
		rep.setReputationRatings(CriteriaType.QUALITY.getValue(), Double.parseDouble(attributes[2]));
		rep.setReputationRatings(CriteriaType.DELIVERY.getValue(), Double.parseDouble(attributes[3]));
		
		rep.setReliabilityRatings(CriteriaType.PRICE.getValue(), Double.parseDouble(attributes[4]));
		rep.setReliabilityRatings(CriteriaType.QUALITY.getValue(), Double.parseDouble(attributes[5]));
		rep.setReliabilityRatings(CriteriaType.DELIVERY.getValue(), Double.parseDouble(attributes[6]));
		
		return rep;
	}
	
	public boolean checkReputation(double priceWeight, double qualityWeight, double timeWeight)
	{
		double repPrice = convertInterval(reputationRatings.get(CriteriaType.PRICE.getValue())) * convertInterval(reliabilityRatings.get(CriteriaType.PRICE.getValue()) * priceWeight);
		double repQuality = convertInterval(reputationRatings.get(CriteriaType.QUALITY.getValue())) * convertInterval(reliabilityRatings.get(CriteriaType.QUALITY.getValue()) * qualityWeight);
		double repDelivery = convertInterval(reputationRatings.get(CriteriaType.DELIVERY.getValue())) * convertInterval(reliabilityRatings.get(CriteriaType.DELIVERY.getValue()) * timeWeight);
		
		if(priceWeight == 1 && qualityWeight == 0 && timeWeight == 0)
			return repPrice >= 0.5 ? true : false;
		else if(priceWeight == 0 && qualityWeight == 1 && timeWeight == 0)
			return repQuality >= 0.5 ? true : false;
		else if(priceWeight == 0 && qualityWeight == 0 && timeWeight == 1)
			return repDelivery >= 0.5 ? true : false;
			
		else if(priceWeight != 0 && qualityWeight == 0 && timeWeight != 0)
			return (repPrice + repDelivery)/2 >= 0.5 ? true : false;
		else if(priceWeight == 0 && qualityWeight != 0 && timeWeight != 0)
			return (repQuality + repDelivery)/2 >= 0.5 ? true : false;
		else if(priceWeight != 0 && qualityWeight != 0 && timeWeight == 0)
			return (repQuality + repPrice)/2 >= 0.5 ? true : false;
		
		else
			return (repPrice + repQuality + repDelivery)/3 >= 0.5 ? true : false;		
	}
	
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
		String str = "("+ agent.getName() + ",";
		
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
		
		
		return "Reputation [agent=" + agent + strReputation + strReliability + "]";
	}
}
