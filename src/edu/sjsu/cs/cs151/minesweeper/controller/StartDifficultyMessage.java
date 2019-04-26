package edu.sjsu.cs.cs151.minesweeper.controller;

public class StartDifficultyMessage extends Message
{
	public StartDifficultyMessage(int difficulty)
	{
		this.difficulty = difficulty;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
	
	private int difficulty;
}
