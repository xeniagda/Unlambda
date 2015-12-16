package com.loovjo.unlambdaint.function.functions.added;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;

/*
 * The abstraction operator. Used as ^f <code using variable $f>. Supports elimination. Any 
 * character except for space can be used as a variable.
 */
public class UnlambdaFunctionLambda extends TargetedUnlambdaFunction {

	public char variable;

	public TargetedUnlambdaFunction content;

	public UnlambdaFunctionLambda(char var, TargetedUnlambdaFunction content) {
		variable = var;
		this.content = content;
	}

	@Override
	public int getArity() {
		return 1;
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction... args) {
		return null;
	}

	public String toString() {
		return "UnlambdaFunctionLambda('" + variable + "', " + content.toString() + ")";
	}

	@Override
	public String getUnlCode(boolean targets) {
		return "^" + variable + " " + content.getUnlCode(targets);
	}

	@Override
	public TargetedUnlambdaFunction recursiveAbstractionElimination(char var) {
		if (content instanceof UnlambdaFunctionLambda) {
			return new UnlambdaFunctionLambda(variable,
					content.recursiveAbstractionElimination(((UnlambdaFunctionLambda) content).variable))
							.recursiveAbstractionElimination(variable);
		}
		return content.recursiveAbstractionElimination(variable);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UnlambdaFunctionLambda) {
			UnlambdaFunctionLambda ufl = (UnlambdaFunctionLambda) other;
			return ufl.variable == variable && ufl.content.equals(content);
		}
		return false;
	}

}
