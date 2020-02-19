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
//		System.out.println("...................f(" + x +") = " + (1 + (Math.log(x)/2)/controlFactor));
		double y = (Math.log(x)/2)/controlFactor;
		
		return checkInterval(y); 
	}
}
