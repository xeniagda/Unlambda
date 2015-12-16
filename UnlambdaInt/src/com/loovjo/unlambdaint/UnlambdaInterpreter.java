package com.loovjo.unlambdaint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.loovjo.unlambdaint.function.DefaultFunctionScanner;
import com.loovjo.unlambdaint.function.FunctionScanner;
import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaApplyFunction;
import com.loovjo.unlambdaint.gui.UnlambdaInterfaceGui;

/*
 * Base interpreter, targets and runs each cycle, also contains the output of the program.
 */

public class UnlambdaInterpreter {

	public static String output = "";

	public FunctionScanner scanner = new DefaultFunctionScanner();

	public TargetedUnlambdaFunction root;

	public UnlambdaInterpreter(String code) {
		root = (TargetedUnlambdaFunction) scanner.scanSymbolWithLength(code).getFirst().recursiveAbstractionElimination(' ');
		root = root.findAndSetTarget().getFirst();
	}
	
	public boolean cycle() {
		root = root.findAndSetTarget().getFirst();
		if (root instanceof UnlambdaApplyFunction) {

			root = (TargetedUnlambdaFunction) ((UnlambdaApplyFunction) root).applyToTarget();
			root = root.findAndSetTarget().getFirst();
			return true;
		}
		return false;
	}

	public void start() {
		long timer = System.currentTimeMillis();
		long instructions = 0;
		while (cycle()) {
			instructions++;
			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println("State: " + root.getUnlCode(false));
				System.out.println("Output: " + output);
				System.out.println("Instructions ran the last sec: " + instructions);
				instructions = 0;
				timer = System.currentTimeMillis();
			}
		}

		System.out.println("Output: " + output);
	}

	public static void output(String out) {
		output += out;
	}

	public static void main(String[] args) {
		HashMap<String, Object> arguments = ArgParser.readValues(args);

		if (arguments.containsKey("help") || arguments.isEmpty()) {
			if ((boolean) arguments.get("help")) {
				help();
				System.exit(0);
			}
		}

		String in = "";
		if (arguments.containsKey("execute"))
			in = (String) arguments.get("execute");
		if (arguments.containsKey("file")) {
			in = readFile((String) arguments.get("file"));
		}
		in = in.replaceAll("<add>", "`s``s`ksk");
		if (arguments.containsKey("terminal")) {
			new UnlambdaInterfaceTerminal(new UnlambdaInterpreter(in)).start();
		} else if (arguments.containsKey("gui")) {
			new UnlambdaInterfaceGui(new UnlambdaInterpreter(in));
		} else
			new UnlambdaInterpreter(in).start();
	}

	private static String readFile(String path) {
		String code = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			for (String line; (line = br.readLine()) != null; code += line)
				;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return code;
	}
	
	private static void help() {
		System.out.println("Usage:");
		System.out.println("  -execute <code>: Runs that code.");
		System.out.println("  -file <path>: Runs that code.");
		System.out.println("  -terminal <true/false>: Run in terminal.");
	}
}
