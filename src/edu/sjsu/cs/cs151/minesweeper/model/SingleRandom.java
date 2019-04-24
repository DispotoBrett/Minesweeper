package edu.sjsu.cs.cs151.minesweeper.model;

import java.util.Random;

/**
 * Provides a single random number generator. SingleRandom is a singleton class.
 * The context of the singleton pattern is that all clients need to access a single shared instance of a class,
 * and that it must be ensured that no additional instances can be created accidentally.
 * Sourced from "Object-Oriented Design and Patterns, 3rd Edition".
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */

public class SingleRandom
{
	/**
	 * Sets the seed of the single random number generator.
	 *
	 * @param seed The seed to used to seed the random number generator.
	 */
	public void setSeed(int seed)
	{
		generator.setSeed(seed);
	}

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
		//"Supply a static method that returns a reference to the single instance."
		return instance;
	}

	/**
	 * Constructs the Random object associated with the SingleRandom class.
	 */
	private SingleRandom()
	{
		//"Define a class with a private constructor."
		generator = new Random();
	}

	private Random generator;
	//"The class constructs a single instance of itself", "global variable"
	private static SingleRandom instance = new SingleRandom();
}