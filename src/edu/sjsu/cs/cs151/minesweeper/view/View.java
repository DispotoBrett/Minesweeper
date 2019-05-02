package edu.sjsu.cs.cs151.minesweeper.view;

import edu.sjsu.cs.cs151.minesweeper.controller.DifficultyMessage;
import edu.sjsu.cs.cs151.minesweeper.controller.ExitMessage;
import edu.sjsu.cs.cs151.minesweeper.controller.Message;
import edu.sjsu.cs.cs151.minesweeper.controller.ResetMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
	public enum Difficulty{EASY, MEDIUM, HARD}

	/**
	 * Gets the instance of view the returns the View
	 * @postcondition if a view has already been initialized,
	 * 				  a new one is NOT constructed, and the parameter will be ignored.
	 * @return the instance of view.
	 * * */
	public static View init(BlockingQueue<Message> messages)
	{
		if(instance == null)
			instance = new View(messages);
		return instance;
	}

	/**
	 * Get the instance of View
	 * @preconditon the view must have already been initialized.
	 * @return the instance of view.
	 */
	public View getInstance()
	{
		return instance;
	}

	/**
	 * Starts the game.
	 *
	 * @param rows     the number of rows of tiles that the View will display
	 * @param cols     the number of columns of tiles that the View will display
	 * @param adjMines the 2d array that stores the number of adjacent mines for each tile
	 */
	public void startGame(int rows, int cols, int[][] adjMines, Difficulty difficulty)
	{
		this.rows = rows;
		this.columns = cols;
		animationTimer.stop();
		frame.remove(welcome);
		initializeFrame(adjMines);
		initializeMenu(difficulty);
	}

	/**
	 * Puts on a display when the game has been won
	 */
	public void gameWon()
	{
		boardPanel.gameWon();
		GameWonAnimation winAnimation = new GameWonAnimation();

		animationTimer = new Timer(10, e -> {
			winAnimation.move();
			frame.repaint();
		});

		animationTimer.start();
		frame.setGlassPane(winAnimation);
		frame.getGlassPane().setVisible(true);
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
	public BlockingQueue<Message> getQueue()
	{
		return messageQueue;
	}

	public void resetTo(int row, int col, int[][] adjMines)
	{
		if (animationTimer != null && animationTimer.isRunning())
		{
			animationTimer.stop();
		}
		rows = row;
		columns = col;
		frame.remove(boardPanel);
		frame.getGlassPane().setVisible(false);
		boardPanel = new BoardPanel(rows, columns, messageQueue, frame, adjMines);
		frame.add(boardPanel);
		frame.pack();
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Disposes the View, its subcomponents, and all of its owned children.
	 */
	public void dispose()
	{
		frame.dispose();
	}

	/**
	 * Opens a dialog for user to confirm the change in difficulty.
	 *
	 * @return JOptionPane button chosen indicator
	 */
	public static int difficultyChanged()
	{
		return JOptionPane.showConfirmDialog(frame, "Reset the game now?");
	}

	//-------------------------Private Fields/Methods------------------
	private static View instance;
	private static JFrame frame;
	private BoardPanel boardPanel;
	private int rows;
	private int columns;
	private BlockingQueue<Message> messageQueue;
	private Timer animationTimer;
	private WelcomePanel welcome;
	private ButtonGroup difficulties;

	/**
	 * Constructor for View.
	 * @param messages the message queue.
	 */
	private View(BlockingQueue<Message> messages)
	{
		frame = new JFrame("Minesweeper");
		messageQueue = messages;

		initializeWelcomeMenu();
	}

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

	private void initializeMenu(Difficulty difficulty)
	{
		JMenuBar menuBar = new JMenuBar();

		JMenu game = new JMenu("Game");

		menuBar.add(game);

		JMenu difficultyMenu = new JMenu("Difficulty");

		difficulties = new ButtonGroup();
		JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
		easy.addActionListener(new ChangeDifficultyAction(Difficulty.EASY));
		difficulties.add(easy);
		difficultyMenu.add(easy);

		JRadioButtonMenuItem medium = new JRadioButtonMenuItem("Medium");
		medium.addActionListener(new ChangeDifficultyAction(Difficulty.MEDIUM));
		difficulties.add(medium);
		difficultyMenu.add(medium);

		JRadioButtonMenuItem hard = new JRadioButtonMenuItem("Hard");
		hard.addActionListener(new ChangeDifficultyAction(Difficulty.HARD));

		difficulties.add(hard);
		difficultyMenu.add(hard);

		switch (difficulty)
		{
		case EASY:
			easy.setSelected(true);
			break;

		case MEDIUM:
			medium.setSelected(true);
			break;

		case HARD:
			hard.setSelected(true);
			break;

		default:
			easy.setSelected(true);
			break;
		}

		game.add(difficultyMenu);

		JMenuItem startNew = new JMenuItem("Start New Game");
		startNew.addActionListener(e -> {
			try
			{
				messageQueue.put(new ResetMessage());
			}
			catch (InterruptedException e2)
			{
				e2.printStackTrace();
			}
		});

		game.add(startNew);

		game.addSeparator();

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e -> {
			try
			{
				messageQueue.put(new ExitMessage());
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		});
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
		animationTimer = new Timer(10, e -> {
			welcome.animate();
			frame.repaint();
		});
		animationTimer.start();
	}

	//-------------------------Private Classes------------------
	private class ChangeDifficultyAction implements ActionListener
	{
		public ChangeDifficultyAction(Difficulty difficulty)
		{
			this.difficulty = difficulty;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (JOptionPane.YES_OPTION == View.difficultyChanged())
			{
				try
				{
					messageQueue.put(new DifficultyMessage(difficulty, true));
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				try
				{
					messageQueue.put(new DifficultyMessage(difficulty, false));
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
		}

		private Difficulty difficulty;
	}

	private class GameWonAnimation extends JLabel
	{
		@Override
		public void paint(Graphics g)
		{
			if (winnerImage == null)
			{
				try
				{
					winnerImage = ImageIO.read(new File("resources/winner.png"));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(winnerImage, x, IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT, null, null);
		}

		public void move()
		{
			if (x < frame.getWidth())
			{
				x++;
			}
			else
			{
				x = -IMAGE_WIDTH;
			}
		}

		private int x = 0;
		private final int IMAGE_WIDTH = frame.getWidth() / 3;
		private final int IMAGE_HEIGHT = frame.getWidth() / 3;
		private final int IMAGE_Y = boardPanel.getY() + frame.getHeight() / 4;
		private BufferedImage winnerImage = null;
	}

}