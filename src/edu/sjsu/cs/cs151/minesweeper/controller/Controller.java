package edu.sjsu.cs.cs151.minesweeper.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;
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

		//get the initial difficulty level based on startup screen input
		int initialDifficulty = view.getQueue().take()[2];
		if (initialDifficulty == View.EASY_DIFFICULTY)
		{
			model = new Model(Model.Difficulty.EASY);

			//update difficulty used for proper reset functionality 
			difficulty = Model.Difficulty.EASY;
		}
		else if (initialDifficulty == View.MEDIUM_DIFFICULTY)
		{
			model = new Model(Model.Difficulty.MEDIUM);
			difficulty = Model.Difficulty.MEDIUM;
		}
		else
		{
			model = new Model(Model.Difficulty.HARD);
			difficulty = Model.Difficulty.HARD;
		}


		SwingUtilities.invokeAndWait(()
				-> view.startGame(model.getBoard().getRows(), model.getBoard().getColumns(), model.getBoard().adjacentMines(), initialDifficulty));

	}

	public void mainLoop() throws InterruptedException, InvocationTargetException
	{
		BlockingQueue<int[]> mainQueue = view.getQueue();

		while (true)
		{
			int[] message = mainQueue.take();

			if (message[2] == View.RIGHT_CLICK)
			{
				if (!gameOver)
				{
					model.toggleFlag(message[0], message[1]);
				}
			}

			else if (message[2] == View.LEFT_CLICK)
			{
				if (!gameOver)
				{
					model.revealTile(message[0], message[1]);
				}
				gameOver = model.gameLost();
			}
			else if (message[2] == View.EXIT)
			{
				System.exit(0);
			}
			else if (message[2] == View.RESET_GAME)
			{
				reset();
			}
			else if (message[2] == View.EASY_DIFFICULTY)
			{
				difficulty = Model.Difficulty.EASY;
				SwingUtilities.invokeAndWait(() -> {
					if (JOptionPane.YES_OPTION == View.difficultyChanged())
					{
						reset();
					}
				});

			}
			else if (message[2] == View.MEDIUM_DIFFICULTY)
			{
				difficulty = Model.Difficulty.MEDIUM;
				SwingUtilities.invokeAndWait(() -> {
					if (JOptionPane.YES_OPTION == View.difficultyChanged())
					{
						reset();
					}
				});

			}
			else if (message[2] == View.HARD_DIFFICULTY)
			{
				difficulty = Model.Difficulty.HARD;
				SwingUtilities.invokeAndWait(() -> {
					if (JOptionPane.YES_OPTION == View.difficultyChanged())
					{
						reset();
					}
				});

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
							SwingUtilities.invokeAndWait(() -> view.explode(message[0], message[1]));
							Thread.sleep(2000);
							gameOver();

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

	public void reset()
	{
		gameOver = false;
		model = new Model(difficulty);
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
	private static boolean gameOver;
	private Model.Difficulty difficulty = Model.Difficulty.EASY;

}