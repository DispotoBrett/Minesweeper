package edu.sjsu.cs.cs151.minesweeper.ui;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;

public class Explosion extends JComponent implements Explodable
{
	private int width = 10;
	private int height = 10;
	private int x, y;

	public Explosion(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void explode()
	{
		if(width % 2 == 1)
			x--;
		if(width % 3 == 1)
			y--;

		height++;
		width++; 
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