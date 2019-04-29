package edu.sjsu.cs.cs151.minesweeper.controller;

public class DifficultyMessage extends Message
{
	public DifficultyMessage(int difficulty, boolean gameAlreadyRunning)
	{
		this.difficulty = difficulty;
		alreadyRunning = gameAlreadyRunning;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
	
	public boolean isGameAlreadyRunning()
	{
		return alreadyRunning;
	}
	
	private int difficulty;
	private boolean alreadyRunning;
}
