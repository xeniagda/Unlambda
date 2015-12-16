package com.loovjo.unlambdaint.function.functions.defaults;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;

/*
 * Constant-making funcition, when applied to two arguments, it returns the first.
 */
public class UnlambdaKFunction extends TargetedUnlambdaFunction {

	@Override
	public boolean equals(Object other) {
		return other instanceof UnlambdaKFunction;
	}
	
	@Override
	public String toString() {
		return "UnlambdaKFunction()";
	}

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public String getUnlCode(boolean targets) {
		return "k";
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction... args) {
		return args[0];
	}
}
