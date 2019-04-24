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

	/**
	 * Constructs a new TileIcon.
	 *
	 * @param revealed indicates if the tile has been revealed
	 * @param flagged  indicates if the tile has been flagged
	 */
	public TileIcon(boolean revealed, boolean flagged)
	{
		this.flagged = flagged;
		this.revealed = revealed;
		this.adjMines = 0;
	}

	/**
	 * Constructs a new TileIcon.
	 *
	 * @param revealed indicates if the tile has been revealed
	 * @param flagged  indicates if the tile has been flagged
	 * @param adjMines the number of mines adjacent to this mine
	 */
	public TileIcon(boolean revealed, boolean flagged, int adjMines)
	{
		this.flagged = flagged;
		this.revealed = revealed;
		this.adjMines = adjMines;
	}

	/* Constructs a new TileIcon.
	 *
	 * @param revealed indicates if the tile has been revealed
	 * @param flagged  indicates if the tile has been flagged
	 * @param showMine paints a mine on the icon if revealed
	 */
	public TileIcon(boolean revealed, boolean flagged, boolean showMine)
	{
		this.flagged = flagged;
		this.revealed = revealed;
		this.adjMines = 0;
		this.showMine = showMine;
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
		g2.setColor(revealed ? Color.WHITE : Color.LIGHT_GRAY);
		g2.fill(rec);

		if (showMine)
		{
			BufferedImage img = null;
			try
			{
				img = ImageIO.read(new File("resources/mine.png"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			g2.drawImage(img, 0, 0, WIDTH, HEIGHT, g2.getColor(), null);
		}

		else if (flagged)
		{
			BufferedImage img = null;
			try
			{
				img = ImageIO.read(new File("resources/flag.png"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			g2.drawImage(img, 0, 0, WIDTH, HEIGHT, Color.LIGHT_GRAY, null);
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

	/**
	 * Tells if the tile has been flagged
	 *
	 * @return boolean indicating if the tile has been flagged
	 */
	public boolean isFlagged()    //FIXME unused?
	{
		return flagged;
	}

	//----------------Private Methods/Fields----------------------
	//private static final int WIDTH = 25, HEIGHT = 25;
	public static final int WIDTH = (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 76.8);
	public static final int HEIGHT = WIDTH;
	private static final int FONT_SIZE = (int) (HEIGHT * .76);
	private static final int FONT_X = (int) (HEIGHT * .28);
	private static final int FONT_Y = HEIGHT - (int) (HEIGHT * .2);
	private static final int MIN_YELLOW = 2;
	private static final int MIN_RED = 4;
	private boolean flagged;
	private int adjMines;
	private boolean showMine;
	private boolean revealed;
}