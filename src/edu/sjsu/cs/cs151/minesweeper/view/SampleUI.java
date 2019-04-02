package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;

public class SampleUI
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Minesweeper");
		
		JButton[][] buttons = new JButton[9][9];
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
				{
					JButton b = new JButton(new TileIcon(Color.GRAY, false));
					b.addMouseListener(new MouseAdapter() { 
				          public void mousePressed(MouseEvent me)
				          { 
				          		boolean flagged = ((TileIcon) b.getIcon()).flagged;
				          		boolean revealed = ((TileIcon) b.getIcon()).revealed;

				          		if(revealed) return;

								if(SwingUtilities.isLeftMouseButton(me));
								{
									if(!flagged)
										b.setIcon(new TileIcon(Color.WHITE, false));
								}   

								if(SwingUtilities.isRightMouseButton(me)) 
								{
									if( !flagged)
										b.setIcon(new TileIcon(Color.GRAY, true));
									else
										b.setIcon(new TileIcon(Color.GRAY, false));

								}        
						  } 
				        }); 
						b.setPreferredSize(new Dimension(25, 25));
						frame.setResizable(false);
						frame.add(b);
						buttons[i][j] = b;

				}
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout(10));

		frame.setSize(292,312);
		frame.setVisible(true);
	}

	private static class TileIcon implements Icon
	{
		int width, height;
		Color color;
		boolean revealed;
		boolean flagged;

		public TileIcon(Color color, boolean flagged)
		{
			width = 25;
			height = 25;
			this.color = color;
			this.flagged = flagged;
			if(color == Color.WHITE)	revealed = true;
			else revealed = false;

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

		public Color getColor()
		{
			return color;
		}
	}

}