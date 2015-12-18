package com.loovjo.unlambdaint.function;

import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.added.UnlambdaFunctionLambda;
import com.loovjo.unlambdaint.function.functions.added.UnlambdaFunctionVariable;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaApplyFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaDFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaIFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaKFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaPrintFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaSFunction;
import com.loovjo.unlambdaint.utils.Pair;


/*
 * The basic function scanner, scans char by char. 
 */
public class DefaultFunctionScanner implements FunctionScanner {

	public Pair<TargetedUnlambdaFunction, Integer> scanSymbolWithLength(String code) {
		return scanSymbolWithLength(code, -1, true);
	}

	public static void main(String[] args) {
		System.out.println(new DefaultFunctionScanner().scanSymbolWithLength("`````````````.H.e.l.l.o.,. .w.o.r.l.d.!i"));
	}

	private Pair<TargetedUnlambdaFunction, Integer> scanSymbolWithLength(String code, int indent, boolean giveArgsToApply) {
		code = code.replaceAll("([^.])\\s(.)|^\\s", "$1$2");
		if (indent < 0) {
			indent--;
		}
		String in = new String(new char[Math.abs(indent)]).replace("\0", " ");

		if (indent > 0)
			System.out.println(in + code);

		char firstChar = code.charAt(0);
		switch (firstChar) {
		case '.':
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaPrintFunction(code.charAt(1)), 2);
		case 'r':
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaPrintFunction('\n'), 1);
		case 's':
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaSFunction(), 1);
		case 'k':
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaKFunction(), 1);
		case 'i':
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaIFunction(), 1);
		case 'd':
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaDFunction(), 1);
		case '$':
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaFunctionVariable(code.charAt(1)), 2);
		case '`':
			if (!giveArgsToApply)
				return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaApplyFunction(null, null), 1);
			if (indent > 0)
				System.out.println(in + "Calling first:");
			Pair<TargetedUnlambdaFunction, Integer> first = scanSymbolWithLength(scanOps(code.substring(1)), indent + 1,
					giveArgsToApply);
			if (indent > 0)
				System.out.println("Result: " + first.getFirst());
			if (indent > 0)
				System.out.println(in + "Calling second:");
			Pair<TargetedUnlambdaFunction, Integer> second = scanSymbolWithLength(
					scanOps(code.substring(1 + first.getSecond())), indent + 1, giveArgsToApply);
			if (indent > 0)
				System.out.println("Result: " + second.getFirst());
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaApplyFunction(first.getFirst(), second.getFirst()),
					first.getSecond() + second.getSecond() + 1);
		case '^':
			char var = code.charAt(1);
			TargetedUnlambdaFunction nextPart = scanSymbolWithLength(scanOps(code.substring(2))).getFirst();
			return new Pair<TargetedUnlambdaFunction, Integer>(new UnlambdaFunctionLambda(var, nextPart), scanOps(code.substring(2)).length() + 2);
		}

		return null;
	}

	public String scanOps(String code) {
		int i = 1;

		String result = "";

		while (i > 0) {
			Pair<TargetedUnlambdaFunction, Integer> symbol = scanSymbolWithLength(code, -1, false);
			int length = symbol.getSecond();
			UnlambdaFunction func = symbol.getFirst();
			String symbolString = code.substring(0, length);
			result += symbolString;
			code = code.substring(length);

			if (func instanceof UnlambdaApplyFunction) {
				i++;
			} else {
				i--;
			}
			if (code.length() == 0 && i > 0) {
				return null;
			}
		}
		return result;
	}

}
