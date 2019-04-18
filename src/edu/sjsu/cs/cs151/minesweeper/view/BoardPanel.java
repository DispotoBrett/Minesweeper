package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.concurrent.BlockingQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
	public BoardPanel(int rows, int cols, BlockingQueue<int[]> messageQueue, JFrame frame, int[][] adjMines)
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
		return new Dimension(225, 225);
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

	private TileButton[][] tileButtons;
}