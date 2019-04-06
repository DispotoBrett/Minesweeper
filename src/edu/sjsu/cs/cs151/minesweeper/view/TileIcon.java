package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.Icon;

public final class TileIcon implements Icon
{
	//----------------Public Interface-------------------------

	/**
	 * Constructs a new TileIcon.
	 * @param revealed indicates if the tile has been revealed
	 * @param flagged indicates if the tile has been flagged
	 * @param row the row of the tile in a board
	 * @param col the column of the tile in a board
	 */
	public TileIcon(boolean revealed, boolean flagged, int row, int col)
	{
		this.revealed = revealed;
		this.flagged  = flagged;
		this.row = row;
		this.col = col;
		color = revealed ? Color.WHITE : Color.LIGHT_GRAY;
	}
	
	/**
	 * Paints the Icon.
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

		if(flagged)
		{
			Point2D.Double p1 = new Point2D.Double(10, 10);
			Point2D.Double p2 = new Point2D.Double(5, 20);
			Shape flagPole = new Line2D.Double(p1 , p2);
			g2.setColor(Color.RED);
			g2.draw(flagPole);
			g2.fillPolygon(new int[]{0, 15, 10} , new int[]{0, 0, 10 },  3 );
			g2.drawPolygon(new int[]{0, 15, 10} , new int[]{0, 0, 10 },  3 );
		}
	}	

	/**
	 * Gets the icon's width.
	 * @return the icon's width.
	 */
	public int getIconWidth()
	{
		return WIDTH;
	}

	/**
	 * Gets the icon's height.
	 * @return the icon's height
	 */
	public int getIconHeight()
	{
		return HEIGHT;
	}

	/**
	 * Tells if the tile has been revealed
	 * @return boolean indicating if the tile has been revealed
	 */
	public boolean isRevealed()
	{
		return revealed;
	}
	
	/**
	 * Tells if the tile has been flagged
	 * @return boolean indicating if the tile has been flagged
	 */
	public boolean isFlagged()
	{
		return flagged;
	}
	
	/**
	 * Gets the tile's row in a Board.
	 * @return the icon's row
	 */
	public int getRow()
	{
		return row;
	}
	
	/**
	 * Gets the tile's column in a Board.
	 * @return the icon's column
	 */
	public int getCol()
	{
		return col;
	}
	
	//----------------Private Methods/Fields----------------------

	private static final int WIDTH = 25, HEIGHT = 25;
	private Color color;
	private boolean revealed;
	private boolean flagged;
	private int row;
	private int col;

}