package entities.model.behaviors;

public class ExponantialDecreaseBehavior extends Behavior
{

	public ExponantialDecreaseBehavior(double controlFactor)
	{
		super(controlFactor);
	}
	
	/* 
	 * In this method the return value decreases according to x value increases (exponentially)
	 * The greater x the smaller y
	 * @param x An integer value that represents a variation on x-axis of the cartesian plane
	 * @return resultant value from equation
	 */
	@Override
	public double getbehaviorValueFor(int x) 
	{
		double y = Math.pow(-(1 + 1/controlFactor), x) + 2;	//y = f(x) : f(x) = -(1+1/cF)^x + 2;
		return checkInterval(y);
	}
}
