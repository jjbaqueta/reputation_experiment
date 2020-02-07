package entities.model.behaviors;

public class ParableDecIncBehavior extends Behavior 
{

	public ParableDecIncBehavior(double controlFactor) 
	{
		super(controlFactor);
	}

	/* In this method the return value decreases up to 0 and after that increases
	 * @param x An integer value that represents a variation on x-axis of the cartesian plane
	 * @return resultant value from equation
	 */
	@Override
	public double getbehaviorValueFor(int x) 
	{
		double y = 1 / controlFactor * Math.pow((x - controlFactor / 2), 2);	//y = f(x) : f(x) = (1/cF) * (x-cF/2) ^2
		return checkInterval(y);
	}
}
