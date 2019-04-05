package edu.sjsu.cs.cs151.minesweeper.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import edu.sjsu.cs.cs151.minesweeper.model.Model;

public class ModelTests
{
   @Test
   public void newGameTest()
   {
	   Model game = new Model();
	   
	   assertEquals(false, game.gameWon());
	   assertEquals(false, game.gameLost());
   }
   
   @Test
   public void winTest()
   {
	   Model game = new Model();
	   
	   assertEquals(false, game.gameWon());
	   assertEquals(false, game.gameLost());
	   

	   for(int i = 0; i < 9; i++)
	   {
		   for(int j = 0; j < 9; j++)
		   {
				   if(!game.getBoard().isMine(i , j))
						   game.revealTile(i, j);
		   }
	   }
	   game.updateGameProgress();
	   
	   assertEquals(true, game.gameWon());	   
   }
   
   @Test
   public void loseTest()
   {
	   Model game = new Model();
	   
	   assertEquals(false, game.gameWon());
	   assertEquals(false, game.gameLost());
	   

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
	   
	   assertEquals(true, game.gameLost());	   
   }
}