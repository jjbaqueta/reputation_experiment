package entities.model.behaviors;

public class ParableIncDecBehavior extends Behavior
{

	public ParableIncDecBehavior(double controlFactor)
	{
		super(controlFactor);
	}
	
	/* 
	 * In this method the return value increases up to 1 and after that decreases
	 * @param x An integer value that represents a variation on x-axis of the cartesian plane
	 * @return resultant value from equation
	 */
	@Override
	public double getbehaviorValueFor(int x) 
	{
		double y = (-1.0/controlFactor) * Math.pow(x, 2) + x;	//y = f(x) : f(x) = (-1/cF) * x^2 + x;
		return checkInterval(y); 
	}

}
