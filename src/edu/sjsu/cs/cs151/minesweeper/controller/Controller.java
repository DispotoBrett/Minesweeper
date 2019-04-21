package edu.sjsu.cs.cs151.minesweeper.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		SwingUtilities.invokeAndWait(() -> view = new View());
		view.getQueue().take();
		model = new Model(Model.Difficulty.EASY);

		SwingUtilities.invokeAndWait(()
				-> view.startGame(model.getBoard().getRows(), model.getBoard().getColumns(), model.getBoard().adjacentMines()));

	}

	public void mainLoop() throws InterruptedException
	{
		BlockingQueue<int[]> mainQueue = view.getQueue();

		while (true)
		{
			int[] message = mainQueue.take();

			if (message[2] == View.RIGHT_CLICK)
			{
				model.toggleFlag(message[0], message[1]);
			}

			else if (message[2] == View.LEFT_CLICK)
			{
				model.revealTile(message[0], message[1]);
			}

			else if (message[2] == View.RESET_GAME)
			{
				model = new Model(difficulty);
				view.resetTo(model.getBoard().getRows(), model.getBoard().getColumns(), model.getBoard().adjacentMines());
			}
			else if (message[2] == View.EXIT)
			{
				System.exit(0);
			}
			else if (message[2] == View.EASY_DIFFICULTY)
			{
				difficulty = Model.Difficulty.EASY;
			}
			else if (message[2] == View.MEDIUM_DIFFICULTY)
			{
				difficulty = Model.Difficulty.MEDIUM;
			}
			else if (message[2] == View.HARD_DIFFICULTY)
			{
				difficulty = Model.Difficulty.HARD;
			}
			if (message[0] != -1)
			{
				ExecutorService service = Executors.newCachedThreadPool();
				service.execute(() -> {
					try
					{
						updateView();
						if (model.gameLost())
						{
							gameOver();
							SwingUtilities.invokeLater(() -> view.explode(message[0], message[1]));
						}
					}
					catch (InvocationTargetException | InterruptedException e)
					{
						e.printStackTrace();
					}
				});
			}
		}
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
	private Model.Difficulty difficulty = Model.Difficulty.EASY;

}