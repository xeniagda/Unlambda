package com.loovjo.unlambdaint.function.functions.added;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaIFunction;

/*
 * The variables used in abstraction. Used like $f. Supports elimination.
 */
public class UnlambdaFunctionVariable extends TargetedUnlambdaFunction {
	
	public char var;
	
	public UnlambdaFunctionVariable(char var) {
		this.var = var;
	}
	
	public String toString() {
		return "UnlambdaFunctionVariable('" + var + "')";
	}
	
	@Override
	public int getArity() {
		return 1;
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction... args) {
		return null;
	}
	
	public String getUnlCode(boolean targets) {
		return "$" + var;
	}
	
	@Override
	public TargetedUnlambdaFunction recursiveAbstractionElimination(char var) {
		if (this.var == var)
			return new UnlambdaIFunction();
		return super.recursiveAbstractionElimination(var);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof UnlambdaFunctionVariable) {
			UnlambdaFunctionVariable ufv = (UnlambdaFunctionVariable) other;
			return ufv.var == var;
		}
		return false;
	}

}
