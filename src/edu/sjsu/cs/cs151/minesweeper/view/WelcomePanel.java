package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class WelcomePanel extends JPanel{
	
	public WelcomePanel(Queue messageQueue)
	{
		setLayout(new BorderLayout());
		setUpNorth();
		setUpCenter(messageQueue);
		setUpEast(messageQueue);
		setUpSouth();
	}	
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(400, 200);
	}
	
	public void animate()
	{
		animation.x++;
		if(animation.x > 400)
			animation.x = -50;
	}
	private MineAnimation animation;
	
	private void setUpNorth()
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources/logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel logo = new JLabel(new ImageIcon(img));
		add(logo, BorderLayout.NORTH);		
	}
	
	private void setUpCenter(Queue messageQueue)
	{
		JLabel start = new JLabel();
		start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));
		start.add(new JLabel("Select a difficulty"));
		
		ButtonGroup difficulties = new ButtonGroup();
		JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
		easy.addActionListener(e -> messageQueue.add(new int[]{-1, -1, View.EASY_DIFFICULTY}));
		easy.setSelected(true);
		difficulties.add(easy);
		start.add(easy);

		JRadioButtonMenuItem medium = new JRadioButtonMenuItem("Medium");
		medium.addActionListener(e -> messageQueue.add(new int[]{-1, -1, View.MEDIUM_DIFFICULTY}));
		difficulties.add(medium);
		start.add(medium);

		JRadioButtonMenuItem hard = new JRadioButtonMenuItem("Hard");
		hard.addActionListener(e -> messageQueue.add(new int[]{-1, -1, View.HARD_DIFFICULTY}));
		difficulties.add(hard);
		start.add(hard);

		
		add(start, BorderLayout.CENTER);
	}
	
	private void setUpEast(Queue messageQueue) 
	{
		JButton startButton = new JButton("Start");
		startButton.addActionListener(e -> messageQueue.add(new int[]{-1, -1, -1})); //define new message item
		add(startButton, BorderLayout.EAST);
	}
	
	private void setUpSouth()
	{
		BufferedImage mineIcon = null;
		
		try {
			mineIcon = ImageIO.read(new File("resources/mine.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		animation = new MineAnimation(mineIcon);
		add(animation, BorderLayout.SOUTH);
	}

	private class MineAnimation extends JPanel
	{
		@Override
		public void paint(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(mineIcon, x, 0, 50,50, null, null);
		}
		
		@Override
		public Dimension getPreferredSize()
		{
			return new Dimension(400, 50);
		}

		private int x;
		private BufferedImage mineIcon;
		private static final long serialVersionUID = 1L;

		private MineAnimation(BufferedImage mineIcon)
		{
			 x = -50;
			this.mineIcon = mineIcon;
		}	
	}
}
