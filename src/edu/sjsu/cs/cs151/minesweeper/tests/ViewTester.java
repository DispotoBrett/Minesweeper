package edu.sjsu.cs.cs151.minesweeper.tests;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import edu.sjsu.cs.cs151.minesweeper.model.Board;
import edu.sjsu.cs.cs151.minesweeper.model.BoardIterator;
import edu.sjsu.cs.cs151.minesweeper.model.Model;
import edu.sjsu.cs.cs151.minesweeper.model.Tile;
import edu.sjsu.cs.cs151.minesweeper.view.View;

public class ViewTester
{
    static Model model = new Model();
    static View view;
    static final int ROW_INDEX = 0;
    static final int COL_INDEX = 1;
    static int[] msg;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException
    {
	SwingUtilities
		.invokeAndWait(() -> view = new View(Board.NUM_ROWS, Board.NUM_COLS, model.getBoard().adjacentMines()));

	while (true)
	{
	    if (!view.getQueue().isEmpty())
	    {
		msg = view.getQueue().remove();
		if (msg[2] == View.LEFT_CLICK)
		{
		    model.revealTile(msg[ROW_INDEX], msg[COL_INDEX]);
		}
		else if (msg[2] == View.RIGHT_CLICK)
		{
		    model.toggleFlag(msg[ROW_INDEX], msg[COL_INDEX]);
		}
		sync();
	    }
	}
    }

    private static void sync()
    {
	if (model.gameLost()) model.toggleFlag(msg[ROW_INDEX], msg[COL_INDEX]);
	
	try
	{
	    BoardIterator iter = model.boardIterator();
	    Tile t;
	    while (iter.hasNext())
	    {
		t = iter.next();
		final boolean isFlagged = t.isFlagged();
		
		if (t.isRevealed())
		    SwingUtilities.invokeAndWait(() -> view.reveal(iter.prevRow(), iter.prevCol()));
		else
		    SwingUtilities.invokeAndWait(() -> view.flag(iter.prevRow(), iter.prevCol(), isFlagged));
	    }

	}
	catch (Exception e)
	{
	}
    }
}