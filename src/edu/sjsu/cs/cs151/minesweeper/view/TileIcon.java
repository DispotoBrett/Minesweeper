package edu.sjsu.cs.cs151.minesweeper.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
		this.isMine = isMine;
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
		this.isMine = isMine;
	}

	/* Constructs a new TileIcon.
	 *
	 * @param revealed indicates if the tile has been revealed
	 * @param flagged  indicates if the tile has been flagged
	 * @param isMine paints a mine on the icon if revealed
	 */
	public TileIcon(boolean revealed, boolean flagged, boolean isMine)
	{
		this.flagged = flagged;
		this.revealed = revealed;
		this.adjMines = 0;
		this.isMine = isMine;
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

		if (revealed && isMine)
		{
			g2.setColor(Color.black);
			g2.fill(new Ellipse2D.Double(BEVEL_BUFFER_X, BEVEL_BUFFER_Y, WIDTH - BEVEL_BUFFER, HEIGHT - BEVEL_BUFFER));
			g2.draw(new Ellipse2D.Double(BEVEL_BUFFER_X, BEVEL_BUFFER_Y, WIDTH - BEVEL_BUFFER, HEIGHT - BEVEL_BUFFER));
		}

		else if (flagged)
		{
			BufferedImage img = null;
			try {img = ImageIO.read(new File("resources\\flag.png"));}
			catch (IOException e) {e.printStackTrace();}
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
	public boolean isFlagged()
	{
		return flagged;
	}

	//----------------Private Methods/Fields----------------------
	private static final int WIDTH = 25, HEIGHT = 25;
	private static final int FLAG_POLE_BASE_Y = 10;
	private static final int FLAG_POLE_TOP_Y = 20;
	private static final int[] FLAG_COORDINATES_X = {5, WIDTH / 2, WIDTH / 2};
	private static final int[] FLAG_COORDINATES_Y = {HEIGHT / 4 + 2, 5, HEIGHT / 2};
	private static final int BASE1_X = 5;
	private static final int BASE2_X = 8;
	private static final int BASE1_Y = 21;
	private static final int BASE2_Y = 18;
	private static final int BASE1_WIDTH = WIDTH - 10;
	private static final int BASE2_WIDTH = WIDTH - 16;
	private static final int BASE1_HEIGHT = 21;
	private static final int BASE2_HEIGHT = 18;
	private static final int FONT_SIZE = 19;
	private static final int FONT_X = 7;
	private static final int FONT_Y = HEIGHT - 5;
	private static final int MIN_YELLOW = 2;
	private static final int MIN_RED = 4;
	private static final int BEVEL_BUFFER = 10;
	private static final int BEVEL_BUFFER_X = 4;
	private static final int BEVEL_BUFFER_Y = 5;
	private boolean flagged;
	private int adjMines;
	private boolean isMine;
	private boolean revealed;
}