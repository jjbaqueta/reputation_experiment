package entities.services;

import entities.model.SimpleAgent;
import enums.CriteriaType;
import reputationModels.Reputation;

public abstract class ReputationFacade 
{
	/*
	 * Generate a reputation tuple with all attributes set at the lowest value
	 * @param agent Agent who reputation will be set
	 * @param time initial time
	 */
	public static Reputation generateMinRepTuple(SimpleAgent agent, long time)
	{
		Reputation reputation = new Reputation(agent, time);
		
		reputation.setReputationRatings(CriteriaType.PRICE.getValue(), -1.0);
		reputation.setReputationRatings(CriteriaType.QUALITY.getValue(), -1.0);
		reputation.setReputationRatings(CriteriaType.DELIVERY.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.PRICE.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.QUALITY.getValue(), -1.0);
		reputation.setReliabilityRatings(CriteriaType.DELIVERY.getValue(), -1.0);
		
		return reputation;
	}
	
	/*
	 * Generate a reputation tuple with all attributes set at neutral value
	 * @param agent Agent who reputation will be set
	 * @param time initial time
	 */
	public static Reputation generateNeutralRepTuple(SimpleAgent agent, long time)
	{
		Reputation reputation = new Reputation(agent, time);
		
		reputation.setReputationRatings(CriteriaType.PRICE.getValue(), 0.5);
		reputation.setReputationRatings(CriteriaType.QUALITY.getValue(), 0.5);
		reputation.setReputationRatings(CriteriaType.DELIVERY.getValue(), 0.5);
		reputation.setReliabilityRatings(CriteriaType.PRICE.getValue(), 0.5);
		reputation.setReliabilityRatings(CriteriaType.QUALITY.getValue(), 0.5);
		reputation.setReliabilityRatings(CriteriaType.DELIVERY.getValue(), 0.5);
		
		return reputation;
	}	
	
	/*
	 * Generate a reputation tuple with all attributes set at the highest value
	 * @param agent Agent who reputation will be set
	 * @param time initial time
	 */
	public static Reputation generateMaxRepTuple(SimpleAgent agent, long time)
	{
		Reputation reputation = new Reputation(agent, time);
		
		reputation.setReputationRatings(CriteriaType.PRICE.getValue(), 1.0);
		reputation.setReputationRatings(CriteriaType.QUALITY.getValue(), 1.0);
		reputation.setReputationRatings(CriteriaType.DELIVERY.getValue(), 1.0);
		reputation.setReliabilityRatings(CriteriaType.PRICE.getValue(), 1.0);
		reputation.setReliabilityRatings(CriteriaType.QUALITY.getValue(), 1.0);
		reputation.setReliabilityRatings(CriteriaType.DELIVERY.getValue(), 1.0);
		
		return reputation;
	}
}
