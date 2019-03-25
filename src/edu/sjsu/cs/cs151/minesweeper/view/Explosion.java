package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;
import java.awt.*;

/**
 * Draws an explosion in a content pane (swing).
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
	 * @param contentPaneWidth the width of the frame.
	 * @param contentPaneHeight the height of the frame.
	 */
	public Explosion(int x, int y, int contentPaneWidth, int contentPaneHeight)
	{
		this.x = x;
		this.y = y;
		width  = 0;
		height = 0;
		count  = 0;
		fadeCount = RGB_RED_INIT;
		this.contentPaneWidth = contentPaneWidth;
		this.contentPaneHeight = contentPaneHeight;
	}

	/**
	 * Initiates the explosion animation.
	 */
	public void explode()
	{
		if(fadeCount > 0)
			fadeCount--;
		
		if(width < contentPaneWidth * CONTENT_PANE_OFFSET)
		{			
			int delta = (int) (DELTA_OFFSET * Math.pow(count, EXPONENT));
			height += delta * 2;
			width += delta * 2;
			x -= delta;
			y -= delta;
			count++;
		}
	}

	/**
	 * Paints the explosion onto the graphics context.
	 * @param g the graphics context.
	 */
	public void paint(Graphics g)
	{
		//Explosion
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double explosion = new Ellipse2D.Double(x, y, width, height);

		g2.setColor(new Color(fadeCount, 0, 0));
		g2.fill(explosion);

		//"Game over" text
		String text = "GAME OVER";
		Font font = new Font("TimesRoman", Font.PLAIN, FONT_SIZE);
		g2.setFont(font);
		FontRenderContext context = g2.getFontRenderContext();

		//get bounding rectangle of string
		Rectangle2D bounds = font.getStringBounds(text, context);

		double ascent = -bounds.getY();
		double descent = bounds.getHeight() - ascent;
		double extent = bounds.getWidth();

		g2.setColor(new Color(
				Color.WHITE.getRed(),
				Color.WHITE.getGreen(),
				Color.WHITE.getBlue(),
				Color.WHITE.getAlpha() - fadeCount
		));

		//draw string at center of given frame
		g2.drawString(
				text,
				(int) (contentPaneWidth - extent) / 2,
				(int) (((contentPaneHeight - (ascent + descent)) / 2) + ascent)
		);
	}
	
	//----------------Private Methods/Fields----------------------
	private int x, y, height, width, fadeCount, count, contentPaneWidth, contentPaneHeight;
	private static final int RGB_RED_INIT = 255;
	private static final int CONTENT_PANE_OFFSET = 2;
	private static final int EXPONENT = 3;
	private static final int FONT_SIZE = 48;
	private static final double DELTA_OFFSET = 0.001;
	private static final long serialVersionUID = 1L;
}