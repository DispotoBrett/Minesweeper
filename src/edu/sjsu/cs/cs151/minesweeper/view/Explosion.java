package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.Color;

/**
 * Draws an explosion in a frame (swing).
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class Explosion extends JComponent implements Explodable
{
	//----------------Public Interface-------------------------

	/**
	 * Creates a new explosion instance.
	 * @param x the x-coordinate of the upper left-hand corner
	 * 					of the bounding rectangle of the explosion.
	 * @param y the y-coordinate of the upper left-hand corner 
	 * 					of the bounding rectangle of the explosion.
	 */
	public Explosion(int x, int y)
	{
		this.x = x;
		this.y = y;
		width  = 0;
		height = 0;
		count  = 0;
		fadeCount = RGB_RED_INIT;
	}

	/**
	 * Initiates the explosion animation.
	 */
	public void explode()
	{
		if(fadeCount > 0)
			fadeCount--;
	
		int delta = (int) ( 1.0/1000 * Math.pow(count, 3) );
		height += delta * 2;
		width += delta * 2;
		x -= delta;
		y -= delta;
		count++;
	}

	/**
	 * Paints the explosion onto the graphics context.
	 * @param g the graphics context.
	 */
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double explosion = new Ellipse2D.Double(x, y, width, height);

		g2.setColor(new Color(fadeCount, 0, 0));
		g2.fill(explosion);
	}	
	
	//----------------Private Methods/Fields----------------------
	private int x, y, height, width, fadeCount, count;
	private static final int RGB_RED_INIT = 255;
	private static final long serialVersionUID = 1L;
}
