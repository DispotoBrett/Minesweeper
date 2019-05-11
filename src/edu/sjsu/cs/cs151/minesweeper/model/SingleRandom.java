package edu.sjsu.cs.cs151.minesweeper.model;

import java.util.Random;

/**
 * Provides a single random number generator. SingleRandom is a singleton class.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */

public class SingleRandom extends Random
{
	/**
	 * Gets the next (pseudo)random int from the single random number generator.
	 *
	 * @return The next (pseudo)random int from the single random number generator.
	 */
	public int nextInt()
	{
		return generator.nextInt();
	}

	/**
	 * Gets an instance of the single random number generator.
	 *
	 * @return An instance of the single random number generator.
	 */
	public static SingleRandom getInstance()
	{
		return instance;
	}

	/**
	 * Constructs the Random object associated with the SingleRandom class.
	 */
	private SingleRandom()
	{
		generator = new Random();
	}

	private Random generator;
	private static SingleRandom instance = new SingleRandom();
}