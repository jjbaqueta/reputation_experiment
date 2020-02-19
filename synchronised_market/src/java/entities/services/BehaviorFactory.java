package entities.services;

import entities.model.behaviors.Behavior;
import entities.model.behaviors.ConstantBehavior;
import entities.model.behaviors.ExponantialDecreaseBehavior;
import entities.model.behaviors.ExponentialIncreaseBehavior;
import entities.model.behaviors.LinearDecreaseBehavior;
import entities.model.behaviors.LinearIncreasingBehavior;
import entities.model.behaviors.SemiConstantBehavior;
import enums.BehaviorPattern;

public class BehaviorFactory {

	public static Behavior getBehavior(BehaviorPattern pattern, int controlFactor) throws Exception
	{
		switch (pattern)
		{
			case CONSTANT:
				return new ConstantBehavior(controlFactor);
			
			case SEMICONSTANT:
				return new SemiConstantBehavior(controlFactor);
			
			case LINEAR_INCREASING:
				return new LinearIncreasingBehavior(controlFactor);
			
			case LINEAR_DECREASING:
				return new LinearDecreaseBehavior(controlFactor);
			
			case EXPONENTIAL_INCREASING:
				return new ExponentialIncreaseBehavior(controlFactor);
			
			case EXPONENTIAL_DECREASING:
				return new ExponantialDecreaseBehavior(controlFactor);
			
			default:
				throw new Exception("Pattern is not valid!");
		}
	}
}
