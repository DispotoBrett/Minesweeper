package edu.sjsu.cs.cs151.minesweeper.controller;

import edu.sjsu.cs.cs151.minesweeper.model.Model.Difficulty;

public class DifficultyMessage extends Message
{
	public DifficultyMessage(Difficulty difficulty, boolean changeNow)
	{
		this.difficulty = difficulty;
		this.changeNow = changeNow;
	}

	public Difficulty getDifficulty()
	{
		return difficulty;
	}

	public boolean shouldBeChangedNow()
	{
		return changeNow;
	}

	private Difficulty difficulty;
	private boolean changeNow;
}
