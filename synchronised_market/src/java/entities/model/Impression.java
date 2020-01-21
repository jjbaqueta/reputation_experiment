package entities.model;

import java.time.LocalTime;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import jason.asSemantics.Agent;

public class Impression<K, V> {
	private Agent appraiser;
	private Agent evaluated;
	private LocalTime time;
	private Dictionary<K, V> ratings;
	
	public Impression(Agent appraiser, Agent evaluated, LocalTime time) {
		this.appraiser = appraiser;
		this.evaluated = evaluated;
		this.time = time;
		this.ratings = new Hashtable<K, V>();
	}

	public void insertRatingsFromList(List<Entry<K, V>> ratings)
	{
		for(Entry<K, V> rate : ratings)
		{
			this.ratings.put(rate.getKey(), rate.getValue());
		}
	}
	
	public void insertRating(K key, V value)
	{
		ratings.put(key, value).equals(value);
	}
	
	public Agent getAppraiser() {
		return appraiser;
	}

	public Agent getEvaluated() {
		return evaluated;
	}

	public LocalTime getTime() {
		return time;
	}

	public Dictionary<K, V> getRatings() {
		return ratings;
	}

	@Override
	public String toString() {
		
		String strRatings = ", ratings={";
		
		for(Enumeration<K> key = ratings.keys(); key.hasMoreElements();)
		{
			strRatings += "(key=" + key + " : value=" + ratings.get(key)+")";
		}
		strRatings += "}";
		
		return "Impression [appraiser=" + appraiser + ", evaluated=" + evaluated + ", time=" + time + strRatings + "]";
	}

	
}
