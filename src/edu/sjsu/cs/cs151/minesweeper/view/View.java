package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.*;

public class View
{
	public static final int RIGHT_CLICK = 1;
	public static final int LEFT_CLICK = 2;
	
	/**
	 * Constructor for View
	 * @param rows the number of rows of tiles that the View will display
	 * @param cols the number of columns of tiles that the View will display
	 */
	public View(int rows, int cols)
	{
		this.rows = rows;
		this.columns = cols;
		messageQueue = new ArrayBlockingQueue<int[]>(1); //TODO: fix param
		initializeFrame();
	}
	
	public void change()
	{
		//TODO
	}
	
	public void reveal(int row, int col)
	{	
		if(!((TileIcon)buttons[row][col].getIcon()).isFlagged())
			buttons[row][col].getActionListeners()[0].actionPerformed(REVEAL); 
	}
	
	public void flag(int row, int col)
	{		
		if(!((TileIcon)buttons[row][col].getIcon()).isRevealed())
		{
			if(!((TileIcon)buttons[row][col].getIcon()).isFlagged())
				buttons[row][col].getActionListeners()[0].actionPerformed(FLAG); 
			else
				buttons[row][col].getActionListeners()[0].actionPerformed(UNFLAG); 
		}
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
	private BlockingQueue<int[]> messageQueue;
	
	
	/**
	 * Creates frame, creates the panel, and fills the panel with buttons
	 * Created to keep the constructor relatively clear
	 */
	private void initializeFrame()
	{
		frame = new JFrame("Minesweeper");
		panel = new JPanel();
		panel.setLayout(new GridLayout(rows, columns));
		buttons = new JButton[9][9];
		
		// Fills the panel with buttons
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				TileIcon tile = new TileIcon(false, false, i, j);
				JButton button = new JButton(tile);
							
				button.addActionListener(e -> {
					int row = ((TileIcon)button.getIcon()).getRow();
					int col = ((TileIcon)button.getIcon()).getCol();
										
					if(e == REVEAL)
						button.setIcon(new TileIcon(true, false,row, col));
					else if(e == FLAG)
						button.setIcon(new TileIcon(false, true,row, col));
					else if(e == UNFLAG)
						button.setIcon(new TileIcon(false, false,row, col));
					});
				
				// Upon being clicked, the button will send a message to messageQueue that contains the location of the button and the type of click
				button.addMouseListener( new MouseAdapter() 
				{
			        public void mousePressed(MouseEvent e)
					{
						int row = ((TileIcon) button.getIcon()).getRow(); // The row of the button that was clicked
						int col = ((TileIcon) button.getIcon()).getCol(); // The column of the button that was clicked
						
						if(SwingUtilities.isLeftMouseButton(e)) 
							messageQueue.add(new int[] {row, col, LEFT_CLICK});
						else if(SwingUtilities.isRightMouseButton(e)) 
							messageQueue.add(new int[] {row, col, RIGHT_CLICK});
					}	
				});

				button.setPreferredSize(new Dimension(tile.getIconWidth(), tile.getIconHeight()));	
				button.setBorder(new BevelBorder(BevelBorder.RAISED));
				buttons[i][j] = button;
				panel.add(i+ "" + j, button);
			}
		}
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(400,400);
		frame.pack();
		frame.setVisible(true);
	}
	
	private static JButton buttons[][];
	private static final ActionEvent REVEAL = new ActionEvent(new Object(), 0, "reveal");
	private static final ActionEvent FLAG = new ActionEvent(new Object(), 0, "flag");
	private static final ActionEvent UNFLAG = new ActionEvent(new Object(), 0, "unflag");

	
}
