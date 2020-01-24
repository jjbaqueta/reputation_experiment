package entities.model;

import jason.asSemantics.Agent;

public abstract class SimpleAgent extends Agent implements Comparable<SimpleAgent>{
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SimpleAgent [name=" + name + "]";
	}

	@Override
	public int compareTo(SimpleAgent agent) {
		return name.compareTo(agent.getName());
	}
}
