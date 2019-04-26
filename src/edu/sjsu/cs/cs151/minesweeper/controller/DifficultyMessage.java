package edu.sjsu.cs.cs151.minesweeper.controller;

public class DifficultyMessage extends Message
{
	public DifficultyMessage(int difficulty)
	{
		this.difficulty = difficulty;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
	
	private int difficulty;
}
