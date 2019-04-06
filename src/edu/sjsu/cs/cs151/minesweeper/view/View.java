package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class View
{
	
	public static final int RIGHT_CLICK = 1;
	public static final int LEFT_CLICK = 2;
	
	public View(int row, int col)
	{
		rows = row;
		columns = col;
		messageQueue = new LinkedList<>();
		initializeFrame();
	}
	
	public void change()
	{
		//TODO
	}
	
	public Queue<int[]> getQueue()
	{
		return messageQueue;
	}
	
	//-------------------------Private Fields/Methods------------------
	JFrame frame;
	JPanel panel;
	private int rows;
	private int columns;
	private Queue<int[]> messageQueue;
	
	private void initializeFrame()
	{
		frame = new JFrame("Minesweeper");
		panel = new JPanel();
		panel.setLayout(new GridLayout(rows, columns));
		
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				messageQueue.add(new int[] {1,2,3});
				JButton button = new JButton(new TileIcon(false, false, i, j));
				
				button.addMouseListener( new MouseAdapter() 
				{
					public void MouseClicked(MouseEvent e)
					{
						int row = ((TileIcon) button.getIcon()).getRow();
						int col = ((TileIcon) button.getIcon()).getCol();
						if(SwingUtilities.isLeftMouseButton(e)) 
							messageQueue.add(new int[] {row, col, LEFT_CLICK});
						else if(SwingUtilities.isRightMouseButton(e)) 
							messageQueue.add(new int[] {row, col, RIGHT_CLICK});
					}	
				});
						
				panel.add(button);
			}
		}
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(400,400);
		frame.pack();
		
		
		frame.setVisible(true);
		panel.setVisible(true);
	}
}
