package com.loovjo.unlambdaint.function;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.utils.Pair;

/*
 * A scanner that can parse unlambda syntax.
 */
public interface FunctionScanner {
	/*
	 * Gives back a pair contaning the function and the length of the function.
	 */
	public Pair<TargetedUnlambdaFunction, Integer> scanSymbolWithLength(String code);
	/*
	 * Gives back the valid unlambda part of the argument.
	 */
	public String scanOps(String code);
}
