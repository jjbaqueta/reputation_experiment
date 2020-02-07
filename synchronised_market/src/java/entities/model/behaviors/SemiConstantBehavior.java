package entities.model.behaviors;

public class SemiConstantBehavior extends Behavior{

	public SemiConstantBehavior(double controlFactor) 
	{
		super(controlFactor);
	}

	/* 
	 * In this method the return value is partially constant
	 * @return resultant value from equation
	 */
	@Override
	public double getbehaviorValueFor(int x) 
	{
		double y = -controlFactor/3 + x;	//y = f(x); f(x) = -cF/3 + x;	
		return checkInterval(y); 
	}
}
