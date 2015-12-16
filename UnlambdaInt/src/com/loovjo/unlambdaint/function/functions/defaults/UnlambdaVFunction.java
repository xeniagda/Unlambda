package com.loovjo.unlambdaint.function.functions.defaults;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;

/*
 * Simple identity function, which returns its argument.
 */
public class UnlambdaVFunction extends TargetedUnlambdaFunction {

	@Override
	public boolean equals(Object other) {
		return other instanceof UnlambdaVFunction;
	}
	
	@Override
	public String toString() {
		return "UnlambdaVFunction()";
	}

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public String getUnlCode(boolean targets) {
		return "v";
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction... args) {
		return new UnlambdaVFunction();
	}
}
