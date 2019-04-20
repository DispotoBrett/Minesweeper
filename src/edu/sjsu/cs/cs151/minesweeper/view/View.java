package edu.sjsu.cs.cs151.minesweeper.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
	public static final int EXIT = 0;
	public static final int RIGHT_CLICK = 1;
	public static final int LEFT_CLICK = 2;
	public static final int RESET_GAME = 3;
	public static final int EASY_DIFFICULTY = 4;
	public static final int MEDIUM_DIFFICULTY = 5;
	public static final int HARD_DIFFICULTY = 6;


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
		messageQueue = new ArrayBlockingQueue<int[]>(1000); //TODO: reassess

		initializeFrame(adjMines);
		initializeMenu();
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
	 * Shows the mine at the specified location
	 *
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void exposeMine(int row, int col)
	{
		boardPanel.exposeMine(row, col);
	}

	/**
	 * Gets the message queue (messages from user input).
	 *
	 * @return the message queue
	 */
	public BlockingQueue<int[]> getQueue()
	{
		return messageQueue;
	}

	public void resetTo(int row, int col, int[][] adjMines)
	{
		this.rows = row;
		columns = col;
		frame.remove(boardPanel);

		boardPanel = new BoardPanel(rows, columns, messageQueue, frame, adjMines);
		frame.add(boardPanel);
		frame.pack();
	}

	//-------------------------Private Fields/Methods------------------
	private JFrame frame;
	private BoardPanel boardPanel;
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

		frame.setLayout(new BorderLayout());

		boardPanel = new BoardPanel(rows, columns, messageQueue, frame, adjMines);

		frame.add(boardPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(new File("resources\\mine.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		frame.setIconImage(img);
		frame.setVisible(true);
	}

	private void initializeMenu()
	{

		JMenuBar menuBar = new JMenuBar();

		JMenu game = new JMenu("Game");

		menuBar.add(game);

		JMenu difficultyMenu = new JMenu("Difficulty");

		ButtonGroup difficulties = new ButtonGroup();
		JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
		easy.addActionListener(e -> messageQueue.add(new int[]{-1, -1, EASY_DIFFICULTY}));
		easy.setSelected(true);
		difficulties.add(easy);
		difficultyMenu.add(easy);

		JRadioButtonMenuItem medium = new JRadioButtonMenuItem("Medium");
		medium.addActionListener(e -> messageQueue.add(new int[]{-1, -1, MEDIUM_DIFFICULTY}));
		difficulties.add(medium);
		difficultyMenu.add(medium);

		JRadioButtonMenuItem hard = new JRadioButtonMenuItem("Hard");
		hard.addActionListener(e -> messageQueue.add(new int[]{-1, -1, HARD_DIFFICULTY}));
		difficulties.add(hard);
		difficultyMenu.add(hard);


		game.add(difficultyMenu);

		JMenuItem startNew = new JMenuItem("Start New Game");
		startNew.addActionListener(e -> messageQueue.add(new int[]{-1, -1, RESET_GAME}));

		game.add(startNew);

		game.addSeparator();

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e -> messageQueue.add(new int[]{-1, -1, EXIT}));
		game.add(exit);

		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		JMenuItem howTo = new JMenuItem("How to Play");

		help.add(about);
		help.add(howTo);

		menuBar.add(help);

		frame.setJMenuBar(menuBar);
		frame.pack();
	}
}