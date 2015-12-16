package com.loovjo.unlambdaint.function.functions.defaults;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;

public class UnlambdaSFunction extends TargetedUnlambdaFunction {

	@Override
	public boolean equals(Object other) {
		return other instanceof UnlambdaSFunction;
	}

	@Override
	public String toString() {
		return "UnlambdaSFunction()";
	}

	@Override
	public int getArity() {
		return 3;
	}

	@Override
	public String getUnlCode(boolean targets) {
		return "s";
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction[] args) {
		return new UnlambdaApplyFunction(
					new UnlambdaApplyFunction(
							args[0],
							args[2]
					),
					new UnlambdaApplyFunction(
							args[1],
							args[2]
							)
			    );
	}
}
