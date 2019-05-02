package edu.sjsu.cs.cs151.minesweeper.tests;

import edu.sjsu.cs.cs151.minesweeper.model.SingleRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SingleRandomTests
{
	@Test
	public void ensureOneSingleRandomInstanceTest()
	{
		SingleRandom aRandomNumberGenerator = SingleRandom.getInstance();
		SingleRandom anotherRandomNumberGenerator = SingleRandom.getInstance();

		assertEquals(aRandomNumberGenerator, anotherRandomNumberGenerator);
	}
}
