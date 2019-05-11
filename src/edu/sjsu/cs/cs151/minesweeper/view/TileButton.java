package edu.sjsu.cs.cs151.minesweeper.view;

import edu.sjsu.cs.cs151.minesweeper.message.LeftClickMessage;
import edu.sjsu.cs.cs151.minesweeper.message.Message;
import edu.sjsu.cs.cs151.minesweeper.message.RightClickMessage;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.BlockingQueue;

/**
 * Visual representation of a tile.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class TileButton extends JButton
{
	// ----------------Public Interface-------------------------

	/**
	 * Constructs a new TileButton.
	 *
	 * @param row          The row of the button.
	 * @param col          The column of the button.
	 * @param messageQueue The message queue to collect user input.
	 * @param frame        The frame to be painted on (for explosions).
	 * @param adjMines     The number of mines adjacent to the Tile that this TileButton represents.
	 */
	public TileButton(int row, int col, BlockingQueue<Message> messageQueue, JFrame frame, int adjMines)
	{
		super(UNREVEALED_ICON);
		revealed = false;
		theFrame = frame;
		exploded = false;

		//Listen to when a TileButton is pressed
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
							if (!exploded)
							{
								messageQueue.put(new LeftClickMessage(row, col));
							}
						}
						catch (InterruptedException e1)
						{
							e1.printStackTrace();
						}
					}
					else if (SwingUtilities.isRightMouseButton(e))
					{
						try
						{
							if (!exploded)
							{
								messageQueue.put(new RightClickMessage(row, col));
							}
						}
						catch (InterruptedException e1)
						{
							e1.printStackTrace();
						}
					}
					if (getBackground() == Color.DARK_GRAY)
					{
						setBackground(null);
					}
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
					setIcon(new TileIcon(TileIcon.TileState.revealed, adjMines));
				}
				revealed = true;
			}
			else if (e == FLAG)
			{
				setIcon(FLAGGED_ICON);
			}
			else if (e == UNFLAG)
			{
				if (!exploded)
				{
					setIcon(UNREVEALED_ICON);
				}
			}
			else if (e == EXPOSE_MINE)
			{
				setIcon(SHOW_MINE_ICON);
			}
			else if (e == EXPLODE)
			{
				exploded = true;

				setBackground(Color.red);
				setIcon(SHOW_MINE_ICON);

				explosion = new Explosion(getX(), getY(), frame.getContentPane().getWidth(),
						frame.getContentPane().getHeight());
				frame.setGlassPane(explosion);
				explosion.setVisible(true);

				t = new Timer(12, e2 -> {

					Rectangle bound = explosion.repaintArea();
					frame.repaint((int) Math.ceil(bound.getX()), (int) Math.ceil(bound.getY()),
							(int) Math.ceil(bound.getWidth()), (int) Math.ceil(bound.getHeight()));

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
	 * Flags or unflags this TileButton.
	 *
	 * @param flag True if this TileButton should be flagged, false otherwise.
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
	 * Explodes this TileButton.
	 */
	public void explode()
	{
		if (!exploded)
		{
			getActionListeners()[0].actionPerformed(EXPLODE);
		}
	}

	/**
	 * Reveals this TileButton.
	 */
	public void reveal()
	{
		getActionListeners()[0].actionPerformed(REVEAL);
	}

	/**
	 * Exposes a mine icon on top of this TileButton.
	 */
	public void exposeMine()
	{
		getActionListeners()[0].actionPerformed(EXPOSE_MINE);
	}

	// ----------------Private Methods/Fields----------------------

	private static final ActionEvent REVEAL = new ActionEvent("reveal", 1, "reveal");
	private static final ActionEvent FLAG = new ActionEvent("flag", 2, "flag");
	private static final ActionEvent UNFLAG = new ActionEvent("unflag", 3, "unflag");
	private static final ActionEvent EXPLODE = new ActionEvent("explode", 4, "explode");
	private static final ActionEvent EXPOSE_MINE = new ActionEvent("expose", 5, "expose");

	private static final TileIcon UNREVEALED_ICON = new TileIcon(TileIcon.TileState.unrevealed);
	private static final TileIcon FLAGGED_ICON = new TileIcon(TileIcon.TileState.flagged);
	private static final TileIcon SHOW_MINE_ICON = new TileIcon(TileIcon.TileState.mineShowing);

	public boolean revealed;
	private static boolean exploded;

	private static JFrame theFrame;

	private Explosion explosion;

	static Timer t;

	private void stopTimer()
	{
		t.stop();
		theFrame.getGlassPane().setEnabled(false);
		theFrame.repaint();
	}
}