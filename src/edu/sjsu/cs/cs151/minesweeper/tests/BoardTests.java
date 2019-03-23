package edu.sjsu.cs.cs151.minesweeper.tests;
import org.junit.*;
import edu.sjsu.cs.cs151.minesweeper.model.Board;
import edu.sjsu.cs.cs151.minesweeper.model.Tile;

import static org.junit.Assert.*;

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
	public void flagTest()
	{
		Board testBoard = new Board(true);

		assertFalse(testBoard.getTileAt(0, 0).isMine());

		assertFalse(testBoard.getTileAt(0, 0).isFlagged());

		testBoard.toggleFlag(0,0);
		assertTrue(testBoard.getTileAt(0,0).isFlagged());

		testBoard.revealTile(0,0);
		assertFalse(testBoard.getTileAt(0,0).isRevealed());
		assertTrue(testBoard.getTileAt(0, 0).isFlagged());

		testBoard.toggleFlag(0, 0);
		assertFalse(testBoard.getTileAt(0, 0).isFlagged());

		testBoard.revealTile(0, 0);
		testBoard.toggleFlag(0, 0);
		assertFalse(testBoard.getTileAt(0, 0).isFlagged());
	}
	
	@Test
	public void adjacentMinesTest()
	{
		Board testBoard = new Board(true);
		
		int noMines = testBoard.adjacentMines(0, 0);
		assertTrue(noMines == 0);
		
		int twoMines = testBoard.adjacentMines(3,4);
		assertTrue(twoMines == 2);
		
		int threeMines = testBoard.adjacentMines(4, 5);
		assertTrue(threeMines == 3);
	}
	
	@Test
	public void revealTest()
	{
		Board testBoard = new Board(true);
		// Testing a single reveal
		assertTrue(!testBoard.getTileAt(0, 4).isRevealed());
		testBoard.revealTile(0, 4);
		assertTrue(testBoard.getTileAt(0, 4).isRevealed());
		
		// Testing the recursive reveal
		assertTrue(!testBoard.getTileAt(1, 8).isRevealed());
		testBoard.revealTile(0, 8);
		assertTrue(testBoard.getTileAt(1, 8).isRevealed());
		
		// Testing single reveal on a flag
		assertTrue(!testBoard.getTileAt(2, 4).isRevealed());
		testBoard.toggleFlag(2, 4);
		testBoard.revealTile(2, 4);
		assertTrue(!testBoard.getTileAt(2, 4).isRevealed());
		
		// Testing recursive reveal on a flag
		assertTrue(!testBoard.getTileAt(5, 2).isRevealed());
		assertTrue(!testBoard.getTileAt(4, 2).isRevealed());
		assertTrue(!testBoard.getTileAt(3, 2).isRevealed());
		testBoard.toggleFlag(4, 2);
		assertTrue(testBoard.getTileAt(4, 2).isFlagged());
		testBoard.revealTile(8, 0);
		assertTrue(testBoard.getTileAt(5, 2).isRevealed());
		assertTrue(!testBoard.getTileAt(4, 2).isRevealed());
		assertTrue(!testBoard.getTileAt(3, 2).isRevealed());
		
		// Testing single reveal on a mine
		assertTrue(testBoard.getTileAt(1, 4).isMine());
		assertTrue(!testBoard.getTileAt(1, 4).isRevealed());
		testBoard.revealTile(1, 4);
		assertTrue(!testBoard.getTileAt(1, 4).isRevealed());
	}
	
	
	
	@Test
	public void visualTest() //Useful for determine the test cases
	{
		Board test = new Board(true);
		test.revealTile(0, 8);
		for(int i = 0; i < Board.NUM_ROWS; i++)
		{
			for(int j = 0; j < Board.NUM_COLS; j++)
			{
				Tile current = test.getTileAt(i, j);
				
				if(current.isMine())
					System.out.print("X ");
				else if(current.isFlagged())
					System.out.print("F ");
				else if(current.isRevealed())
					System.out.print(test.adjacentMines(i, j) + " ");
				else
					System.out.print("O ");
			}
			System.out.println();
		}
	}
}
