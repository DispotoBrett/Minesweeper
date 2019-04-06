package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.GridLayout;
import java.util.Queue;

import javax.swing.JFrame;

public class View
{
	public View(int row, int col)
	{
		rows = row;
		columns = col;
		
		initializeFrame();
	}
	
	public void change()
	{
		//TODO
	}
	
	public Queue<Object[]> getQueue()
	{
		return messageQueue;
	}
	
	//-------------------------Private Fields/Methods------------------
	JFrame frame;
	private int rows;
	private int columns;
	private Queue<Object[]> messageQueue;
	
	private void initializeFrame()
	{
		frame = new JFrame("Minesweeper");
		
		frame.setLayout(new GridLayout(rows, columns));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(400,400);
		frame.pack();
		
		frame.setVisible(true);
	}
}
