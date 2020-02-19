package entities.model.behaviors;

public abstract class Behavior 
{
	protected double controlFactor;
	
	public Behavior(double controlFactor)
	{
		this.controlFactor = controlFactor;
	}
	
	public abstract double getbehaviorValueFor(int x);
	
	public double getControlFactor() 
	{
		return controlFactor;
	}

	public void setControlFactor(double controlFactor) 
	{
		this.controlFactor = controlFactor;
	}
	
	protected double checkInterval(double y)
	{
		if (y <= 0)	
			return 0;
		
		else if (y >= 1) 
			return 1.0;
		
		else 
			return y; 
	}

	@Override
	public String toString() {
		return "Behavior [controlFactor=" + controlFactor + "]";
	}
}
