package edu.sjsu.cs.cs151.minesweeper.app;

/**
 * Tile represents a MineSweeper square, which may or may not contain a mine.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
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
	 * Accessor to determine is this tile has an underlying mine.
	 * @return isMine denotes if this tile is a mine.
	 */
	public boolean isMine()
	{
		return isMine;
	}
	
	/**
	 * Accessor to determine is this tile has been revealed.
	 * @return isRevealed denotes if this tile has been revealed.
	 */
	public boolean isRevealed()
	{
		return isRevealed;
	}
	
	/**
	 * Accessor to determine is this tile has been flagged.
	 * @return isFlagged denotes if this tile has been flagged.
	 */
	public boolean isFlagged()
	{
		return isFlagged;
	}
	
	/*
	 * Reveals this tile, this action cannot be undone.
	 * @precondition a tile cannot be revealed if it is currently flagged.
	 * @return boolean indicating if the tile could be revealed successfully.
	 */
	public boolean reveal()
	{
		if(isFlagged)
			return false;
		
		isRevealed = true;
		return true;
	}
	
	/**
	 * Adds or removed a flag from this tile.
	 * @precondition this tile must be unrevealed in order to be flagged/unflagged.
	 */
	public void toggleFlag()
	{
		if(isRevealed) 
			return;
		
		isFlagged = !isFlagged;
	}
	//-------------------------Private Fields/ Methods------------------
	
	private final boolean isMine;
	private boolean isRevealed;
	private boolean isFlagged;
}
