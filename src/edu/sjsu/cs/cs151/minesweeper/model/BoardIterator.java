package edu.sjsu.cs.cs151.minesweeper.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterates over the entire gameBoard in order left -> right, top -> down.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class BoardIterator implements Iterator<Tile>
{
    /**
     * Constructs a new board iterator.
     * @param board the board to be iterated over
     */
    BoardIterator(Board board)
    {
    	row = 0;
    	col = INIT_COLLUMN_INDEX;
    	boardInstance = board;
    }
    
    /**
     * Tells if the iterator has the next tile.
     * @return if the iterator has the next tile
     */
    public boolean hasNext()
    {
        return (row < boardInstance.getRows() - 1 || col < boardInstance.getColumns() - 1);
    }
    
    /**
     * Advances the cursor and returns element passed over.
     * @return the tile passed over
     */
    public Tile next()
    {
    	try
    	{
    	    if(++col == boardInstance.getColumns()) 
    	    {
    		col = 0;
    		row++;
    	    }
    	    return boardInstance.getTileAt(row,col);
    	}
    	catch(ArrayIndexOutOfBoundsException e)
    	{
    	    throw (new NoSuchElementException());
    	}
    }
    
    /**
     * gets the row of the previously retrieved tile.
     * @return the row of the previously retrieved tile
     */
    public int prevRow()
    {
        return row;
    }
    
    /**
     * gets the column of the previously retrieved tile.
     * @return the column of the previously retrieved tile
     */
    public int prevCol()
    {
        return col;
    }
    
    private int row;
    private int col;
    private int INIT_COLLUMN_INDEX = -1;
    private Board boardInstance;
}