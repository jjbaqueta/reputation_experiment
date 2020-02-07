package entities.model.behaviors;

public class ConstantBehavior extends Behavior{

	public ConstantBehavior(double controlFactor) 
	{
		super(controlFactor);
	}
	
	/* In this method the return value is constant 
	 * @return 0 all the time
	 */
	@Override
	public double getbehaviorValueFor(int x) 
	{
		return 0.0;		//y = f(x) : f(x) = 0;
	}
}
