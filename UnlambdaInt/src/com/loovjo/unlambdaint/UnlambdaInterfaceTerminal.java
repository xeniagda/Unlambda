package com.loovjo.unlambdaint;

import java.io.IOException;
import java.util.Scanner;
/*
 * Simple terminal interface. Note: VERY slow (terminals fault).
 */
public class UnlambdaInterfaceTerminal {

	public UnlambdaInterpreter interpreter;

	public int split;

	public UnlambdaInterfaceTerminal(UnlambdaInterpreter interpreter) {
		this.interpreter = interpreter;
	}
	
	public void start() {
		while (true) {
			interpreter.cycle();
			update();
		}
	}
	
	public void update() {
		clearScreen();
		updateSplitAndDrawLine();
		updateOutputSection();
		updateCodeSection();
	}
	// Displays the output, line by line.
	private void updateOutputSection() {
		String out = UnlambdaInterpreter.output;
		int x = 0;
		int y = 0;

		int lastX = x;
		int width = getWidth();

		String buffer = "";
		int bufferX = 0;
		boolean bufferEvaluating = false;

		for (int i = 0; i < out.length(); i++) {
			char chr = out.charAt(i);
			x = i % width;
			y = i / width;
			boolean evaluating = i >= interpreter.root.getTargetStartChar() && i <= interpreter.root.getTargetEnd();
			if (x == lastX + 1 && evaluating == bufferEvaluating) {
				buffer += chr;
			}
			else {
				// Reset
				System.out.println("\033[0m");
				// Move cursor
				System.out.println("\033[" + (y + 1) + ";" + bufferX + "H");
				// Set color
				if (bufferEvaluating) {
					System.out.println("\033[48;5;52m");
				}
				System.out.println(buffer);
				
				buffer = "";
				bufferX = x;
				bufferEvaluating = evaluating;
				
			}
		}

	}
	
	// Draws the code section.
	private void updateCodeSection() {
		String code = interpreter.root.getUnlCode(false);

		for (int i = 0; i < code.length(); i++) {
			int x = i % split;
			int y = i / split;
			char chr = code.charAt(i);
			boolean targeted = i >= interpreter.root.getTargetStartChar() && i < interpreter.root.getTargetEnd();

			// Clear styles
			System.out.print("\033[0m");
			// Set color
			if (targeted)
				System.out.println("\033[48;5;52m");
			// Move cursor
			System.out.print("\033[" + (y + 1) + ";" + x + "H");

			System.out.print(chr);

			// Reset
			System.out.print("\033[0m");
		}
	}
	// The split is at 2/3s of the width.
	private void updateSplitAndDrawLine() {
		// Get width
		int width = getWidth();
		split = 2 * width / 3;

		for (int y = 0; y < 500; y++) { // Assume max 500 lines in terminal, I'm
										// to lazy to find the exact.
			System.out.print("\033[" + y + ";" + split + "H\033[38;5;20m|");
		}
	}
	
	// Gets the width by running tput cols 2> /dev/tty and reading.
	private int getWidth() {
		try {
			Process p = Runtime.getRuntime().exec(new String[] { "bash", "-c", "tput cols 2> /dev/tty" });
			Scanner scanner = new Scanner(p.getInputStream());
			int width = scanner.nextInt();
			scanner.close();
			return width;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 80;
	}
	// Clear the screen by printing an escape sequence.
	private void clearScreen() {
		System.out.println("\033[2J");
	}

}
