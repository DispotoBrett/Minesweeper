package edu.sjsu.cs.cs151.minesweeper.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class TileIcon implements Icon
{
	//----------------Public Interface-------------------------
	public enum TileState
	{
		revealed, unrevealed, flagged, mineShowing
	}

	/**
	 * Constructs a TileIcon based on the current state of the Tile it represents.
	 *
	 * @param state The state of the Tile that this TileIcon represents.
	 */
	public TileIcon(TileState state)
	{
		try
		{
			if (flagIcon == null)
			{
				flagIcon = ImageIO.read(new File("resources/flag.png"));
			}
			if (mineIcon == null)
			{
				mineIcon = ImageIO.read(new File("resources/mine.png"));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		adjMines = 0;
		this.state = state;
	}

	/**
	 * Constructs a TileIcon based on the current state of the Tile it represents, and the number of adjacent mines.
	 *
	 * @param state    The state of the Tile that this TileIcon represents.
	 * @param adjMines The number of mines adjacent to the Tile that this TileIcon represents.
	 */
	public TileIcon(TileState state, int adjMines)
	{
		this(state);
		this.adjMines = adjMines;
	}

	/**
	 * Paints the Icon.
	 *
	 * @param c the component being painted on
	 * @param g the graphics context
	 * @param x the x-coordinate of the bounding rectangle
	 * @param y the y-coordinate of the bounding rectangle
	 */
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		Graphics2D g2 = (Graphics2D) g;
		Rectangle rec = new Rectangle(WIDTH, HEIGHT);
		g2.setColor((state == TileState.revealed) ? Color.WHITE : Color.LIGHT_GRAY);
		g2.fill(rec);

		if (state == TileState.mineShowing)
		{
			g2.drawImage(mineIcon, 0, 0, WIDTH, HEIGHT, g2.getColor(), null);
		}
		else if (state == TileState.flagged)
		{
			g2.drawImage(flagIcon, 0, 0, WIDTH, HEIGHT, Color.LIGHT_GRAY, null);
		}
		else if (adjMines != 0)
		{
			Color fontColor;
			if (adjMines >= MIN_YELLOW)
			{
				if (adjMines >= MIN_RED)
				{
					fontColor = Color.RED;
				}
				else
				{
					fontColor = Color.ORANGE;
				}
			}
			else
			{
				fontColor = Color.BLACK;
			}
			g2.setFont(new Font("Monospaced", Font.BOLD, FONT_SIZE));
			g2.setColor(fontColor);
			g2.drawString(adjMines + "", FONT_X, FONT_Y);
		}
	}

	/**
	 * Gets the icon's width.
	 *
	 * @return the icon's width.
	 */
	public int getIconWidth()
	{
		return WIDTH;
	}

	/**
	 * Gets the icon's height.
	 *
	 * @return the icon's height
	 */
	public int getIconHeight()
	{
		return HEIGHT;
	}

	//----------------Private Methods/Fields----------------------
	public static final int WIDTH = (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
		  .getDisplayMode().getWidth() / 76.8);
	public static final int HEIGHT = (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
		  .getDisplayMode().getWidth() / 76.8);

	private static final int FONT_SIZE = (int) (HEIGHT * .76);
	private static final int FONT_X = (int) (HEIGHT * .28);
	private static final int FONT_Y = HEIGHT - (int) (HEIGHT * .2);

	private static final int MIN_YELLOW = 2;
	private static final int MIN_RED = 4;

	private static BufferedImage flagIcon = null;
	private static BufferedImage mineIcon = null;

	private TileState state;

	private int adjMines;
}