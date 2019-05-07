package edu.sjsu.cs.cs151.minesweeper.model;

/**
 * The over-arching model class. Manages data, logic, and rules.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
@SuppressWarnings("FieldCanBeLocal")
public class Model
{
	//-------------------------Public Interface-----------------------
	public enum Difficulty
	{EASY, MEDIUM, HARD}

	/**
	 * @param usePresetSeed True if mine placement is to be predetermined, false otherwise.
	 */
	public Model(Boolean usePresetSeed)
	{
		this.usePresetSeed = usePresetSeed;
		gameBoard = new Board(EASY_SIZE, EASY_SIZE, EASY_MINES, usePresetSeed);
		numberOfTiles = gameBoard.getRows() * gameBoard.getColumns();
		gameWon = false;
		gameLost = false;
	}

	/**
	 * Sets the difficulty level of the Model.
	 *
	 * @param d The difficulty level to set Model to.
	 */
	public void setDifficultyAndReset(Difficulty d)
	{
		currentDifficulty = d;

		switch (d)
		{
		case EASY:
			gameBoard = new Board(EASY_SIZE, EASY_SIZE, EASY_MINES, usePresetSeed);
			break;

		case MEDIUM:
			gameBoard = new Board(MEDIUM_SIZE, MEDIUM_SIZE, MEDIUM_MINES, usePresetSeed);
			break;

		case HARD:
			gameBoard = new Board(HARD_SIZE, HARD_SIZE, HARD_MINES, usePresetSeed);
			break;

		default:
			gameBoard = new Board(EASY_SIZE, EASY_SIZE, EASY_MINES, usePresetSeed);
			currentDifficulty = Difficulty.EASY;
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
	 * Resets the Model
	 */
	public void reset()
	{
		gameWon = false;
		gameLost = false;

		int rows = gameBoard.getRows();
		int cols = gameBoard.getColumns();
		int mines = gameBoard.getNumMines();

		gameBoard = new Board(rows, cols, mines, usePresetSeed);
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
		if (gameBoard.getNumberTilesRevealed() == numberOfTiles - gameBoard.getNumMines())
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
	private int EASY_SIZE = 9;
	private int MEDIUM_SIZE = 11;
	private int HARD_SIZE = 14;
	private int EASY_MINES = 8;
	private int MEDIUM_MINES = 14;
	private int HARD_MINES = 34;
	private Board gameBoard;
	private boolean usePresetSeed;
	private int numberOfTiles;
	private boolean gameWon;
	private boolean gameLost;
	private Difficulty currentDifficulty;
}