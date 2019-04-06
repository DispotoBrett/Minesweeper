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
					JButton b = new JButton(new TileIcon(false, false, 0 , 0));
					b.addMouseListener(new MouseAdapter() { 
				          public void mousePressed(MouseEvent me)
				          { 
				          		boolean flagged = ((TileIcon) b.getIcon()).isFlagged();
				          		boolean revealed = ((TileIcon) b.getIcon()).isRevealed();

				          		if(revealed) return;

								if(SwingUtilities.isLeftMouseButton(me));
								{
									if(!flagged)
										b.setIcon(new TileIcon(true, false, 0 , 0 ));
								}   

								if(SwingUtilities.isRightMouseButton(me)) 
								{
									if( !flagged)
										b.setIcon(new TileIcon(false, true, 0 , 0 ));
									else
										b.setIcon(new TileIcon(false, false, 0 , 0 ));

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
}