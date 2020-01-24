package enums;

public enum CriteriaType {
	PRICE("price"),
	QUALITY("quality"),
	DELIVERY("delivery");
	
	private String value;
	
	CriteriaType(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}
