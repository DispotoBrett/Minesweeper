package edu.sjsu.cs.cs151.minesweeper.controller;

import edu.sjsu.cs.cs151.minesweeper.model.Board;
import edu.sjsu.cs.cs151.minesweeper.model.BoardIterator;
import edu.sjsu.cs.cs151.minesweeper.model.Model;
import edu.sjsu.cs.cs151.minesweeper.model.Tile;
import edu.sjsu.cs.cs151.minesweeper.view.View;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * The controller class, which coordinates a model object and a view object.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */

public class Controller
{
	//-------------------------Public Interface-----------------------
	
	/**
	 * Creates the Controller 
	 * 
	 * @param model the Model that the Controller acts on
	 * @param view the View that the Controller acts on
	 * @param messageQueue the queue that will contain messages sent from the View to be processed by the Controller
	 */
	public Controller(Model model, View view, BlockingQueue<Message> messageQueue)
	{
		this.model = model;
		this.view = view;
		messages = messageQueue;
		onWelcomeMenu = true;

		initValves();
	}
	
	/**
	 * Main loop of the game. Handles input from the View. 
	 * When this method stops running, the game is over and the program can begin shutting down
	 */
	public void mainLoop()
	{
		Valve.ValveResponse response = Valve.ValveResponse.EXECUTED;
		Message message = null;

		while (response != Valve.ValveResponse.FINISH)
		{
			try
			{
				message = messages.take();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			for (Valve valve : valves)
			{
				response = valve.execute(message);

				if (response != Valve.ValveResponse.MISS)
				{
					break;
				}
			}
		}
	}
	
	/**
	 * Starts a new game. Model and View are reset to the last chosen difficulty
	 */
	public void reset()
	{
		gameOver = false;
		model.setDifficultyAndReset(difficulty);
		view.resetTo(model.getBoard().getRows(), model.getBoard().getColumns(), model.getBoard().adjacentMines());
	}
	
	/**
	 * Updates the View based on the Model. Called whenever something is changed in the Model
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public void updateView() throws InvocationTargetException, InterruptedException
	{
		
		BoardIterator it = model.boardIterator();
		
		// Iterates through the Tiles stored by the Board in the Model in a left to right, top to bottom fashion
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
		view.repaint();
	}
	
	/**
	 * Handles displaying all mines on the board when the player triggers a game over
	 * @throws InvocationTargetException 
	 * @throws InterruptedException
	 */
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

	//-------------------------Private Fields/Methods------------------
	private Model model;
	private View view;
	private BlockingQueue<Message> messages;
	private List<Valve> valves = new LinkedList<>();
	private static boolean gameOver;
	private boolean onWelcomeMenu; // Indicates whether the player is still on the Welcome Menu. True until they advance past it
	private Model.Difficulty difficulty;
	
	/**
	 * Translates a Difficulty from the Model into the corresponding Difficulty in the View
	 * @param translateMe the Model Difficulty that is going to be translated
	 * @return the corresponding Difficulty in the View
	 */
	private View.Difficulty translateDifficulty(Model.Difficulty translateMe)
	{
		switch (translateMe)
		{
		case EASY:
			return View.Difficulty.EASY;

		case MEDIUM:
			return View.Difficulty.MEDIUM;

		case HARD:
			return View.Difficulty.HARD;

		default:
			return null;
		}
	}
	
	/**
	 * Initializes the Valves used by the Controller 
	 */
	private void initValves()
	{
		// Adds listening for right clicks
		valves.add(message -> {
			if (message.getClass() != RightClickMessage.class)
			{
				return Valve.ValveResponse.MISS;
			}
			RightClickMessage msg = (RightClickMessage) message;
			if (!gameOver)
			{
				model.toggleFlag(msg.getRow(), msg.getColumn());
			}

			try
			{
				updateView();
			}
			catch (InvocationTargetException | InterruptedException e)
			{
				e.printStackTrace();
			}
			return Valve.ValveResponse.EXECUTED;
		});

		// Adds listening for left clicks
		valves.add(new LeftClickValve());

		// Looks for beginning difficulty
		valves.add(new DifficultyValve());

		// Adds reset functionality
		valves.add(message -> {
			if (message.getClass() != ResetMessage.class)
			{
				return Valve.ValveResponse.MISS;
			}
			reset();
			return Valve.ValveResponse.EXECUTED;
		});

		// Adds exit functionality
		valves.add(message -> {
			if (message.getClass() != ExitMessage.class)
			{
				return Valve.ValveResponse.MISS;
			}
			return Valve.ValveResponse.FINISH;
		});
	}

	//-------------------------Private Classes------------------
	
	/**
	 * 
	 * Valves handle the execution of messages generated by the View
	 *
	 */
	private interface Valve
	{
		enum ValveResponse
		{MISS, EXECUTED, FINISH}

		/**
		 * Acts on the Model/View based on the message
		 *
		 * @param message the message that it will act on
		 * @return MISS if the Valve cannot process the message, EXECUTED if it can, and FINISH if the game is over
		 */
		ValveResponse execute(Message message);
	}
	
	/**
	 * Handles changing the difficulty in Controller to reflect the choice in View
	 * Also handles resetting the game 
	 */
	private class DifficultyValve implements Valve
	{
		public ValveResponse execute(Message message)
		{
			if (message.getClass() != DifficultyMessage.class)
			{
				return ValveResponse.MISS;
			}

			DifficultyMessage msg = (DifficultyMessage) message;

			difficulty = msg.getDifficulty();

			if (onWelcomeMenu) // True if the message was generated by the Welcome Menu
			{
				onWelcomeMenu = false;
				model.setDifficultyAndReset(difficulty);
				Board gameBoard = model.getBoard();
				view.startGame(gameBoard.getRows(), gameBoard.getColumns(), gameBoard.adjacentMines(),
					  translateDifficulty(difficulty));
			}
			else if (msg.shouldBeChangedNow())
			{
				reset();
			}

			return ValveResponse.EXECUTED;
		}

	}
	
	/**
	 * Handles left click input, which involves revealing Tiles
	 */
	private class LeftClickValve implements Valve
	{
		
		public ValveResponse execute(Message message)
		{
			if (message.getClass() != LeftClickMessage.class)
			{
				return ValveResponse.MISS;
			}

			LeftClickMessage msg = (LeftClickMessage) message;
			
			// Does nothing if the game has already been won or lost
			if (!gameOver)
			{
				// Reveals a Tile in the Model
				model.revealTile(msg.getRow(), msg.getColumn());
				
				// Updates the View to reflect the Model
				try
				{
					updateView();
				}
				catch (InvocationTargetException | InterruptedException e)
				{
					e.printStackTrace();
				}
				
				gameOver = model.gameLost() || model.gameWon();
				
				// If revealing a Tile in the Model caused the game to be won or lost, proceed with the game over sequence. 
				if (gameOver)
				{
					try
					{
						if (model.gameWon())
						{
							Thread.sleep(500);
							view.gameWon();
						}
						else
						{
							SwingUtilities.invokeAndWait(() -> view.explode(msg.getRow(), msg.getColumn()));
							Thread.sleep(3500);
							gameOver();
						}

					}
					catch (InvocationTargetException | InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
				
			return ValveResponse.EXECUTED;
		}
	}

}