package com.loovjo.unlambdaint.function.functions.defaults;

import com.loovjo.unlambdaint.UnlambdaInterpreter;
import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;

/*
 * Works like the identity function, with the side effect of printing the character.
 */
public class UnlambdaPrintFunction extends TargetedUnlambdaFunction {

	public char printChar;

	public UnlambdaPrintFunction(char chr) {
		printChar = chr;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UnlambdaPrintFunction) {
			UnlambdaPrintFunction upf = (UnlambdaPrintFunction) other;
			return upf.printChar == printChar;
		}
		return false;
	}

	@Override
	public String toString() {
		return "UnlambdaPrintFunction('" + printChar + "')";
	}

	@Override
	public int getArity() {
		return 1;
	}

	@Override
	public String getUnlCode(boolean targets) {
		return printChar == '\n' ? "r" : "." + printChar;
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction... args) {
		UnlambdaInterpreter.output(printChar + "");
		return args[0];
	}
}
