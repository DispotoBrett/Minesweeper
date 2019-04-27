package edu.sjsu.cs.cs151.minesweeper.controller;

import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

import edu.sjsu.cs.cs151.minesweeper.controller.Valve.ValveResponse;
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
	public Controller(Model model, View view, BlockingQueue<Message> messageQueue) throws InvocationTargetException, InterruptedException
	{
		this.model = model;
		this.view = view;
		messages = messageQueue;
		
		initValves();
	}

	public void mainLoop()
	{
		ValveResponse response = ValveResponse.EXECUTED;
		Message message = null;

		while (response != ValveResponse.FINISH)
		{
			try 
			{
				message = messages.take();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
			for(Valve valve: valves)
			{
				response = valve.execute(message);
				
				if(response != ValveResponse.MISS)
					break;
			}
		}
	}

	public void reset()
	{
		gameOver = false;
		model.reset();
		view.resetTo(model.getBoard().getRows(), model.getBoard().getColumns(), model.getBoard().adjacentMines());
	}

	public void updateView() throws InvocationTargetException, InterruptedException
	{

		BoardIterator it = model.boardIterator();

		while (it.hasNext())
		{
			Tile current = it.next();

			if (current.isRevealed())
			{
				SwingUtilities.invokeAndWait(() -> view.reveal(it.prevRow(), it.prevCol()));
			}
			else
			{
				SwingUtilities.invokeAndWait(() -> view.flag(it.prevRow(), it.prevCol(), current.isFlagged()));
			}
		}
	}

	public void gameOver() throws InvocationTargetException, InterruptedException
	{
		BoardIterator it = model.boardIterator();

		while (it.hasNext())
		{
			Tile current = it.next();
			if (current.isMine())
			{
				SwingUtilities.invokeAndWait(() -> view.exposeMine(it.prevRow(), it.prevCol()));
			}
		}
	}
	//TODO: updateGameInfo()

	//-------------------------Private Fields/Methods------------------
	private Model model;
	private View view;
	private BlockingQueue<Message> messages;
	private List<Valve> valves = new LinkedList<Valve>();
	private static boolean gameOver;
	private Model.Difficulty difficulty;
	
	private void initValves()
	{
		// Adds listening for right clicks
		valves.add( message -> { if(message.getClass() != RightClickMessage.class) return ValveResponse.MISS;
			RightClickMessage msg = (RightClickMessage) message;
			if(!gameOver)
				model.toggleFlag(msg.getRow(), msg.getColumn());
			
			try { updateView(); } 
			catch (InvocationTargetException | InterruptedException e) { e.printStackTrace(); }
			return ValveResponse.EXECUTED; }  );
		
		// Adds listening for left clicks
		valves.add( message -> { if(message.getClass() != LeftClickMessage.class) return ValveResponse.MISS;
			LeftClickMessage msg = (LeftClickMessage) message;
			if(!gameOver)
				model.revealTile(msg.getRow(), msg.getColumn());
			
			try { updateView(); } 
			catch (InvocationTargetException | InterruptedException e) { e.printStackTrace(); }
			return ValveResponse.EXECUTED; }  );
		
		// Looks for beginning difficulty
		valves.add(new DifficultyValve());
	}
	
	//-------------------------Private Classes------------------
	
	private class DifficultyValve implements Valve
	{
		public ValveResponse execute(Message message)
		{
			if(message.getClass() != StartDifficultyMessage.class)
					return ValveResponse.MISS;
			
			StartDifficultyMessage msg = (StartDifficultyMessage) message;
			
			switch(msg.getDifficulty())
			{
			case View.EASY_DIFFICULTY: model.setDifficulty(Model.Difficulty.EASY); break;
			
			case View.MEDIUM_DIFFICULTY: model.setDifficulty(Model.Difficulty.MEDIUM); break;
			
			case View.HARD_DIFFICULTY: model.setDifficulty(Model.Difficulty.HARD); break;
			
			default: model.setDifficulty(Model.Difficulty.EASY);
			}
			
			Board gameBoard = model.getBoard();
			view.startGame(gameBoard.getRows(), gameBoard.getColumns(), gameBoard.adjacentMines(), msg.getDifficulty());
			
			return ValveResponse.EXECUTED;
		}
	}

}