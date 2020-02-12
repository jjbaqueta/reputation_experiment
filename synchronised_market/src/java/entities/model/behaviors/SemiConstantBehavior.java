package entities.model.behaviors;

public class SemiConstantBehavior extends Behavior{

	public SemiConstantBehavior(double controlFactor) 
	{
		super(controlFactor);
	}

	/* 
	 * In this method the return value is partially constant
	 * @return resultant value from equation
	 * 
	 * 0.85
	 */
	@Override
	public double getbehaviorValueFor(int x) 
	{
		double y = -controlFactor/5 + x;	//y = f(x); f(x) = -cF/5 + x;	
		return checkInterval(y); 
	}
}
