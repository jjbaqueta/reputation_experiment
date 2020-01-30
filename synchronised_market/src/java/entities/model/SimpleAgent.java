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
		return getClass().toString().split("\\.")[2];
	}
	
	@Override
	public int compareTo(SimpleAgent agent) 
	{
		return name.compareTo(agent.getName());
	}
}
