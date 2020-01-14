package entities.model;

import jason.asSemantics.Agent;

public abstract class SimpleAgent extends Agent{
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
}
