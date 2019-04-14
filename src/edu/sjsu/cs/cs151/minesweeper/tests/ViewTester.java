package edu.sjsu.cs.cs151.minesweeper.tests;

import javax.swing.SwingUtilities;

import edu.sjsu.cs.cs151.minesweeper.model.Board;
import edu.sjsu.cs.cs151.minesweeper.model.Model;
import edu.sjsu.cs.cs151.minesweeper.view.View;

public class ViewTester
{
	static Model model = new Model();
	static View view;
	static final int ROW_INDEX = 0;
	static final int COL_INDEX = 1;
	static int[] msg;

	public static void main(String[] args) throws InterruptedException
	{
		view = new View(Board.NUM_ROWS, Board.NUM_COLS, model.getBoard().adjacentMines());

		while (true)
		{
			if (!view.getQueue().isEmpty())
			{
				msg = view.getQueue().remove();
				if (msg[2] == View.LEFT_CLICK)
				{
					model.revealTile(msg[ROW_INDEX], msg[COL_INDEX]); //Comment to test explosion
					//view.explode(msg[ROW_INDEX], msg[COL_INDEX]); break; //uncomment to test explosion
				}
				else if (msg[2] == View.RIGHT_CLICK)
				{
					model.getTileAt(msg[ROW_INDEX], msg[COL_INDEX]).toggleFlag();
				}
				SwingUtilities.invokeLater( () -> sync());
			}
		}
	}

	private static void sync()
	{
		for (int i = 0; i < Board.NUM_ROWS; i++)
		{
			for (int j = 0; j < Board.NUM_COLS; j++)
			{
				if (model.getBoard().getTileAt(i, j).isRevealed())
				{
					view.reveal(i, j);
				}
				else
				{
					view.flag(i, j, model.getBoard().getTileAt(i, j).isFlagged());
				}
			}
		}
		if(model.gameLost()) 
		{
			view.explode(msg[View.LEFT_CLICK], msg[View.RIGHT_CLICK]);
		}
	}
}