package edu.sjsu.cs.cs151.minesweeper.controller;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs.cs151.minesweeper.model.*;
import edu.sjsu.cs.cs151.minesweeper.view.View;

/**
 * The controller class, which coordinates a model object and a view object.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */

public class Controller
{
	
	public Controller()
	{
		model = new Model();
		
		view = new View(Board.NUM_ROWS, Board.NUM_COLS, model.getBoard().adjacentMines());
		
	}
	public void mainLoop()
	{
		Queue<int[]> mainQueue = view.getQueue();
		
		while(true)
		{
			if(!mainQueue.isEmpty())
			{
				int[] message = mainQueue.remove();
				
				if(message[2] == View.RIGHT_CLICK)
					model.toggleFlag(message[0], message[1]);
				
				else if(message[2] == View.LEFT_CLICK)
					model.revealTile(message[0], message[1]);
			}
			
			updateView();
		}
	}
	
	public void updateView()
	{
		BoardIterator it = model.boardIterator();
		
		while(it.hasNext())
		{
			Tile current = it.next();
			
			if(current.isFlagged())
				view.flag(it.prevRow(), it.prevCol(), true);
			
			else if(current.isRevealed())
				view.reveal(it.prevRow(), it.prevCol());
		}
		
	}
	//TODO: updateGameInfo()

	//-------------------------Private Fields/Methods------------------
	private Model model;
	private View view;
}