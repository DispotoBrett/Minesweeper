package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.util.concurrent.BlockingQueue;
import java.awt.Color;
import java.awt.Dimension;

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
	 * @param adjMines
	 */
	public TileButton(int row, int col, BlockingQueue<int[]> messageQueue, JFrame frame, int adjMines)
	{
		super(new TileIcon(false, false));

		addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					messageQueue.add(new int[]{row, col, View.LEFT_CLICK});
				}
				else if (SwingUtilities.isRightMouseButton(e))
				{
					messageQueue.add(new int[]{row, col, View.RIGHT_CLICK});
				}
				setBackground(null);
			}

			public void mouseEntered(MouseEvent e)
			{
				setBackground(Color.yellow);
			}

			public void mouseExited(MouseEvent e)
			{
				setBackground(null);
			}

		});

		addActionListener(e -> {
			if (e == REVEAL)
			{
				setIcon(new TileIcon(true, false, adjMines));
			}
			else if (e == FLAG)
			{
				setIcon(new TileIcon(false, true));
			}
			else if (e == UNFLAG)
			{
				setIcon(new TileIcon(false, false));
			}
			else if (e == EXPLODE)
			{
				SwingUtilities.invokeLater(() -> {
					setBackground(Color.red);
					setIcon(new TileIcon(true, false, true));
					// TODO: Somehow feed the explosion the Board Panel's width and height, b/c when
					// menus are added, they will be painted over.
					Explosion explosion = new Explosion(getX(), getY(), frame.getContentPane().getWidth(),
							frame.getContentPane().getHeight());
					frame.setGlassPane(explosion);
					explosion.setVisible(true);
					Timer t = new Timer(10, e2 -> {
						explosion.explode();
						frame.repaint();
					});
					t.start();
				});
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
		getActionListeners()[0].actionPerformed(EXPLODE);
	}

	/**
	 * Reveals this tile.
	 *
	 * @param adjMines the number of mines adjacent to this mine
	 */
	public void reveal()
	{
		getActionListeners()[0].actionPerformed(REVEAL);
	}

	// ----------------Private Methods/Fields----------------------

	private static final ActionEvent REVEAL = new ActionEvent(new Object(), 0, "reveal");
	private static final ActionEvent FLAG = new ActionEvent(new Object(), 1, "flag");
	private static final ActionEvent UNFLAG = new ActionEvent(new Object(), 2, "unflag");
	private static final ActionEvent EXPLODE = new ActionEvent(new Object(), 3, "explode");

}
