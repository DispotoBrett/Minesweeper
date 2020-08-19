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
	 * Constructs a square Board with or without randomized mine placement.
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
		boardShape = new boolean[NUM_ROWS][NUM_COLS];
		
		for(int i = 0; i < NUM_ROWS; i++)
			for(int j = 0; j < NUM_COLS; j++)
				boardShape[i][j] = true;
		
		initializeTiles(usePresetSeed);
		initializeAdjacentMines();
		numberTilesRevealed = 0;
		numberTilesFlagged = 0;
	}

	/**
	 * Constructs a Board with a custom shape or without randomized mine placement.
	 * 
	 * @param boardShape 	A 2d array of booleans that define the shape of the board. A true value indicates that a tile should be placed in that location
	 * @param numberOfMines The number of mines in the Board.
	 * @param usePresetSeed	True if mine placement is to be predetermined, false otherwise.
	 */
	public Board(boolean[][] boardShape, int numberOfMines, boolean usePresetSeed)
	{
		NUM_ROWS = boardShape.length;
		NUM_COLS = boardShape[0].length;
		NUM_MINES = numberOfMines;

		tiles = new Tile[NUM_ROWS][NUM_COLS];
		this.boardShape = boardShape;
		initializeTiles(usePresetSeed);
		initializeAdjacentMines();
		numberTilesRevealed = 0;
		numberTilesFlagged = 0;
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
	 * @precondition a tile at row,col must exist
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
					if (tileExistsAt(i, j))
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

		if (tiles[row][col].isFlagged())
		{
			numberTilesFlagged++;
		}
		else
		{
			numberTilesFlagged--;
		}
	}

	/**
	 * Gets the number of mines adjacent to the specified tile.
	 *
	 * @param row The row of the specified tile.
	 * @param col The column of the specified tile.
	 * @return the number of adjacent mines
	 */
	public int adjacentMines(int row, int col)
	{
		return adjMines[row][col];
	}

	/**
	 * Gets the number of tiles on the Board.
	 *
	 * @return The number of tiles.
	 */
	public int getNumberOfTiles()
	{
		return numberOfTiles;
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
	 * Gets the number of tiles flagged on the Board.
	 *
	 * @return The number of tiles flagged.
	 */
	public int getNumberTilesFlagged()
	{
		return numberTilesFlagged;
	}

	/**
	 * Gets the Tile at the specified location.
	 *
	 * @param row the row
	 * @param col the column
	 * @return Tile at the specified location.
	 */
	public Tile getTileAt(int row, int col)
	{
		return tiles[row][col];
	}

	/**
	 * Determines if the Tile at the specified location is a mine.
	 *
	 * @param row the row
	 * @param col the column
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
	 * Get the 'shape' of the board, as represented by a 2d array of booleans where a true value indicates that there is a tile in that position
	 * @return
	 */
	public boolean[][] getBoardShape()
	{
		return boardShape;
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

	public boolean tileExistsAt(int row, int col)
	{
		return row >= 0 && row < NUM_ROWS &&
				col >= 0 && col < NUM_COLS &&
				boardShape[row][col];
	}
	
	//-------------------------Private Fields/Methods------------------
	private final int NUM_ROWS;
	private final int NUM_COLS;
	private final int NUM_MINES;

	private boolean[][] boardShape;
	private Tile[][] tiles;

	// Each index in adjMines stores a value that indicates how many mines are adjacent to the same index in tiles
	private int[][] adjMines;

	private int numberOfTiles;
	private int numberTilesRevealed;
	private int numberTilesFlagged;

	private final int DEFAULT_SEED = 0;

	/**
	 * Initializes all tiles, randomly choosing which have underlying mines.
	 *
	 * @param usePresetSeed True if mine placement is to be predetermined, false otherwise
	 */
	private void initializeTiles(boolean usePresetSeed)
	{
		numberOfTiles = 0;
		ArrayList<Integer> mines = new ArrayList<>();

		for (int i = 0; i < NUM_ROWS * NUM_COLS; i++)
		{

			if(boardShape[i / NUM_COLS][i % NUM_COLS])
			{
				mines.add(i);
				numberOfTiles++;
			}
				
		}
		
		if (usePresetSeed)
		{
			//Generates a Board with identical mine placement every time the game is played.
			Collections.shuffle(mines, new Random(DEFAULT_SEED));
		}
		else
		{
			Collections.shuffle(mines, SingleRandom.getInstance());
		}

		mines = new ArrayList<>(mines.subList(0, NUM_MINES));

		int tileCounter = 0;
		for (int i = 0; i < NUM_ROWS; i++)
		{
			for (int j = 0; j < NUM_COLS; j++)
			{
				if(boardShape[i][j])
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
				else
					tiles[i][j] = null;
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
				if(tileExistsAt(row, col))
				{
					int mines = 0;

					for (int i = row - 1; i <= row + 1; i++) //Begin with the row directly above the clicked tile
					{
						for (int j = col - 1; j <= col + 1; j++) //Begin from the column directly left of the clicked tile
						{
							if (tileExistsAt(i, j)) 
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
				else
				{
					adjMines[row][col] = 0;
				}	
			}
		}
	}
}