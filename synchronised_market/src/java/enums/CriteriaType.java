package enums;

/*
 * This enum defines the criteria used on the reputation model
 * If necessary more criteria can be added in this enum
 */
public enum CriteriaType 
{
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