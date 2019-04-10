package edu.sjsu.cs.cs151.minesweeper.tests;

import edu.sjsu.cs.cs151.minesweeper.model.Tile;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TileTests
{
	@Test
	public void constructionTest()
	{
		Tile testTile = new Tile(false);

		assertFalse(testTile.isMine());
		assertFalse(testTile.isRevealed());
		assertFalse(testTile.isFlagged());

		testTile = new Tile(true);

		assertTrue(testTile.isMine());
		assertFalse(testTile.isRevealed());
		assertFalse(testTile.isFlagged());
	}

	@Test
	public void revealTest()
	{
		Tile testTile = new Tile(true);

		testTile.reveal();
		assertTrue(testTile.isRevealed());

		testTile.reveal(); //Tile cannot be unrevealed
		assertTrue(testTile.isRevealed());

		testTile = new Tile(false);

		testTile.reveal();
		assertTrue(testTile.isRevealed());

		testTile.reveal(); //Tile cannot be unrevealed
		assertTrue(testTile.isRevealed());
	}

	@Test
	public void flagTest()
	{
		Tile testTile = new Tile(true);

		testTile.toggleFlag();
		assertTrue(testTile.isFlagged());

		testTile.toggleFlag();
		assertFalse(testTile.isFlagged());

		testTile = new Tile(false);

		testTile.toggleFlag();
		assertTrue(testTile.isFlagged());

		testTile.toggleFlag();
		assertFalse(testTile.isFlagged());
	}

	@Test
	public void revealAndFlagTest()
	{
		Tile testTile = new Tile(true);

		testTile.toggleFlag();
		assertTrue(testTile.isFlagged());
		assertFalse(testTile.reveal());
		assertFalse(testTile.isRevealed());

		testTile.toggleFlag();
		assertFalse(testTile.isFlagged());
		assertTrue(testTile.reveal());
		assertTrue(testTile.isRevealed());

		testTile = new Tile(false);

		testTile.toggleFlag();
		assertTrue(testTile.isFlagged());
		assertFalse(testTile.reveal());
		assertFalse(testTile.isRevealed());

		testTile.toggleFlag();
		assertFalse(testTile.isFlagged());
		assertTrue(testTile.reveal());
		assertTrue(testTile.isRevealed());
	}
}