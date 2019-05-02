package edu.sjsu.cs.cs151.minesweeper.view;

import edu.sjsu.cs.cs151.minesweeper.controller.DifficultyMessage;
import edu.sjsu.cs.cs151.minesweeper.controller.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * WelcomePanel represents a startup screen to welcome the player.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */

public class WelcomePanel extends JPanel
{
	/**
	 * Constructor for WelcomePanel.
	 *
	 * @param messageQueue A queue to hold messages (user inputs).
	 */
	public WelcomePanel(BlockingQueue<Message> messageQueue)
	{
		setLayout(new BorderLayout());
		setUpNorth();
		setUpCenter(messageQueue);
		setUpSouth();
		setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(400, 200);
	}

	public void animate()
	{
		animation.x++;
		if (animation.x > 400)
		{
			animation.x = -50;
		}
	}

	//-------------------------Private Fields/Methods------------------
	private MineAnimation animation;

	private void setUpNorth()
	{
		JLabel logo;
		//avoid trying to create an ImageIcon with a null parameter
		try
		{
			BufferedImage img = ImageIO.read(new File("resources/logo.png"));
			logo = new JLabel(new ImageIcon(img));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logo = new JLabel(new ImageIcon());
		}

		add(logo, BorderLayout.NORTH);
	}

	private void setUpCenter(BlockingQueue<Message> messageQueue)
	{
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new GridLayout(2, 3, 10, 10));

		//using empty JLabels to fill grid cells (any alternative?)
		difficultyPanel.add(new JLabel());

		JLabel difficultyLabel = new JLabel("Select a difficulty:");
		difficultyPanel.add(difficultyLabel);

		difficultyPanel.add(new JLabel());

		//FIXME unchecked calls to add as a member of a raw type (Queue)
		JButton easyButton = new JButton("Easy");
		easyButton.setBorder(BorderFactory.createLineBorder(Color.black));
		easyButton.setBackground(Color.YELLOW);
		easyButton.setOpaque(true);
		easyButton.addActionListener(e -> {
			try
			{
				messageQueue.put(new DifficultyMessage(View.Difficulty.EASY, true));
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		});
		difficultyPanel.add(easyButton);

		JButton mediumButton = new JButton("Medium");
		mediumButton.addActionListener(e -> {
			try
			{
				messageQueue.put(new DifficultyMessage(View.Difficulty.MEDIUM, true));
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		});
		mediumButton.setBorder(BorderFactory.createLineBorder(Color.black));
		mediumButton.setBackground(Color.cyan);
		mediumButton.setOpaque(true);
		difficultyPanel.add(mediumButton);

		JButton hardButton = new JButton("Hard");
		hardButton.addActionListener(e -> {
			try
			{
				messageQueue.put(new DifficultyMessage(View.Difficulty.HARD, true));
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		});
		hardButton.setBorder(BorderFactory.createLineBorder(Color.black));
		hardButton.setBackground(Color.RED);
		hardButton.setOpaque(true);
		difficultyPanel.add(hardButton);

		add(difficultyPanel, BorderLayout.CENTER);
		difficultyPanel.setBackground(Color.LIGHT_GRAY);
	}

	private void setUpSouth()
	{
		BufferedImage mineIcon = null;

		try
		{
			mineIcon = ImageIO.read(new File("resources/mine.png"));
		}
		catch (IOException e)
		{
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
			g2.drawImage(mineIcon, x, 0, 50, 50, null, null);
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