package com.loovjo.unlambdaint.function.functions.defaults;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;

/*
 * Simple identity function, which returns its argument.
 */
public class UnlambdaIFunction extends TargetedUnlambdaFunction {

	@Override
	public boolean equals(Object other) {
		return other instanceof UnlambdaIFunction;
	}
	
	@Override
	public String toString() {
		return "UnlambdaIFunction()";
	}

	@Override
	public int getArity() {
		return 1;
	}

	@Override
	public String getUnlCode(boolean targets) {
		return "i";
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction... args) {
		return args[0];
	}
}
