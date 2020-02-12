package entities.model.behaviors;

public class LinearIncreasingBehavior extends Behavior
{

	public LinearIncreasingBehavior(double controlFactor) 
	{
		super(controlFactor);
	}

	/* 
	 * In this method the return value increases according to x value increases  
	 * The greater x the greater y
	 * @param x An integer value that represents a variation on x-axis of the cartesian plane
	 * @return resultant value from equation
	 */
	@Override
	public double getbehaviorValueFor(int x) 
	{
		double y = x;			//y = f(x); f(x) = x;
		return checkInterval(y); 
	}

}
