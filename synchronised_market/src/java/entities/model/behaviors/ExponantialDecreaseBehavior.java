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
		double y = Math.log(x)/controlFactor;
		return checkInterval(y);
	}
}
