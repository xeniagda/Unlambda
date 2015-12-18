package com.loovjo.unlambdaint.function.functions.defaults;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;
import com.loovjo.unlambdaint.utils.Pair;

/*
 * The apply function is a bit different, it isn't a function.
 */
public class UnlambdaApplyFunction extends TargetedUnlambdaFunction {

	public TargetedUnlambdaFunction call, arg;

	/*
	 * @param call - The function that should be applied.
	 * 
	 * @param arg - The function that is the argument to the call.
	 */
	public UnlambdaApplyFunction(TargetedUnlambdaFunction call, TargetedUnlambdaFunction arg) {
		this.call = call;
		this.arg = arg;
	}

	/*
	 * @param call - The function that should be applied.
	 * 
	 * @param arg - The function that is the argument to the call.
	 */
	public UnlambdaApplyFunction(UnlambdaFunction call, UnlambdaFunction arg) {
		this.call = (TargetedUnlambdaFunction) call;
		this.arg = (TargetedUnlambdaFunction) arg;
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof UnlambdaApplyFunction) {
			UnlambdaApplyFunction auf = (UnlambdaApplyFunction) other;
			return auf.arg.equals(this.arg) && auf.call.equals(this.call) && auf.isTargeted == isTargeted;
		}
		return false;
	}

	@Override
	public String toString() {
		return "UnlambdaApplyFunction(" + call + ", " + arg + ")";
	}

	/*
	 * Works by first trying on call and arg, otherwise it sees if the number of
	 * recursive applies on the caller matches the arity on the function.
	 * 
	 * @see com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction#
	 * findAndSetTarget()
	 */

	public Pair<TargetedUnlambdaFunction, Boolean> findAndSetTarget() {

		// Try to target call and arg.
		
		TargetedUnlambdaFunction[] order = new TargetedUnlambdaFunction[] { call, arg };
		
		if (getDeepFunctionApplied() instanceof UnlambdaDFunction && getDeepFunctionDepth() == 1) {
			order = new TargetedUnlambdaFunction[] { arg, call };
		}
		
		for (TargetedUnlambdaFunction func : order) {
			// Recurse
			Pair<TargetedUnlambdaFunction, Boolean> target = func.findAndSetTarget();
			if (target.getSecond())
				return new Pair<TargetedUnlambdaFunction, Boolean>(this, true);
		}

		// See if the number of applies matches the arity of the first
		// function.
		// Ex. ``ki - first function = k, arity = 2, amount of applies = 2.

		int applies = 1;

		// Check the depth.
		TargetedUnlambdaFunction current = call;
		while (current instanceof UnlambdaApplyFunction) {
			applies++;
			current = ((UnlambdaApplyFunction) current).call;
		}
		// Now current is the first function.
		if (current.getArity() == applies) {
			isTargeted = true;
			return new Pair<TargetedUnlambdaFunction, Boolean>(this, true);

		}

		return new Pair<TargetedUnlambdaFunction, Boolean>(this, false);

	}

	@Override
	public int getArity() {
		return UNDEFINED_ARITY;
	}

	@Override
	public String getUnlCode(boolean targets) {
		if (targets && isTargeted) {
			return "§" + "`" + call.getUnlCode(targets) + arg.getUnlCode(targets) + "°";
		}
		return "`" + call.getUnlCode(targets) + arg.getUnlCode(targets);
	}
	
	public TargetedUnlambdaFunction getDeepFunctionApplied() {
		if (call instanceof UnlambdaApplyFunction)
			return ((UnlambdaApplyFunction) call).getDeepFunctionApplied();
		return call;
	}
	
	public int getDeepFunctionDepth() {
		if (call instanceof UnlambdaApplyFunction)
			return ((UnlambdaApplyFunction) call).getDeepFunctionDepth() + 1;
		return 0;
	}
	
	public UnlambdaFunction applyToTarget() {
		if (isTargeted) {
			return apply(arg);
		} else {
			for (UnlambdaFunction func : new UnlambdaFunction[] { call, arg }) {
				if (func instanceof UnlambdaApplyFunction) {
					UnlambdaFunction callApplied = ((UnlambdaApplyFunction) func).applyToTarget();
					if (callApplied != null) {
						if (func == call) {
							call = (TargetedUnlambdaFunction) callApplied;
						}
						if (func == arg) {
							arg = (TargetedUnlambdaFunction) callApplied;
						}
						return this;
					}
				}
			}
		}
		return null;
	}

	@Override
	public TargetedUnlambdaFunction recursiveAbstractionElimination(char variable) {
		if (variable == ' ') {
			return new UnlambdaApplyFunction(call.recursiveAbstractionElimination(variable), arg.recursiveAbstractionElimination(variable));
		}
		return new UnlambdaApplyFunction(
				new UnlambdaApplyFunction(new UnlambdaSFunction(), call.recursiveAbstractionElimination(variable)),
				arg.recursiveAbstractionElimination(variable));
	}

	@Override
	public UnlambdaFunction apply(UnlambdaFunction... args) {
		ArrayList<UnlambdaFunction> list = new ArrayList<UnlambdaFunction>(Arrays.asList(args));
		list.add(0, arg);

		return call.apply((UnlambdaFunction[]) list.toArray(new UnlambdaFunction[list.size()]));
	}
}
