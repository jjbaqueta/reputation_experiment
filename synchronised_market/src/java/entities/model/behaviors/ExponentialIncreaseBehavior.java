package entities.model.behaviors;

public class ExponentialIncreaseBehavior extends Behavior 
{

	public ExponentialIncreaseBehavior(double controlFactor)
	{
		super(controlFactor);
	}

	/* 
	 * In this method the return value increases according to x value increases (exponentially)
	 * The greater x the greater y
	 * @param x An integer value that represents a variation on x-axis of the cartesian plane
	 * @return resultant value from equation
	 */
	@Override
	public double getbehaviorValueFor(int x) 
	{
		double y = 0.25 - Math.log(x)/controlFactor;
		return checkInterval(y); 
	}
}
