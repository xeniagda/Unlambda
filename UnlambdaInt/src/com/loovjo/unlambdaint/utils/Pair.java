package com.loovjo.unlambdaint.utils;

/*
 * Just contains two values.
 */
public class Pair<T1, T2> {

	private T1 first;
	private T2 second;

	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}

	public void setFirst(T1 first) {
		this.first = first;
	}

	public void setSecond(T2 second) {
		this.second = second;
	}
	/*
	 * Check if both the first and the second are the same.  
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Pair) {
			Pair p = (Pair) other;
			return p.first.equals(first) && p.second.equals(second);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Pair(" + first + ", " + second + ")";
	}
}
