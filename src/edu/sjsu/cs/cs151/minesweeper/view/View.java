package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * View class for Minesweeper using MVC pattern.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class View
{
	public static final int RIGHT_CLICK = 1;
	public static final int LEFT_CLICK = 2;

	/**
	 * Constructor for View
	 *
	 * @param rows     the number of rows of tiles that the View will display
	 * @param cols     the number of columns of tiles that the View will display
	 * @param adjMines the 2d array that stores the number of adjacent mines for each tile
	 */
	public View(int rows, int cols, int[][] adjMines)
	{
		this.rows = rows;
		this.columns = cols;
		messageQueue = new ArrayBlockingQueue<int[]>(10); //TODO: reassess
		initializeFrame(adjMines);
	}

	/**
	 * Updates View subsequent to changes in Model.
	 */
	public void change()
	{
		//TODO
	}

	/**
	 * Reveals the tiles at the specified location
	 *
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void reveal(int row, int col)
	{
		boardPanel.reveal(row, col);
	}

	/**
	 * Explodes the tiles at the specified location
	 *
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void explode(int row, int col)
	{

		boardPanel.explode(row, col);
	}


	/**
	 * Flags the tiles at the specified location
	 *
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void flag(int row, int col, boolean flag)
	{
		boardPanel.flag(row, col, flag);
	}

	/**
	 * Gets the message queue (messages from user input).
	 *
	 * @return the message queue
	 */
	public Queue<int[]> getQueue()
	{
		return messageQueue;
	}

	//-------------------------Private Fields/Methods------------------
	JFrame frame;
	BoardPanel boardPanel;
	private int rows;
	private int columns;
	private BlockingQueue<int[]> messageQueue;

	/**
	 * Creates frame, creates the boardPanel, and fills the boardPanel with buttons
	 * Created to keep the constructor relatively clear
	 *
	 * @param adjMines the 2d array that stores the number of adjacent mines for each tile
	 */
	private void initializeFrame(int[][] adjMines)
	{
		frame = new JFrame("Minesweeper");
		boardPanel = new BoardPanel(rows, columns, messageQueue, frame, adjMines);

		frame.add(boardPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}