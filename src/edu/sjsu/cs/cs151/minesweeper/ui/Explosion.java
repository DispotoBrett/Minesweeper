package edu.sjsu.cs.cs151.minesweeper.ui;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.Color;

public class Explosion extends JComponent implements Explodable
{
	private int width = 10;
	private int height = 10;
	private int x, y;
	private double count;

	public Explosion(int x, int y)
	{
		this.x = x;
		this.y = y;
		count = 0;
	}

	public void explode()
	{
		if(count < 10)
		{
			if(width % 2 == 1)
				x--;
			if(width % 3 == 1)
				y--;

			height++;
			width++; 
			count = count + .1;
		}
	
		else
		{
			int delta = (int) ( 1.0/100 * Math.pow(count, 3) );
			height += delta * 2;
			width += delta * 2;
			x -= delta;
			y -= delta;
			count++;
		}
	}
	
	public int getIconWidth(){return width;}
	public int getIconHeight(){return height;}
	

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double explosion = new Ellipse2D.Double(x, y, width, height);
		g2.setColor(Color.RED);
		g2.fill(explosion);
	}	
}
