package edu.sjsu.cs.cs151.minesweeper.model;

/**
 * The over-arching model class. Manages data, logic, and rules.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class Model
{
	//-------------------------Public Interface-----------------------
	public enum Difficulty{ EASY, MEDIUM, HARD };
	
	/**
	 * Constructs a new Model instance.
	 */
	public Model()
	{
		gameBoard = new Board(false);
		numberOfTiles = gameBoard.getRows() * gameBoard.getColumns();
		gameWon = false;
		gameLost = false;
	}
	
	public Model(Difficulty d)
	{
		switch(d)
		{
			case EASY: gameBoard = new Board(9, 9, 9); break;
			
			case MEDIUM: gameBoard = new Board(16, 16, 40); break;
			
			case HARD: gameBoard = new Board(24, 24, 99); break;
			
			default: gameBoard = new Board(false);
			
		}
		numberOfTiles = gameBoard.getRows() * gameBoard.getColumns();
		gameWon = false;
		gameLost = false;
	}

	/**
	 * Gets whether or not the game has been won.
	 *
	 * @return True if the game has been won, false otherwise.
	 */
	public boolean gameWon()
	{
		return gameWon;
	}

	/**
	 * Gets whether or not the game has been lost.
	 *
	 * @return True if the game has been lost, false otherwise.
	 */
	public boolean gameLost()
	{
		return gameLost;
	}

	/**
	 * Reveals the tile specified, and all surrounding tiles without mines, recursively, and updates game win/loss state.
	 *
	 * @param row The row of the tile specified.
	 * @param col The column of the tile specified.
	 */
	public void revealTile(int row, int col)
	{
		//lose condition (tile to be revealed is a mine)
		gameLost = gameBoard.isMine(row, col) && !gameBoard.getTileAt(row, col).isFlagged();

		if (!gameLost)
		{
			gameBoard.revealTile(row, col);
		}

		//win condition (all non-mine tiles have been revealed)
		if (gameBoard.getNumberTilesRevealed() == numberOfTiles - gameBoard.getNumMines() )
		{
			gameWon = true;
		}
	}

	/**
	 * Flags the tile specified.
	 *
	 * @param row The row of the tile specified.
	 * @param col The column of the tile specified.
	 */
	public void toggleFlag(int row, int col)
	{
		gameBoard.toggleFlag(row, col);
	}

	/**
	 * Accessor for the underlying game board
	 *
	 * @return the underlying game board
	 */
	public Board getBoard()
	{
		return gameBoard;
	}
	
	/**
	 * Constructs an iterator over the board's tiles.
	 *
	 * @return An iterator over the board's tiles.
	 */
	public BoardIterator boardIterator()
	{
	    return gameBoard.iterator();
	}

	//-------------------------Private Fields/Methods------------------
	private Board gameBoard;
	private int numberOfTiles;
	private boolean gameWon;
	private boolean gameLost;
}