package reputationModels;

/*
 * This class represents a criterion used during the evaluation of seller's reputation
 * This class store the criterion's name and the type of value associated to it
 */
public class Criteria
{	
	private String name;		//criterion's name
	private Class<?> type;		//criterion's type
	
	public Criteria(String name, Class<?> type) 
	{
		this.name = name;
		this.type = type;
	}

	public String getName() 
	{
		return name;
	}
	
	public Class<?> getType() 
	{
		return type;
	}

	@Override
	public String toString() 
	{
		return "Criteria [name=" + name + ", type=" + type + "]";
	}
}