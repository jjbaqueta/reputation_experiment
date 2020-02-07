package entities.model;

import jason.asSemantics.Agent;

public abstract class SimpleAgent extends Agent implements Comparable<SimpleAgent>
{
	private String name;

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getMyType()
	{
		String[] path = getClass().toString().split("\\."); 
		return path[path.length - 1];
	}
	
	@Override
	public int compareTo(SimpleAgent agent) 
	{
		return name.compareTo(agent.getName());
	}
}
