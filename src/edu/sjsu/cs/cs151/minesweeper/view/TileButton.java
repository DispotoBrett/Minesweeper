package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import edu.sjsu.cs.cs151.minesweeper.controller.*;

import java.util.concurrent.BlockingQueue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * Visual Representation of a tile.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class TileButton extends JButton
{
	// ----------------Public Interface-------------------------

	/**
	 * Constructs a new TileButton
	 *
	 * @param row          the row of the button
	 * @param col          the column of the button
	 * @param messageQueue the message Queue (user input)
	 * @param frame        the frame to be painted on (for explosions)
	 * @param adjMines     the number of mines adjacent to the Tile represented by this TileButton
	 */
	public TileButton(int row, int col, BlockingQueue<Message> messageQueue, JFrame frame, int adjMines)
	{
		super(new TileIcon(false, false));
		revealed = false;
		theFrame = frame;
		exploded = false;


		addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e)
			{
				if (!exploded)
				{
					if (SwingUtilities.isLeftMouseButton(e))
					{
						try
						{
							messageQueue.put(new LeftClickMessage(row, col));
						} catch (InterruptedException e1)
						{
							e1.printStackTrace();
						}
					}
					else if (SwingUtilities.isRightMouseButton(e))
					{
						try
						{
							messageQueue.put(new RightClickMessage(row, col));
						} catch (InterruptedException e1)
						{
							e1.printStackTrace();
						}
					}
					setBackground(null);
				}
			}

			public void mousePressed(MouseEvent e)
			{
				if (!exploded && !revealed && SwingUtilities.isLeftMouseButton(e))
				{
					setBackground(Color.DARK_GRAY);
				}

			}
		});

		addActionListener(e -> {
			if (e == REVEAL)
			{
				if (!revealed)
				{
					setIcon(new TileIcon(true, false, adjMines));
				}
				revealed = true;
			}
			else if (e == FLAG)
			{
				setIcon(new TileIcon(false, true));
			}
			else if (e == UNFLAG)
			{
				if (!exploded)
				{
					setIcon(new TileIcon(false, false));
				}
			}
			else if (e == EXPOSE_MINE)
			{
				setIcon(new TileIcon(false, false, true));
			}
			else if (e == EXPLODE)
			{
				exploded = true;

				setBackground(Color.red);
				setIcon(new TileIcon(true, false, true));
				// TODO: Somehow feed the explosion the Board Panel's width and height, b/c when
				// menus are added, they will be painted over.
				explosion = new Explosion(getX(), getY(), frame.getContentPane().getWidth(),
						frame.getContentPane().getHeight());
				frame.setGlassPane(explosion);
				explosion.setVisible(true);

				t = new Timer(12, e2 -> {

					Rectangle bound = explosion.repaintArea();
					frame.repaint((int) Math.ceil(bound.getX()),
							(int) Math.ceil(bound.getY()),
							(int) Math.ceil(bound.getWidth()),
							(int) Math.ceil(bound.getHeight()));

					explosion.explode();

					if (explosion.isDone())
					{
						stopTimer();
					}
				});
				t.start();
			}
		});

		setPreferredSize(new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight()));
		setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	/**
	 * Sets or removes flag from this tile.
	 *
	 * @param flag whether or not the tile should be flagged.
	 */
	public void flag(boolean flag)
	{
		if (flag)
		{
			getActionListeners()[0].actionPerformed(FLAG);
		}
		else
		{
			getActionListeners()[0].actionPerformed(UNFLAG);
		}
	}

	/**
	 * Explodes this tile.
	 */
	public void explode()
	{
		if(!exploded)
			getActionListeners()[0].actionPerformed(EXPLODE);
	}

	/**
	 * Reveals this tile.
	 */
	public void reveal()
	{
		getActionListeners()[0].actionPerformed(REVEAL);
	}

	/**
	 * Places a mine icon on top of this tile
	 */
	public void exposeMine()
	{
		getActionListeners()[0].actionPerformed(EXPOSE_MINE);
	}

	// ----------------Private Methods/Fields----------------------

	private static final ActionEvent REVEAL = new ActionEvent(new Object(), 0, "reveal");
	private static final ActionEvent FLAG = new ActionEvent(new Object(), 1, "flag");
	private static final ActionEvent UNFLAG = new ActionEvent(new Object(), 2, "unflag");
	private static final ActionEvent EXPLODE = new ActionEvent(new Object(), 3, "explode");
	private static final ActionEvent EXPOSE_MINE = new ActionEvent(new Object(), 4, "explode");

	public boolean revealed;
	private static boolean exploded;
	private static JFrame theFrame;
	private Explosion explosion;
	static Timer t;

	private void stopTimer()
	{
		t.stop();
		theFrame.getGlassPane().setVisible(false);
		theFrame.repaint();
	}


}