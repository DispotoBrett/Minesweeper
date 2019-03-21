package edu.sjsu.cs.cs151.minesweeper.model;

/**
 * The over-arching game logic class.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class MineSweeper 
{
   public static void main(String[] args)
   {
      Board gameBoard = new Board(false);
      int numberOfTiles = Board.NUM_ROWS * Board.NUM_COLS;
      boolean gameInProgress = true;

//      while (gameInProgress)
//      {
//         //TODO single turn logic
//
//         //TODO game over logic
//         if (gameBoard.getNumberTilesRevealed() == numberOfTiles - gameBoard.NUM_MINES)
//         {
//            gameInProgress = false;
//         }
//      }
   }
}