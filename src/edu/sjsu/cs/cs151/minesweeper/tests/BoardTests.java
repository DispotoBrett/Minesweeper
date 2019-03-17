package edu.sjsu.cs.cs151.minesweeper.tests;

import org.junit.*;
import static org.junit.Assert.*;
import edu.sjsu.cs.cs151.minesweeper.app.Board;

public class BoardTests
{
	@Test
	public void constructorTest()
	{
		Board testBoard = new Board(false);
		
		int numberOfMines = 0;
		int numberOfTiles = 0;
		for(int row = 0; row < Board.NUM_ROWS; row++)
		{
			for(int col = 0; col < Board.NUM_COLS; col++)
			{
				if(testBoard.getTileAt(row, col).isMine())
					numberOfMines++;
				numberOfTiles++;
			}
		}

		assertEquals(numberOfMines, Board.NUM_MINES);
		assertEquals(numberOfTiles, Board.NUM_ROWS * Board.NUM_COLS);
	}
	
	@Test
	public void revealTest()
	{
		
	}
	
	@Test
	public void adjacentTilesTest()
	{
		
	}
}
