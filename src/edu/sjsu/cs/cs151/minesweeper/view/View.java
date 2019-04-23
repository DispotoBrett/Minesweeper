package edu.sjsu.cs.cs151.minesweeper.view;

import javax.imageio.ImageIO;
import javax.swing.*;


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
	 */
	public View()
	{
		frame = new JFrame("Minesweeper");
		messageQueue = new ArrayBlockingQueue<int[]>(1000); //TODO: reassess

		initializeWelcomeMenu();
	}

	/**
	 * Starts the game.
	 *
	 * @param rows     the number of rows of tiles that the View will display
	 * @param cols     the number of columns of tiles that the View will display
	 * @param adjMines the 2d array that stores the number of adjacent mines for each tile
	 */
	public void startGame(int rows, int cols, int[][] adjMines, int difficulty)
	{
		this.rows = rows;
		this.columns = cols;
		welcomeMenuHelper.stop();
		frame.remove(welcome);
		initializeFrame(adjMines);
		initializeMenu(difficulty);
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
		rows = row;
		columns = col;
		frame.remove(boardPanel);
		frame.getGlassPane().setVisible(false);
		
		boardPanel = new BoardPanel(rows, columns, messageQueue, frame, adjMines);
		frame.add(boardPanel);
		frame.pack();
	}

	/**
	 * Opens a dialog for user to confirm the change in difficulty.
	 * @return JOptionPane button chosen indicator
	 */
	public static int difficultyChanged()
	{
	    return JOptionPane.showConfirmDialog(frame, "Reset the game now?");
	}

	//-------------------------Private Fields/Methods------------------
	private static JFrame frame;
	private BoardPanel boardPanel;
	private int rows;
	private int columns;
	private BlockingQueue<int[]> messageQueue;
	private Timer welcomeMenuHelper;
	private WelcomePanel welcome;


	/**
	 * Creates frame, creates the boardPanel, and fills the boardPanel with buttons
	 * Created to keep the constructor relatively clear
	 *
	 * @param adjMines the 2d array that stores the number of adjacent mines for each tile
	 */
	private void initializeFrame(int[][] adjMines)
	{
		boardPanel = new BoardPanel(rows, columns, messageQueue, frame, adjMines);

		frame.add(boardPanel);

		frame.pack();
	}

	private void initializeMenu(int difficulty)
	{
		JMenuBar menuBar = new JMenuBar();

		JMenu game = new JMenu("Game");

		menuBar.add(game);

		JMenu difficultyMenu = new JMenu("Difficulty");

		ButtonGroup difficulties = new ButtonGroup();
		JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
		easy.addActionListener(e -> messageQueue.add(new int[]{-1, -1, EASY_DIFFICULTY}));
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
		
		switch(difficulty)
		{
		case EASY_DIFFICULTY: easy.setSelected(true); break;
		
		case MEDIUM_DIFFICULTY: medium.setSelected(true); break;
		
		case HARD_DIFFICULTY: hard.setSelected(true); break;
		
		default: easy.setSelected(true); break;
		}

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
		frame.setLocationRelativeTo(null);
	}

	private void initializeWelcomeMenu()
	{
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(new File("resources/mine.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		frame.setIconImage(img);
		welcome = new WelcomePanel(messageQueue);
		frame.add(welcome);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		welcomeMenuHelper = new Timer(10, e -> {
			welcome.animate();
			frame.repaint();
		});
		welcomeMenuHelper.start();
	}
}