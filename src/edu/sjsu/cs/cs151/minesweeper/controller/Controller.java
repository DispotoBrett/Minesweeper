package edu.sjsu.cs.cs151.minesweeper.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

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
	
	public Controller() throws InvocationTargetException, InterruptedException
	{
		model = new Model();
		
		SwingUtilities.invokeAndWait(() 
				-> view = new View(model.getBoard().getRows(), model.getBoard().getColumns(), model.getBoard().adjacentMines()));
		
	}
	
	public void mainLoop() throws InvocationTargetException, InterruptedException
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
				
				else if(message[2] == View.RESET_GAME)
				{
					model = new Model();
					view.resetTo(model.getBoard().getRows(), model.getBoard().getColumns(), model.getBoard().adjacentMines());
				}
				else if(message[2] == View.EXIT)
				{
					System.exit(0);
				}
					
			}
			
			updateView();
		}
	}
	
	public void updateView() throws InvocationTargetException, InterruptedException
	{
		BoardIterator it = model.boardIterator();
		
		while(it.hasNext())
		{
			Tile current = it.next();
			
			if (current.isRevealed())
			    SwingUtilities.invokeAndWait(() -> view.reveal(it.prevRow(), it.prevCol()));
			else
			    SwingUtilities.invokeAndWait(() -> view.flag(it.prevRow(), it.prevCol(), current.isFlagged()));
		}
		
	}
	//TODO: updateGameInfo()

	//-------------------------Private Fields/Methods------------------
	private Model model;
	private View view;
}