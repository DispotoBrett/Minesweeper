package edu.sjsu.cs.cs151.minesweeper.app;

/**
 * Tile represents a Minesweeper square, which may or may not contain a mine.
 *
 * @author Jordan Conragan
 * @author Brett Dispoto
 * @author Patrick Silvestre
 */
public final class Tile 
{
	//-------------------------Public Interface-----------------------
	
	/**
	 * Constructs a new Tile object.
	 * @param isMine denotes whether a tile has an underlying mine.
	 */
	public Tile(boolean isMine)
	{
		this.isMine = isMine;
		isRevealed = false;
		isFlagged = false;
	}
	
	/**
	 * Accessor to determine is a tile has an underlying mine.
	 * @return isMine denotes if this tile is a mine.
	 */
	public boolean isMine()
	{
		return isMine;
	}
	
	/**
	 * Accessor to determine is a tile has been revealed.
	 * @return isMine denotes if this tile has been revealed.
	 */
	public boolean isRevealed()
	{
		return isRevealed;
	}
	
	/**
	 * Accessor to determine is the tile has been flagged.
	 * @return isMine denotes if this tile has been flagged.
	 */
	public boolean isFlagged()
	{
		return isFlagged;
	}
	//-------------------------Private Fields/ Methods------------------
	
	private final boolean isMine;
	private boolean isRevealed;
	private boolean isFlagged;
}
