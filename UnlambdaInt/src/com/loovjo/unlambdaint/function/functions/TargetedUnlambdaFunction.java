package com.loovjo.unlambdaint.function.functions;

import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaApplyFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaKFunction;
import com.loovjo.unlambdaint.utils.Pair;

/*
 * The basic unlambda function, which can be targeted, ei. be marked to be evaluated.
 */
public abstract class TargetedUnlambdaFunction implements UnlambdaFunction {
	
	public boolean isTargeted = false;
	
	/*
	 * Sets the target and returns this.
	 */
	public TargetedUnlambdaFunction setTarget(boolean target) {
		isTargeted = target;
		return this;
	}
	
	public String getUnlCode(boolean targets) {
		return "NonImplemented.";
	}
	
	/*
	 * Returns a pair, containing itself and a boolean, indicating if the targeting was successful.
	 * Will be non-successful if it isn't called on an ApplyFunction
	 */
	public Pair<TargetedUnlambdaFunction, Boolean> findAndSetTarget() {
		return new Pair<TargetedUnlambdaFunction, Boolean>(this, false);
	}
	
	public TargetedUnlambdaFunction recursiveAbstractionElimination(char variable) {
		if (variable == ' ')
			return this;
		return new UnlambdaApplyFunction(
				new UnlambdaKFunction(), 
				this);
	}
	
	class IllegalFunctionException extends RuntimeException {
		private static final long serialVersionUID = -766053004750649511L;
		
		public IllegalFunctionException(String message) {
			super(message);
		}
	}


	public int getTargetStartChar() {
		return getUnlCode(true).indexOf('§');
	}
	
	public int getTargetEnd() {
		return getUnlCode(true).indexOf('°') - 1;
	}

}
