package edu.sjsu.cs.cs151.minesweeper.tests;

import edu.sjsu.cs.cs151.minesweeper.model.Model;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ModelTests
{
	@Test
	public void newGameTest()
	{
		Model model = new Model();

		assertFalse(model.gameWon());
		assertFalse(model.gameLost());
	}

	@Test
	public void winTest()
	{
		Model model = new Model();
		model.setDifficulty(Model.Difficulty.EASY);

		assertFalse(model.gameWon());
		assertFalse(model.gameLost());

		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (!model.getBoard().isMine(i, j))
				{
					model.revealTile(i, j);
				}
			}
		}

		assertTrue(model.gameWon());
		assertFalse(model.gameLost());
	}

	@Test
	public void loseTest()
	{
		Model model = new Model();
		model.setDifficulty(Model.Difficulty.EASY);

		assertFalse(model.gameWon());
		assertFalse(model.gameLost());

		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (model.getBoard().isMine(i, j))
				{
					model.revealTile(i, j);
					break;
				}
			}
		}

		assertFalse(model.gameWon());
		assertTrue(model.gameLost());
	}

	@Test
	public void revealTileTest()
	{
		Model model = new Model();
		model.setDifficulty(Model.Difficulty.EASY);

		assertFalse(model.getBoard().getTileAt(0, 0).isRevealed());

		model.revealTile(0, 0);

		assertTrue(model.getBoard().getTileAt(0, 0).isRevealed());
	}

	@Test
	public void toggleFlagTest()
	{
		Model model = new Model();
		model.setDifficulty(Model.Difficulty.EASY);

		assertFalse(model.getBoard().getTileAt(0, 0).isFlagged());

		model.toggleFlag(0, 0);

		assertTrue(model.getBoard().getTileAt(0, 0).isFlagged());
	}
}