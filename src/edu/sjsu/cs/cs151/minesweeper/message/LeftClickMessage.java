package edu.sjsu.cs.cs151.minesweeper.message;

/**
 * A Message that indicates that the user has left-clicked a tile
 */
public class LeftClickMessage implements Message
{
	/**
	 * Creates the Message and stores the row and column of the tile that was clicked
	 * @param row the row of the tile that was clicked
	 * @param col the column of the tile that was clicked
	 */
	public LeftClickMessage(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Gets the row of the tile that was clicked
	 * @return the row of the tile that was clicked
	 */
	public int getRow()
	{
		return row;
	}
	
	/**
	 * Gets the column of the tile that was clicked
	 * @return the column of the tile that was clicked
	 */
	public int getColumn()
	{
		return col;
	}

	private int row;
	private int col;
}