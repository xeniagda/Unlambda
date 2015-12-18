package com.loovjo.unlambdaint.test;

import com.loovjo.unlambdaint.UnlambdaInterpreter;
import com.loovjo.unlambdaint.function.DefaultFunctionScanner;
import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;

/*
 * A class I used for testing varius small stuff.
 */
public class RandomTesting {

	public static void main(String[] args) {
		UnlambdaInterpreter i = new UnlambdaInterpreter("");
		while (i.cycle());
		System.out.println("Code: " + i.root.getUnlCode(false));
		System.out.println("Out: " + i.output);
	}

	private static TargetedUnlambdaFunction scan(String code) {
		return new DefaultFunctionScanner().scanSymbolWithLength(code).getFirst();
	}
}
