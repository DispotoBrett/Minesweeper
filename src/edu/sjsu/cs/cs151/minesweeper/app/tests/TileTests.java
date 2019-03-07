package edu.sjsu.cs.cs151.minesweeper.app.tests;

import edu.sjsu.cs.cs151.minesweeper.app.Tile;
import org.junit.*;
import static org.junit.Assert.*;

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
}
