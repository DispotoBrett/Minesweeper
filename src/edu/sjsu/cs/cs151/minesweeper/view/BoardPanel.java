package edu.sjsu.cs.cs151.minesweeper.view;

import edu.sjsu.cs.cs151.minesweeper.controller.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * BoardPanel visually represents a Minesweeper board.
 *
 * @author JordanConraganx
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class BoardPanel extends JPanel
{
	/**
	 * Constructs a BoardPanel.
	 *
	 * @param rows         The number of rows in the Board.
	 * @param cols         The number of columns in the Board.
	 * @param messageQueue The message queue to collect user input.
	 * @param frame        The frame to be painted on (for explosions) (glass pane).
	 * @param adjMines     The 2D array that stores the number of adjacent mines for each tile.
	 */
	public BoardPanel(int rows, int cols, BlockingQueue<Message> messageQueue, JFrame frame, int[][] adjMines)
	{
		super();

		setLayout(new GridLayout(rows, cols));
		parentFrame = frame;
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
	 * Reveals the Tiles visually at the specified location.
	 *
	 * @param row The row of the Tile to be revealed.
	 * @param col The column of the Tile to be revealed.
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
	 * Explodes the tiles at the specified location.
	 *
	 * @param row The row of the Tile to be exploded.
	 * @param col The column of the tile to be exploded.
	 */
	public void explode(int row, int col)
	{
		tileButtons[row][col].explode();
	}

	/**
	 * Flags the tile at the specified location.
	 *
	 * @param row The row of the tile to be flagged.
	 * @param col The column of the tile to be flagged.
	 */
	public void flag(int row, int col, boolean flag)
	{
		tileButtons[row][col].flag(flag);
	}

	/**
	 * Exposes the mine at the specified location.
	 *
	 * @param row The row of the tile which should have an exposed mine.
	 * @param col The column of the tile which should have an exposed mine.
	 */
	public void exposeMine(int row, int col)
	{
		tileButtons[row][col].exposeMine();
	}

	/**
	 * Makes the tiles colorful upon winning the game.
	 */
	public void colorTiles()
	{
		for (int i = 0; i < tileButtons.length; i++)
		{
			for (int j = 0; j < tileButtons[0].length; j++)
			{
				switch ((j + i) % 3)
				{
				case 0:
					tileButtons[i][j].setBackground(Color.yellow);
					break;

				case 1:

					tileButtons[i][j].setBackground(Color.cyan);
					break;

				case 2:
					tileButtons[i][j].setBackground(Color.red);
					break;
				}
			}
		}
	}

	/**
	 * Animation displaying "Game Won!" upon winning the game.
	 */
	public void gameWonAnimate()
	{
		GameWonAnimation winAnimation = new GameWonAnimation();

		gameWonTimer = new Timer(ANIMATION_DELAY, (e) -> {
			winAnimation.move();
			parentFrame.repaint();
		});
		gameWonTimer.start();

		parentFrame.setGlassPane(winAnimation);
		parentFrame.getGlassPane().setVisible(true);
	}

	/**
	 * Stops the "Game Won" animation from moving and repainting.
	 */
	public void stopAnimation()
	{
		if (gameWonTimer != null && gameWonTimer.isRunning())
		{
			gameWonTimer.stop();
		}
		parentFrame.getGlassPane().setVisible(false);
	}

	private static final int ANIMATION_DELAY = 12;
	private TileButton[][] tileButtons;
	private JFrame parentFrame; //for glass pane purposes

	private static final int EASY_ROW_SIZE = 9;
	private static final int MED_ROW_SIZE = 12;
	private static final int HARD_ROW_SIZE = 16;

	private static final Dimension EASY_SIZE = new Dimension(TileIcon.WIDTH * EASY_ROW_SIZE,
		  TileIcon.WIDTH * EASY_ROW_SIZE);
	private static final Dimension MEDIUM_SIZE = new Dimension(TileIcon.WIDTH * MED_ROW_SIZE,
		  TileIcon.WIDTH * MED_ROW_SIZE);
	private static final Dimension HARD_SIZE = new Dimension(TileIcon.WIDTH * HARD_ROW_SIZE,
		  TileIcon.WIDTH * HARD_ROW_SIZE);

	private javax.swing.Timer gameWonTimer;

	/**
	 * Responsible for animating the "Game Won" banner.
	 */
	private class GameWonAnimation extends JLabel
	{
		/**
		 * Constructs the GameWonAnimation.
		 */
		public GameWonAnimation()
		{
			try
			{
				winnerImage = ImageIO.read(new File(PATH));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void paint(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(winnerImage, x, IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT, null, null);
		}

		/**
		 * Moves the banner along the frame.
		 */
		public void move()
		{
			//while the banner is within the frame, move it, otherwise, send it to beginning of the frame
			if (x < parentFrame.getWidth())
			{
				x++;
			}
			else
			{
				x = -IMAGE_WIDTH;
			}
		}

		private int x = 0;

		private final int IMAGE_WIDTH = parentFrame.getWidth() / 3;
		private final int IMAGE_HEIGHT = parentFrame.getWidth() / 3;
		private final int IMAGE_Y = BoardPanel.this.getY() + parentFrame.getHeight() / 4;

		private static final String PATH = "resources/winner.png";

		private BufferedImage winnerImage = null;
	}
}