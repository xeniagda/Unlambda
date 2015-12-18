package com.loovjo.unlambdaint.function.functions.defaults;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;

public class UnlambdaDFunction extends TargetedUnlambdaFunction {

	@Override
	public boolean equals(Object other) {
		return other instanceof UnlambdaDFunction;
	}
	
	@Override
	public String toString() {
		return "UnlambdaDFunction()";
	}

	@Override
	public int getArity() {
		return 2;
	}
	
	@Override
	public boolean isD() {
		return true;
	}
	
	@Override
	public String getUnlCode(boolean targets) {
		return "d";
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction... args) {
		return args[0].apply(args[1]);
	}

}
