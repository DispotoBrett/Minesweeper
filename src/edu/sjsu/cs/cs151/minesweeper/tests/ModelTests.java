package edu.sjsu.cs.cs151.minesweeper.tests;

import org.junit.*;
import static org.junit.Assert.*;
import edu.sjsu.cs.cs151.minesweeper.model.Model;

public class ModelTests
{
   @Test
   public void newGameTest()
   {
	   Model game = new Model();
	   
	   assertFalse( game.gameWon());
	   assertFalse(game.gameLost());
   }
   
   @Test
   public void winTest()
   {
	   Model game = new Model();

		assertFalse( game.gameWon());
		assertFalse(game.gameLost());

	   for(int i = 0; i < 9; i++)
	   {
		   for(int j = 0; j < 9; j++)
		   {
		   	if(!game.getBoard().isMine(i , j))
				{
					game.revealTile(i, j);
				}
		   }
	   }

		assertTrue(game.gameWon());
		assertFalse(game.gameLost());
   }
   
   @Test
   public void loseTest()
   {
	   Model game = new Model();

		assertFalse( game.gameWon());
		assertFalse(game.gameLost());

	   for(int i = 0; i < 9; i++)
	   {
		   for(int j = 0; j < 9; j++)
		   {
				   if(game.getBoard().isMine(i , j))
				   {
				   	game.revealTile(i, j);
				   	break;
				   }
		   }
	   }

		assertFalse(game.gameWon());
		assertTrue(game.gameLost());
   }

   @Test
	public void revealTileTest()
	{
		Model game = new Model();

		assertFalse(game.getTileAt(0, 0).isRevealed());

		game.revealTile(0, 0);

		assertTrue(game.getTileAt(0, 0).isRevealed());
	}

   @Test
	public void toggleFlagTest()
	{
		Model game = new Model();

		assertFalse(game.getTileAt(0, 0).isFlagged());

		game.toggleFlag(0, 0);

		assertTrue(game.getTileAt(0, 0).isFlagged());
	}
}