package edu.sjsu.cs.cs151.minesweeper.tests;

import edu.sjsu.cs.cs151.minesweeper.model.Board;
import edu.sjsu.cs.cs151.minesweeper.model.BoardIterator;
import edu.sjsu.cs.cs151.minesweeper.model.Tile;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTests
{
	@Test
	public void constructorTest()
	{
		Board testBoard = new Board(false);

		int numberOfMines = 0;
		int numberOfTiles = 0;
		for (int row = 0; row < testBoard.getRows(); row++)
		{
			for (int col = 0; col < testBoard.getColumns(); col++)
			{
				if (testBoard.getTileAt(row, col).isMine())
				{
					numberOfMines++;
				}
				numberOfTiles++;
			}
		}

		assertEquals(numberOfMines, testBoard.getNumMines());
		assertEquals(numberOfTiles, testBoard.getRows() * testBoard.getColumns());
	}

	@Test
	public void flagTest()
	{
		Board testBoard = new Board(true);

		assertFalse(testBoard.getTileAt(0, 0).isMine());

		assertFalse(testBoard.getTileAt(0, 0).isFlagged());

		testBoard.toggleFlag(0, 0);
		assertTrue(testBoard.getTileAt(0, 0).isFlagged());

		testBoard.revealTile(0, 0);
		assertFalse(testBoard.getTileAt(0, 0).isRevealed());
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
		assertEquals(0, noMines);

		int twoMines = testBoard.adjacentMines(3, 4);
		assertEquals(2, twoMines);

		int threeMines = testBoard.adjacentMines(4, 5);
		assertEquals(3, threeMines);
	}

	@Test
	public void revealTest()
	{
		Board testBoard = new Board(true);
		// Testing a single reveal
		assertFalse(testBoard.getTileAt(0, 4).isRevealed());
		testBoard.revealTile(0, 4);
		assertTrue(testBoard.getTileAt(0, 4).isRevealed());

		// Testing the recursive reveal
		assertFalse(testBoard.getTileAt(1, 8).isRevealed());
		testBoard.revealTile(0, 8);
		assertTrue(testBoard.getTileAt(1, 8).isRevealed());

		// Testing single reveal on a flag
		assertFalse(testBoard.getTileAt(2, 4).isRevealed());
		testBoard.toggleFlag(2, 4);
		testBoard.revealTile(2, 4);
		assertFalse(testBoard.getTileAt(2, 4).isRevealed());

		// Testing recursive reveal on a flag
		assertFalse(testBoard.getTileAt(5, 2).isRevealed());
		assertFalse(testBoard.getTileAt(4, 2).isRevealed());
		assertFalse(testBoard.getTileAt(3, 2).isRevealed());
		testBoard.toggleFlag(4, 2);
		assertTrue(testBoard.getTileAt(4, 2).isFlagged());
		testBoard.revealTile(8, 0);
		assertTrue(testBoard.getTileAt(5, 2).isRevealed());
		assertFalse(testBoard.getTileAt(4, 2).isRevealed());
		assertFalse(testBoard.getTileAt(3, 2).isRevealed());

		// Testing single reveal on a mine
		assertTrue(testBoard.getTileAt(1, 4).isMine());
		assertFalse(testBoard.getTileAt(1, 4).isRevealed());
		testBoard.revealTile(1, 4);
		assertFalse(testBoard.getTileAt(1, 4).isRevealed());
	}


	@Test
	public void visualTest() //Useful for determining the test cases
	{
		Board test = new Board(true);
		test.revealTile(0, 8);
		for (int i = 0; i < test.getRows(); i++)
		{
			for (int j = 0; j < test.getColumns(); j++)
			{
				Tile current = test.getTileAt(i, j);

				if (current.isMine())
				{
					System.out.print("X ");
				}
				else if (current.isFlagged())
				{
					System.out.print("F ");
				}
				else if (current.isRevealed())
				{
					System.out.print(test.adjacentMines(i, j) + " ");
				}
				else
				{
					System.out.print("O ");
				}
			}
			System.out.println();
		}
	}

	@Test
	public void iteratorTest()
	{
		Board test = new Board(true);
		int tileCount = 0;

		for (Tile t : test)
		{
			tileCount++;
		}
		assertEquals(81, tileCount);

		tileCount = 0;

		BoardIterator iter = test.iterator();
		while (iter.hasNext())
		{
			iter.next();
			tileCount++;
		}
		assertEquals(81, tileCount);
	}
}