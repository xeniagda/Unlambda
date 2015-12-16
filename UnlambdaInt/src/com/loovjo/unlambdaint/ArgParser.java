package com.loovjo.unlambdaint;

import java.awt.Color;
import java.util.HashMap;
/*
 * Parses command line arguments.
 */
public class ArgParser {

	public static HashMap<String, Object> readValues(String[] args) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String valueReading = null;

		for (String arg : args) {
			if (arg.startsWith("-")) {
				if (valueReading != null) {
					map.put(valueReading, true);
				}
				valueReading = arg.substring(1).toLowerCase();
			} else if (valueReading != null) {
				map.put(valueReading, parseToObject(arg));
				valueReading = null;
			}
		}
		if (valueReading != null)
			map.put(valueReading, true);

		return map;
	}

	private static Object parseToObject(String val) {
		if (val.matches("^\".*\"$")) { // Double-quoted string
			return val;
		}
		if (val.matches("^-?\\d+$")) { // Integer
			return Integer.parseInt(val);
		}
		if (val.matches("^-?\\d+\\.\\d+$")) { // Float
			return Float.parseFloat(val);
		}
		if (val.matches("[Tt]rue|[Ff]alse")) { // Boolean
			return val.equalsIgnoreCase("true");
		}
		if (val.matches("^'.'$")) { // Char
			return val.charAt(1);
		}
		if (val.matches("^r=\\d+g=\\d+b=\\d+$")) {
			return new Color(Integer.parseInt(val.split(".=")[1]), Integer.parseInt(val.split(".=")[2]),
					Integer.parseInt(val.split(".=")[3]));
		}
		// If nothing matches, return the value as a string.
		return val;
	}

}
