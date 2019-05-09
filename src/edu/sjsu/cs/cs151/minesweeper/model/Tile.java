package edu.sjsu.cs.cs151.minesweeper.model;

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
	 *
	 * @param isMine denotes whether a tile has an underlying mine.
	 */
	public Tile(boolean isMine)
	{
		this.isMine = isMine;
		isRevealed = false;
		isFlagged = false;
	}

	/**
	 * Accessor to determine if this tile has an underlying mine.
	 *
	 * @return True if this tile is a mine, false otherwise.
	 */
	public boolean isMine()
	{
		return isMine;
	}

	/**
	 * Accessor to determine if this tile has been revealed.
	 *
	 * @return True if this tile is revealed, false otherwise.
	 */
	public boolean isRevealed()
	{
		return isRevealed;
	}

	/**
	 * Accessor to determine is this tile has been flagged.
	 *
	 * @return True if this tile is flagged, false otherwise.
	 */
	public boolean isFlagged()
	{
		return isFlagged;
	}

	/**
	 * Reveals this tile, this action cannot be undone.
	 *
	 * @return True if the tile was revealed successfully, false otherwise.
	 */

	public boolean reveal()
	{
		if (isFlagged)
		{
			return false;
		}

		isRevealed = true;
		return true;
	}

	/**
	 * Flags or unflags a tile based on its current state.
	 */
	public void toggleFlag()
	{
		if (isRevealed)
		{
			return;
		}

		isFlagged = !isFlagged;
	}
	//-------------------------Private Fields/Methods------------------

	private final boolean isMine;
	private boolean isRevealed;
	private boolean isFlagged;
}