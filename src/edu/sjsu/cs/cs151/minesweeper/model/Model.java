package edu.sjsu.cs.cs151.minesweeper.model;

/**
 * The over-arching game logic class.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class Model
{
   //-------------------------Public Interface-----------------------
   /**
    * Constructs a new Model instance.
    */
   public Model()
   {
      gameBoard = new Board(false);
      numberOfTiles = gameBoard.NUM_ROWS * gameBoard.NUM_COLS;
      gameWon = false;
      gameLost = false;
   }

   /**
    * Updates the game progress based on the win/loss condition for MineSweeper.
    */
   public void updateGameProgress()
   {
      //win condition (all non-mine tiles have been revealed)
      if (gameBoard.getNumberTilesRevealed() == numberOfTiles - gameBoard.NUM_MINES)
      {
         gameWon = true;
      }
   }

   /**
    * Gets whether or not the game has been won.
    * @return True if the game has been won, false otherwise.
    */
   public boolean gameWon()
   {
      return gameWon;
   }
   
   /**
    * Gets whether or not the game has been lost.
    * @return True if the game has been lost, false otherwise.
    */
   public boolean gameLost()
   {
	   return gameLost;
   }

   /**
    * Reveals the tile specified, and all surrounding tiles without mines, recursively.
    * @param row The row of the tile specified.
    * @param col The column of the tile specified.
    */
   public void revealTile(int row, int col)
   {
	  gameLost = gameBoard.isMine(row, col);
	  
      if(!gameLost)
    	  gameBoard.revealTile(row, col);
   }

   /**
    * Flags the tile specified.
    * @param row The row of the tile specified.
    * @param col The column of the tile specified.
    */
   public void toggleFlag(int row, int col)
   {
      gameBoard.toggleFlag(row, col);
   }

   /**
    * Gets the tile at the specified location.
    * @param row The row of the tile specified.
    * @param col The column of the tile specified.
    * @return The tile at the specified location.
    */
   public Tile getTileAt(int row, int col)
   {
      return gameBoard.getTileAt(row, col);
   }

   //-------------------------Private Fields/Methods------------------
   private Board gameBoard;
   private int numberOfTiles;
   private boolean gameWon;
   private boolean gameLost;
}