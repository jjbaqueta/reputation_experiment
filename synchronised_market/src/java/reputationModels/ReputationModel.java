package reputationModels;

import java.util.ArrayList;
import java.util.List;

/*
 * This class implements the base of a reputation model used in our experiments
 * In this class is stored the list of criteria that it will be used in our experiments
 */
public abstract class ReputationModel 
{
	public static List<Criteria> criteria = new ArrayList<Criteria>();	//list of criteria
	
	/*
	 * This method adds a new evaluation criterion in the list
	 * @param name String that represents the name of the criteria added
	 * @param type Type of the criteria added  
	 */
	public static void insertNewCriteria(String name, Class<?> type)
	{
		criteria.add(new Criteria(name, type));
	}
}
