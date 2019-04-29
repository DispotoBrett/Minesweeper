package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.concurrent.BlockingQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.sjsu.cs.cs151.minesweeper.controller.Message;

/**
 * BoardPanel visually represents a Minesweeper board.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class BoardPanel extends JPanel
{
	/**
	 * Constructs a new BoardPanel.
	 *
	 * @param rows         the number of rows on board
	 * @param cols         the number of columns on board
	 * @param messageQueue the message queue to collect user input
	 * @param frame        the frame to be painted on (for explosions, which need glassPane)
	 * @param adjMines     the 2d array that stores the number of adjacent mines for each tile
	 */
	public BoardPanel(int rows, int cols, BlockingQueue<Message> messageQueue, JFrame frame, int[][] adjMines)
	{
		super();

		setLayout(new GridLayout(rows, cols));

		tileButtons = new TileButton[rows][cols];

		// Fills the boardPanel with buttons
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				TileButton button = new TileButton(i, j, messageQueue, frame, adjMines[i][j]);
				tileButtons[i][j] = button;
				add(button);
			}
		}
	}

	/**
	 * Reveals the tiles at the specified location
	 *
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void reveal(int row, int col)
	{
		tileButtons[row][col].reveal();
	}


	@Override
	public Dimension getPreferredSize()
	{
		if (tileButtons.length == EASY_ROW_SIZE)
		{
			return EASY_SIZE;
		}
		if (tileButtons.length == MED_ROW_SIZE)
		{
			return MEDIUM_SIZE;
		}
		else
		{
			return HARD_SIZE;
		}
	}

	/**
	 * Explodes the tiles at the specified location
	 *
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void explode(int row, int col)
	{
		tileButtons[row][col].explode();
	}

	/**
	 * Flags the tiles at the specified location
	 *
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void flag(int row, int col, boolean flag)
	{
		tileButtons[row][col].flag(flag);
	}

	/**
	 * Exposes the mine at the specified location
	 *
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void exposeMine(int row, int col)
	{
		tileButtons[row][col].exposeMine();
	}

	public void gameWon()
	{
	    for(int i = 0; i < tileButtons.length; i++)
	    {
		for(int j = 0; j < tileButtons[0].length; j++)
		    tileButtons[i][j].setBackground(new Color(255,215,0));
	    }
	}
	
	private TileButton[][] tileButtons;
	public static final int EASY_ROW_SIZE = 9;
	public static final int MED_ROW_SIZE = 16;
	public static final int HARD_ROW_SIZE = 24;
	public static final Dimension EASY_SIZE = new Dimension(TileIcon.WIDTH * EASY_ROW_SIZE, TileIcon.WIDTH * EASY_ROW_SIZE);
	public static final Dimension MEDIUM_SIZE = new Dimension(TileIcon.WIDTH * MED_ROW_SIZE, TileIcon.WIDTH * MED_ROW_SIZE);
	public static final Dimension HARD_SIZE = new Dimension(TileIcon.WIDTH * HARD_ROW_SIZE, TileIcon.WIDTH * HARD_ROW_SIZE);
}