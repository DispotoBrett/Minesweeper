package edu.sjsu.cs.cs151.minesweeper.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	public enum Difficulty
	{EASY, MEDIUM, HARD, USA}

	/**
	 * @param usePresetSeed True if mine placement is to be predetermined, false otherwise.
	 */
	public Model(Boolean usePresetSeed)
	{
		this.usePresetSeed = usePresetSeed;
		//gameBoard = new Board(EASY_SIZE, EASY_SIZE, EASY_MINES, usePresetSeed);
		//numberOfTiles = gameBoard.getNumberOfTiles();
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
		
		case USA:
			gameBoard = new Board(readBoardShapeFromFile("resources/USA.png"), USA_MINES, usePresetSeed);
			break;
			
		default:
			gameBoard = new Board(EASY_SIZE, EASY_SIZE, EASY_MINES, usePresetSeed);
		}

		numberOfTiles = gameBoard.getNumberOfTiles();
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
	private int USA_MINES = 20;
	private Board gameBoard;
	private boolean usePresetSeed;
	private int numberOfTiles;
	private boolean gameWon;
	private boolean gameLost;
	
	/**
	 * Creates a board shape array using the black pixels (and only the black pixels) in the input picture
	 * @param filename the file name of the input picture
	 * @return a board shape where black pixel = true and any other color = false
	 */
	private boolean[][] readBoardShapeFromFile(String filename)
	{
		boolean[][] shape = null;
		BufferedImage img = null;

		try 
		{
		    img = ImageIO.read(new File(filename)); 
		    
		    int height = img.getHeight();
		    int width = img.getWidth();
		    
		    shape = new boolean[height][width];
		    
		    for(int row = 0; row < height; row++)
		    {
		    	for(int col = 0; col < width; col++)
		    	{
		    		if(img.getRGB(col, row) == 0xffffffff) //if it is white, it is false
		    		{
		    			shape[row][col] = false;
		    		}
		    			
		    		else
		    			shape[row][col] = true;
		    	}
		    }
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
		
		return shape;
	}
}