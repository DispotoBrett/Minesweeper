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
	public Controller(Model model, View view, BlockingQueue<Message> messageQueue)
	{
		this.model = model;
		this.view = view;
		messages = messageQueue;
		passedMenu = false;

		initValves();
	}

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

	public void reset()
	{
		gameOver = false;
		model.setDifficultyAndReset(difficulty);
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
	private List<Valve> valves = new LinkedList<>();
	private static boolean gameOver;
	private boolean passedMenu;
	private Model.Difficulty difficulty;

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

	public interface Valve
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

			if (!passedMenu)
			{
				passedMenu = true;
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

	private class LeftClickValve implements Valve
	{
		public ValveResponse execute(Message message)
		{
			if (message.getClass() != LeftClickMessage.class)
			{
				return ValveResponse.MISS;
			}

			LeftClickMessage msg = (LeftClickMessage) message;

			if (!gameOver)
			{
				model.revealTile(msg.getRow(), msg.getColumn());
			}

			if (!gameOver)
			{
				try
				{
					updateView();
				}
				catch (InvocationTargetException | InterruptedException e)
				{
					e.printStackTrace();
				}
			}

			if (!gameOver)
			{
				gameOver = model.gameLost() || model.gameWon();
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