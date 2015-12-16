package com.loovjo.unlambdaint.function.functions;

/*
 * The basic function interface, has an arity function and an apply function.
 */
public interface UnlambdaFunction {
	
	public static final int UNDEFINED_ARITY = -1;
	
	public int getArity();
	
	public UnlambdaFunction apply(UnlambdaFunction... args);
}
