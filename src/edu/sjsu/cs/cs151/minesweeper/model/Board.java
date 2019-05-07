package edu.sjsu.cs.cs151.minesweeper.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents the MineSweeper board, is responsible tile management.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public final class Board implements Iterable<Tile>
{
	//-------------------------Public Interface-----------------------

	/**
	 * Constructs a Board with or without randomized mine placement.
	 *
	 * @param numberOfRows    The number of rows in the Board.
	 * @param numberOfColumns The number of columns in the Board.
	 * @param numberOfMines   The number of mines in the Board.
	 * @param usePresetSeed   True if mine placement is to be predetermined, false otherwise.
	 */
	public Board(int numberOfRows, int numberOfColumns, int numberOfMines, boolean usePresetSeed)
	{
		NUM_ROWS = numberOfRows;
		NUM_COLS = numberOfColumns;
		NUM_MINES = numberOfMines;

		tiles = new Tile[NUM_ROWS][NUM_COLS];
		initializeTiles(usePresetSeed);
		initializeAdjacentMines();
		numberTilesRevealed = 0;
	}

	/**
	 * Constructs an iterator over the board's tiles.
	 *
	 * @return An iterator over the board's tiles.
	 */
	public BoardIterator iterator()
	{
		return new BoardIterator(this);
	}

	/**
	 * Reveals the specified tile, and all surrounding tiles without mines, recursively.
	 *
	 * @param row The row of the specified tile.
	 * @param col The column of the specified tile.
	 */
	public void revealTile(int row, int col)
	{
		Tile currentTile = tiles[row][col];

		if (currentTile.isMine() || currentTile.isFlagged() || currentTile
			  .isRevealed()) // Tile can neither be a mine, flagged, or revealed
		{
			return;
		}
		else if (adjacentMines(row, col) != 0) // Reveals a single tile if there is an adjacent mine
		{
			currentTile.reveal();
			numberTilesRevealed++;
		}
		else // Recursively reveals adjacent tiles if the current has no adjacent mine
		{
			currentTile.reveal();
			numberTilesRevealed++;

			//Begin with the row directly above the clicked tile and move down
			for (int i = row - 1; i <= row + 1; i++)
			{
				//Begin with the column directly left of the clicked tile and move right
				for (int j = col - 1; j <= col + 1; j++)
				{
					if (i >= 0 && j >= 0 && i < NUM_ROWS && j < NUM_COLS) //Only true if i and j are valid indices
					{
						revealTile(i, j);
					}
				}
			}
		}
	}

	/**
	 * Flags the specified tile.
	 *
	 * @param row The row of the specified tile.
	 * @param col The column of the specified tile.
	 */
	public void toggleFlag(int row, int col)
	{
		tiles[row][col].toggleFlag();
	}

	/**
	 * Gets the number of mines adjacent to the specified tile.
	 *
	 * @param row The row of the specified tile.
	 * @param col The column of the specified tile.
	 */
	public int adjacentMines(int row, int col)
	{
		return adjMines[row][col];
	}

	/**
	 * Gets the number of tiles revealed on the Board.
	 *
	 * @return The number of tiles revealed.
	 */
	public int getNumberTilesRevealed()
	{
		return numberTilesRevealed;
	}

	/**
	 * Gets the Tile at the specified location.
	 *
	 * @return Tile at the specified location.
	 */
	public Tile getTileAt(int row, int col)
	{
		return tiles[row][col];
	}

	/**
	 * Determines if the Tile at the specified location is a mine.
	 *
	 * @return True if the Tile is a mine, false otherwise.
	 */
	public Boolean isMine(int row, int col)
	{
		return tiles[row][col].isMine();
	}

	/**
	 * Gets the "table" indicating adjacent mines.
	 *
	 * @return The 2D array "table" of indicating adjacent mines.
	 */
	public int[][] adjacentMines()
	{
		return adjMines;
	}

	/**
	 * Gets the number of rows in the Board.
	 *
	 * @return The number of rows in the Board.
	 */
	public int getRows()
	{
		return NUM_ROWS;
	}

	/**
	 * Gets the number of columns in the Board.
	 *
	 * @return The number of columns in the Board.
	 */
	public int getColumns()
	{
		return NUM_COLS;
	}

	/**
	 * Gets the number of mines in the Board.
	 *
	 * @return The number of mines in the Board
	 */
	public int getNumMines()
	{
		return NUM_MINES;
	}

	//-------------------------Private Fields/Methods------------------
	private final int NUM_ROWS;
	private final int NUM_COLS;
	private final int NUM_MINES;
	private final int DEFAULT_SEED = 0;

	private Tile[][] tiles;

	// Each index in adjMines stores a value that indicates how many mines are adjacent to the same index in tiles
	private int[][] adjMines;

	private int numberTilesRevealed;

	/**
	 * Initializes all tiles, randomly choosing which have underlying mines.
	 *
	 * @param usePresetSeed True if mine placement is to be predetermined, false otherwise
	 */
	private void initializeTiles(boolean usePresetSeed)
	{
		ArrayList<Integer> mines = new ArrayList<>();

		for (int i = 0; i < NUM_ROWS * NUM_COLS; i++)
		{
			mines.add(i);
		}

		if (usePresetSeed)
		{
			//Generates a Board with identical mine placement every time the game is played.
			Collections.shuffle(mines, new Random(DEFAULT_SEED));
		}
		else
		{
			Collections.shuffle(mines, new Random());
		}

		mines = new ArrayList<>(mines.subList(0, NUM_MINES));

		int tileCounter = 0;
		for (int i = 0; i < NUM_ROWS; i++)
		{
			for (int j = 0; j < NUM_COLS; j++)
			{
				if (mines.contains(tileCounter))
				{
					tiles[i][j] = new Tile(true);
				}
				else
				{
					tiles[i][j] = new Tile(false);
				}

				tileCounter++;
			}
		}
	}

	/**
	 * Initializes the 2d array that stores the number of adjacent mines for each index in tiles
	 */
	private void initializeAdjacentMines()
	{
		adjMines = new int[NUM_ROWS][NUM_COLS];

		for (int row = 0; row < NUM_ROWS; row++)
		{
			for (int col = 0; col < NUM_COLS; col++)
			{
				int mines = 0;

				for (int i = row - 1; i <= row + 1; i++) //Begin with the row directly above the clicked tile
				{
					for (int j = col - 1; j <= col + 1; j++) //Begin from the column directly left of the clicked tile
					{
						if (i >= 0 && j >= 0 && i < NUM_ROWS && j < NUM_COLS) //Only true if i and j are valid indices
						{
							if (tiles[i][j].isMine())
							{
								mines++;
							}
						}
					}
				}

				adjMines[row][col] = mines;
			}
		}
	}
}