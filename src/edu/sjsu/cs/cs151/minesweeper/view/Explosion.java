package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.Color;

public class Explosion extends JComponent implements Explodable
{
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private int x, y;
	private int fadeCount;
	private int count;
	private boolean fade;
	
	public Explosion(int x, int y)
	{
		this.x = x;
		this.y = y;
		width = 10;
		height = 10;
		count = 0;
		fadeCount = 255;
		fade = false;
	}

	public void explode()
	{
		if(count < 10)
		{
			if(width % 2 == 0)
			{
				x--;
				y--;
			}
			height++; 
			width++; 
			count++;
		}
		if(count > 80)
		{
			fade = true;
			if(fadeCount > 0)
				fadeCount--;
		}
		else
		{
			int delta = (int) ( 1.0/1000 * Math.pow(count, 3) );
			height += delta * 2;
			width += delta * 2;
			x -= delta;
			y -= delta;
			count++;
		}
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double explosion = new Ellipse2D.Double(x, y, width, height);

		if(!fade)
			g2.setColor(Color.RED);
		else
			g2.setColor(new Color(fadeCount, 0, 0));
	
		g2.fill(explosion);
	}	
}
