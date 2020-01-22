package entities.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ReputationModel {
	public static List<Criteria> criteria = new ArrayList<Criteria>();
	
	/*
	 * This method adds a new evaluation criteria in the  list of criteria
	 * @param name Name of the criteria that will be added
	 * @param type Type of the criteria that will be added  
	 */
	public static void insertNewCriteria(String name, Class<?> type)
	{
		criteria.add(new Criteria(name, type));
	}
}
