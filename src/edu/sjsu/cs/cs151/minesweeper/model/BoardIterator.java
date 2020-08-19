package edu.sjsu.cs.cs151.minesweeper.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterates over a Board in order left -> right, top -> down.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class BoardIterator implements Iterator<Tile>
{
	/**
	 * Constructs a new Board iterator.
	 *
	 * @param board The board to be iterated over.
	 */
	BoardIterator(Board board)
	{
		row = 0;
		col = INIT_COLUMN_INDEX;
		boardInstance = board;
	}

	/**
	 * Tells if the iterator has the next tile.
	 *
	 * @return True if there is a next tile, false otherwise.
	 */
	public boolean hasNext()
	{
		boolean hasNext = false;
		
		int i = row;
		int j = col;
		
		while(!hasNext && (i < boardInstance.getRows() -1 || j < boardInstance.getColumns() -1))
		{
			if (++j == boardInstance.getColumns())
			{
				j = 0;
				i++;
			}
			
			hasNext = boardInstance.tileExistsAt(i, j);
		}

		return hasNext;
	}

	/**
	 * Advances the cursor and returns element passed over.
	 *
	 * @return The tile passed over.
	 */
	public Tile next()
	{
		try
		{
			Tile nextTile;

			do 
			{
				if (++col == boardInstance.getColumns())
				{
					col = 0;
					row++;
				}
				
				nextTile = boardInstance.getTileAt(row, col);
				
			}
			while(nextTile == null);
			
			return nextTile;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw (new NoSuchElementException());
		}
	}

	/**
	 * Gets the row of the previously retrieved tile.
	 *
	 * @return The row of the previously retrieved tile
	 */
	public int prevRow()
	{
		return row;
	}

	/**
	 * Gets the column of the previously retrieved tile.
	 *
	 * @return The column of the previously retrieved tile.
	 */
	public int prevCol()
	{
		return col;
	}

	private int row;
	private int col;
	private Board boardInstance;
	private int INIT_COLUMN_INDEX = -1;
}