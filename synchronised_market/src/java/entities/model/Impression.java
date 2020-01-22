package entities.model;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import jason.asSyntax.Literal;

public class Impression {
	private SimpleAgent appraiser;
	private SimpleAgent evaluated;
	private long time;
	private Dictionary<String, Object> ratings;
	
	public Impression(SimpleAgent appraiser, SimpleAgent evaluated, long time) 
	{
		this.appraiser = appraiser;
		this.evaluated = evaluated;
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

	public SimpleAgent getEvaluated() {
		return evaluated;
	}

	public long getTime() {
		return time;
	}

	public Dictionary<String, Object> getRatings() {
		return ratings;
	}
		
	/*
	 * This method convert an impression to a belief.
	 * Each belief is defined as follow: imp(appraiser, evaluated, [rating1, rating2, rating3, ...])[add_time(time)]
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
		
		return Literal.parseLiteral("imp("+appraiser.getName()+","+evaluated.getName()+","+strCriteria+")[add_time("+time+")]");
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
		
		return "Impression [appraiser=" + appraiser + ", evaluated=" + evaluated + ", time=" + time + strRatings + "]";
	}

	
}
