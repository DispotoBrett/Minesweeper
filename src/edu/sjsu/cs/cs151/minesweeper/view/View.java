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
	 * @param rows the number of rows of tiles that the View will display
	 * @param cols the number of columns of tiles that the View will display
	 */
	public View(int rows, int cols)
	{
		this.rows = rows;
		this.columns = cols;
		messageQueue = new ArrayBlockingQueue<int[]>(10); //TODO: reassess
		initializeFrame();
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
	 * @param row the row the tile
	 * @param col the column of the tile
	 * @param adjMines the number of mines adjacent to this mine
	 */
	public void reveal(int row, int col, int adjMines)
	{
		boardPanel.reveal(row, col, adjMines);
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
	 */
	private void initializeFrame()
	{
		frame = new JFrame("Minesweeper");
		boardPanel = new BoardPanel(rows, columns, messageQueue, frame);

		frame.add(boardPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(400, 400);
		frame.pack();
		frame.setVisible(true);
	}
}