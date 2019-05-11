package edu.sjsu.cs.cs151.minesweeper.message;

import edu.sjsu.cs.cs151.minesweeper.model.Model;
import edu.sjsu.cs.cs151.minesweeper.view.View;

/**
 * A Message that contains information about changing the difficulty
 */
public class DifficultyMessage implements Message
{
	/**
	 * Creates the DifficultyMessage based on the options selected in the View
	 *
	 * @param difficulty the difficulty selected in the View using its View.Difficulty enum
	 * @param changeNow  Whether the Controller should restart the game with the given difficulty
	 */
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

	/**
	 * Gets the Model.Difficulty enum equivalent of the View.Difficulty used to construct this Message
	 *
	 * @return the Model.Difficulty enum equivalent of the View.Difficulty used to construct this Message
	 */
	public Model.Difficulty getDifficulty()
	{
		return difficulty;
	}

	/**
	 * Gets whether the Controller should restart the game with the given difficulty
	 *
	 * @return whether the Controller should restart the game with the given difficulty
	 */
	public boolean shouldBeChangedNow()
	{
		return changeNow;
	}

	private Model.Difficulty difficulty;
	private boolean changeNow;
}
