package com.loovjo.unlambdaint.test;

import com.loovjo.unlambdaint.function.DefaultFunctionScanner;
import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;

/*
 * A class I used for testing varius small stuff.
 */
public class RandomTesting {

	public static void main(String[] args) {
		System.out.println(scan("^f ^x `$f`$f$x")
				.recursiveAbstractionElimination(' ').getUnlCode(false));
	}

	private static TargetedUnlambdaFunction scan(String code) {
		return new DefaultFunctionScanner().scanSymbolWithLength(code).getFirst();
	}
}
