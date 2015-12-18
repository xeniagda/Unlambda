package com.loovjo.unlambdaint.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.loovjo.unlambdaint.function.DefaultFunctionScanner;
import com.loovjo.unlambdaint.function.functions.TargetedUnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaApplyFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaIFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaKFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaSFunction;
import com.loovjo.unlambdaint.utils.Pair;


public class UnlambdaFunctionTester {

	@Test
	public void testFunctionApplying() {
		assertEquals(".a", ((TargetedUnlambdaFunction)scan("`i.a").apply()).getUnlCode(false));
		assertEquals(".a", ((TargetedUnlambdaFunction)scan("``k.a.b").apply()).getUnlCode(false));
		assertEquals(".a", ((TargetedUnlambdaFunction)((UnlambdaApplyFunction)scan("`i.a").findAndSetTarget().getFirst()).applyToTarget()).getUnlCode(false));
		assertEquals("i", ((TargetedUnlambdaFunction)((UnlambdaApplyFunction)scan("`.ai").findAndSetTarget().getFirst()).applyToTarget()).getUnlCode(false));
		assertEquals(".a", ((TargetedUnlambdaFunction)((UnlambdaApplyFunction)scan("``k.a.b").findAndSetTarget().getFirst()).applyToTarget()).getUnlCode(false));
		assertEquals("``.a.c`.b.c", ((TargetedUnlambdaFunction)((UnlambdaApplyFunction) scan("```s.a.b.c").findAndSetTarget().getFirst()).applyToTarget()).getUnlCode(false));
		assertEquals("`.c`.b.c", ((TargetedUnlambdaFunction)((UnlambdaApplyFunction) scan("``.a.c`.b.c").findAndSetTarget().getFirst()).applyToTarget()).getUnlCode(false));
		
	}
	
	@Test
	public void testIO() {
		
	}
	
	private TargetedUnlambdaFunction scan(String code) {
		return new DefaultFunctionScanner().scanSymbolWithLength(code).getFirst();
	}

	@Test
	public void testTargetingAndEvaluating() {
		// Using findAndSetTarget
		//@formatter:off
		Pair<TargetedUnlambdaFunction, Boolean> target = 
				new UnlambdaApplyFunction(
					new UnlambdaApplyFunction(
						new UnlambdaKFunction(),
						new UnlambdaIFunction()),
					new UnlambdaIFunction()
					)
				.findAndSetTarget();
		
		assertEquals(true, 
				target.getSecond()
				);
		assertEquals("§``kii°", 
				target.getFirst().getUnlCode(true)
				);
		
		target =
				new UnlambdaApplyFunction(
						new UnlambdaIFunction(),
						new UnlambdaApplyFunction(
								new UnlambdaApplyFunction(
										new UnlambdaKFunction(),
										new UnlambdaIFunction()),
								new UnlambdaIFunction()
								)
						).findAndSetTarget();
		assertEquals(true, target.getSecond());
		assertEquals("`i§``kii°", target.getFirst().getUnlCode(true));
		
		//@formatter:on
		// Now some tests using the DefaultFunctionScanner

		assertEquals("``sii", scan("``sii").findAndSetTarget().getFirst().getUnlCode(true));
		assertEquals("§```siii°", scan("```siii").findAndSetTarget().getFirst().getUnlCode(true));
		assertEquals("§```sii``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k.nii°",
				scan("```sii``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k.nii").findAndSetTarget().getFirst()
						.getUnlCode(true));
		assertEquals(
				"`§`i``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii°`i``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii",
				scan("``i``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii`i``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii")
						.findAndSetTarget().getFirst().getUnlCode(true));
		// A big one:
		assertEquals(
				"```§```s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii°``k. ``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii`i``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii`i``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii",
				scan("``````s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii``k. ``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii`i``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii`i``s``s``s``s``s``s``s``s`k.H`k.e`k.l`k.l`k.o`k.!`k. ii")
						.findAndSetTarget().getFirst().getUnlCode(true));
	}

	@Test
	public void testGetUnlCode() {

		assertEquals("k", new UnlambdaKFunction().getUnlCode(false));
		assertEquals("s", new UnlambdaSFunction().getUnlCode(false));
		assertEquals("i", new UnlambdaIFunction().getUnlCode(false));
		//@formatter:off
		assertEquals("``sii",
				new UnlambdaApplyFunction(
						new UnlambdaApplyFunction(
								new UnlambdaSFunction(), 
								new UnlambdaIFunction()),
						new UnlambdaIFunction())
				.getUnlCode(false));
		//@formatter:on

		assertEquals("``skk", new DefaultFunctionScanner().scanSymbolWithLength("``skk").getFirst().getUnlCode(false));
	}

}
