package entities.model;

public class Criteria{
	private String name;
	private Class<?> type;
	
	public Criteria(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public Class<?> getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Criteria [name=" + name + ", type=" + type + "]";
	}
}