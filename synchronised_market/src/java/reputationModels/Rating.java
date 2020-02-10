package reputationModels;

import java.util.Dictionary;
import java.util.Hashtable;

public abstract class Rating 
{
	protected long time;
	protected Dictionary<String, Double> scores;
	
	public Rating(long time) 
	{
		this.time = time;
		this.scores = new Hashtable<String, Double>();
	}
	
	public void setRatings(String criteriaName, Double value)
	{
		scores.put(criteriaName, value);
	}

	public long getTime() 
	{
		return time;
	}

	public void setTime(long time) 
	{
		this.time = time;
	}

	public Dictionary<String, Double> getScores() 
	{
		return scores;
	}

	public void setScores(Dictionary<String, Double> scores) 
	{
		this.scores = scores;
	}
}
