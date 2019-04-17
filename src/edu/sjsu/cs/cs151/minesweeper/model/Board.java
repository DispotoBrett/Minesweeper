package edu.sjsu.cs.cs151.minesweeper.model;

import java.util.*;

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

	public final static int NUM_ROWS = 9;
	public final static int NUM_COLS = 9;
	public final static int NUM_MINES = 9;

	/**
	 * Constructs a new Board instance, with randomized mine placement.
	 *
	 * @param usePresetSeed determines whether mine placement is predetermined or randomized
	 */
	public Board(boolean usePresetSeed)
	{
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
	 * Reveals the tile specified, and all surrounding tiles without mines, recursively.
	 *
	 * @param row the row of the tile specified
	 * @param col the column of the tile specified
	 */
	public void revealTile(int row, int col)
	{
		Tile currentTile = tiles[row][col];

		if (currentTile.isMine() || currentTile.isFlagged() || currentTile.isRevealed()) // Tile can neither be a mine, flagged, or revealed
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

			for (int i = row - 1; i <= row + 1; i++) //Begins with the row directly above the clicked tile and moves down
			{
				for (int j = col - 1; j <= col + 1; j++) //Begins from the column directly left of the clicked tile and moves right
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
	 * Flags the tile specified.
	 *
	 * @param row the row of the tile specified
	 * @param col the column of the tile specified
	 */
	public void toggleFlag(int row, int col)
	{
		tiles[row][col].toggleFlag();
	}

	/**
	 * Gets the number of mines adjacent to the tile specified.
	 *
	 * @param row the row of the tile specified
	 * @param col the column of the tile specified
	 */
	public int adjacentMines(int row, int col)
	{
		return adjMines[row][col];
	}

	/**
	 * Gets the number of tiles revealed on the Board.
	 *
	 * @return The number of tiles revealed
	 */
	public int getNumberTilesRevealed()
	{
		return numberTilesRevealed;
	}

	/**
	 * gets the tile at the specified location.
	 *
	 * @return Tile at the specified location
	 */
	public Tile getTileAt(int row, int col)
	{
		return tiles[row][col];
	}

	/**
	 * Determines if the tile at the specified location is a mine
	 *
	 * @return Whether the tile at (row, col) is a mine
	 */
	public Boolean isMine(int row, int col)
	{
		return tiles[row][col].isMine();
	}

	/**
	 * Gets the table of indicating adjacent mines.
	 *
	 * @return table of indicating adjacent mines
	 */
	public int[][] adjacentMines()
	{
		return adjMines;
	}

	//-------------------------Private Fields/Methods------------------
	private Tile[][] tiles;
	private int[][] adjMines; // Each index in adjMines stores a value that indicates how many mines are adjacent to the same index in tiles
	private int numberTilesRevealed;

	/**
	 * Initializes all tiles, randomly choosing which have underlying mines.
	 *
	 * @postcondition This board will now have a new set of tiles, some of which are mines.
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
			Collections.shuffle(mines, new Random(0));
		}
		else
		{
			Collections.shuffle(mines);
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

				for (int i = row - 1; i <= row + 1; i++) //Begins with the row directly above the clicked tile
				{
					for (int j = col - 1; j <= col + 1; j++) //Begins from the column directly left of the clicked tile
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