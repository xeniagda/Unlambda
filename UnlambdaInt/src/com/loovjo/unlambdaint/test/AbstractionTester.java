package com.loovjo.unlambdaint.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.loovjo.unlambdaint.function.DefaultFunctionScanner;
import com.loovjo.unlambdaint.function.functions.UnlambdaFunction;
import com.loovjo.unlambdaint.function.functions.added.UnlambdaFunctionLambda;
import com.loovjo.unlambdaint.function.functions.added.UnlambdaFunctionVariable;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaApplyFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaIFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaKFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaPrintFunction;
import com.loovjo.unlambdaint.function.functions.defaults.UnlambdaSFunction;
import com.loovjo.unlambdaint.utils.Pair;

/*
 * Tests the abstraction function
 */
public class AbstractionTester {
	
	@Test
	public void testScanLambda() {

		assertEquals(
				new UnlambdaFunctionLambda('f', 
						new UnlambdaIFunction()
						),
				new DefaultFunctionScanner().scanSymbolWithLength("^fi").getFirst());
		
		assertEquals(
				new UnlambdaFunctionLambda('f',
						new UnlambdaFunctionVariable('f')
						),
				new DefaultFunctionScanner().scanSymbolWithLength("^f$f").getFirst());
		
		assertEquals(
				new UnlambdaFunctionLambda('f',
						new UnlambdaApplyFunction(
								new UnlambdaFunctionVariable('f'),
								new UnlambdaIFunction()
							)
						),
				new DefaultFunctionScanner().scanSymbolWithLength("^f`$fi").getFirst());
		
		assertEquals(
				new UnlambdaFunctionLambda('f',
						new UnlambdaFunctionLambda('g',
								new UnlambdaApplyFunction(
										new UnlambdaFunctionVariable('f'),
										new UnlambdaFunctionVariable('g')
										)
								)
					),
				new DefaultFunctionScanner().scanSymbolWithLength("^f^g`$f$g").getFirst());
		assertEquals("^f ^g `$f$g",
				new DefaultFunctionScanner().scanSymbolWithLength("^f^g`$f$g").getFirst().getUnlCode(false));
		
	}
	
	@Test
	public void testScanOps() {
		long start = System.currentTimeMillis();
		assertEquals("``ki.a", new DefaultFunctionScanner().scanOps("``ki.ai"));
		assertEquals("s", new DefaultFunctionScanner().scanOps("s``s`ks"));
		assertEquals("``s`kk`ks", new DefaultFunctionScanner().scanOps("``s`kk`ks``s``s`ks``s`kk`kk`kii"));
		assertEquals("`````````````.H.e.l.l.o.,. .w.o.r.l.d.!i", 
				new DefaultFunctionScanner().scanOps("`````````````.H.e.l.l.o.,. .w.o.r.l.d.!i"));

		long delta = System.currentTimeMillis() - start;
		System.out.println("Took " + delta + "ms.");
	}

	@Test
	public void testScanSymbols() {
		assertEquals(new Pair<UnlambdaFunction, Integer>(new UnlambdaPrintFunction('A'), 2),
				new DefaultFunctionScanner().scanSymbolWithLength(".A"));

		assertEquals(new Pair<UnlambdaFunction, Integer>(new UnlambdaPrintFunction('q'), 2),
				new DefaultFunctionScanner().scanSymbolWithLength(".q`s"));

		assertEquals(new Pair<UnlambdaFunction, Integer>(new UnlambdaSFunction(), 1),
				new DefaultFunctionScanner().scanSymbolWithLength("s"));
		assertEquals(new Pair<UnlambdaFunction, Integer>(new UnlambdaSFunction(), 1),
				new DefaultFunctionScanner().scanSymbolWithLength("s`ks"));

		assertEquals(new Pair<UnlambdaFunction, Integer>(new UnlambdaKFunction(), 1),
				new DefaultFunctionScanner().scanSymbolWithLength("k"));
		assertEquals(new Pair<UnlambdaFunction, Integer>(new UnlambdaKFunction(), 1),
				new DefaultFunctionScanner().scanSymbolWithLength("k`ks"));

		// Applying functions.
		// To retain the tree structure:
		// @formatter:off
		
		assertEquals(
				new UnlambdaApplyFunction(
						new UnlambdaKFunction(), 
						new UnlambdaSFunction()),
				
				new DefaultFunctionScanner().scanSymbolWithLength("`ks").getFirst());

		assertEquals(
				new UnlambdaApplyFunction(
						new UnlambdaApplyFunction(
								new UnlambdaSFunction(), 
								new UnlambdaIFunction()),
						new UnlambdaIFunction()),
				
				new DefaultFunctionScanner().scanSymbolWithLength("``sii").getFirst());
		assertEquals(
				new UnlambdaApplyFunction(
						new UnlambdaApplyFunction(
								new UnlambdaSFunction(), 
								new UnlambdaIFunction()),
						new UnlambdaApplyFunction(
								new UnlambdaKFunction(), 
								new UnlambdaIFunction())
						),
				
				new DefaultFunctionScanner().scanSymbolWithLength("``si`ki").getFirst());
		
		new DefaultFunctionScanner().scanSymbolWithLength("``.H.ei");
	}

}
