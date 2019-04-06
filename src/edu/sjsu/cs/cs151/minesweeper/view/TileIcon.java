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

public class TileIcon implements Icon
{
	int width, height;
	Color color;
	boolean revealed;
	boolean flagged;

	public TileIcon(boolean revealed, boolean flagged, int row, int col)
	{
		this.width 	= 25;
		this.height = 25;
		this.revealed = revealed;
		this.flagged  = flagged;
		
		color = revealed ? Color.WHITE : Color.GRAY;
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		Graphics2D g2 = (Graphics2D) g;
		Rectangle rec = new Rectangle(width, height);


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

	public int getIconWidth()
	{
		return width;
	}

	public int getIconHeight()
	{
		return height;
	}

	public boolean isRevealed()
	{
		return revealed;
	}
}