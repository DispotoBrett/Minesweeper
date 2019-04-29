package edu.sjsu.cs.cs151.minesweeper.controller;

public interface Valve
{
	enum ValveResponse { MISS, EXECUTED, FINISH };
	
	/**
	 * Acts on the Model/View based on the message
	 * @param message the message that it will act on
	 * @return MISS if the Valve cannot process the message, EXECUTED if it can, and FINISH if the game is over
	 */
	public ValveResponse execute(Message message);
}
