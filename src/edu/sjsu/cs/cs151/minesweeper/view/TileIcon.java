package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

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
		color = revealed ? Color.WHITE : Color.LIGHT_GRAY;
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
		color = revealed ? Color.WHITE : Color.LIGHT_GRAY;
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


		g2.setColor(color);
		g2.fill(rec);

		if (flagged)
		{
			Point2D.Double p1 = new Point2D.Double(FLAG_POLE_BASE_X, FLAG_POLE_BASE_Y);
			Point2D.Double p2 = new Point2D.Double(FLAG_POLE_TOP_X, FLAG_POLE_TOP_Y);
			Shape flagPole = new Line2D.Double(p1, p2);
			g2.setColor(Color.RED);
			g2.draw(flagPole);
			g2.fillPolygon(FLAG_COORDINATES_X, FLAG_COORDINATES_Y, FLAG_COORDINATES_X.length);
			g2.drawPolygon(FLAG_COORDINATES_X, FLAG_COORDINATES_Y, FLAG_COORDINATES_X.length);
		}
		
		else if(adjMines != 0)
		{
				g2.setColor(Color.BLACK);
				g2.drawString(adjMines + "", WIDTH/2, HEIGHT/2);
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
	private static final int FLAG_POLE_BASE_X = 10;
	private static final int FLAG_POLE_BASE_Y = 10;
	private static final int FLAG_POLE_TOP_X = 5;
	private static final int FLAG_POLE_TOP_Y = 20;
	private static final int[] FLAG_COORDINATES_X = {0, 15, 10};
	private static final int[] FLAG_COORDINATES_Y = {0, 0, 10};
	private Color color;
	private boolean flagged;
	private int adjMines;


}