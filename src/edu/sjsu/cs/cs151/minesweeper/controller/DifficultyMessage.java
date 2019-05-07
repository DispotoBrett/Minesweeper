package edu.sjsu.cs.cs151.minesweeper.controller;

import edu.sjsu.cs.cs151.minesweeper.model.Model;
import edu.sjsu.cs.cs151.minesweeper.view.View;


public class DifficultyMessage implements Message
{
	public DifficultyMessage(View.Difficulty difficulty, boolean changeNow)
	{
		//Translate View.Difficulty -> Model.Difficulty
		switch (difficulty)
		{
		case EASY:
			this.difficulty = Model.Difficulty.EASY;
			break;

		case MEDIUM:
			this.difficulty = Model.Difficulty.MEDIUM;
			break;

		case HARD:
			this.difficulty = Model.Difficulty.HARD;
			break;

		default:
		}
		this.changeNow = changeNow;
	}

	public Model.Difficulty getDifficulty()
	{
		return difficulty;
	}

	public boolean shouldBeChangedNow()
	{
		return changeNow;
	}

	private Model.Difficulty difficulty;
	private boolean changeNow;
}
