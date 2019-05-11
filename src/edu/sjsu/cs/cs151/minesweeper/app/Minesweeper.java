package edu.sjsu.cs.cs151.minesweeper.app;

import edu.sjsu.cs.cs151.minesweeper.controller.Controller;
import edu.sjsu.cs.cs151.minesweeper.message.Message;
import edu.sjsu.cs.cs151.minesweeper.model.Model;
import edu.sjsu.cs.cs151.minesweeper.view.View;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The main class, which initializes all other classes.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */

public class Minesweeper
{
	private static BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
	private static View view;
	private static Model model;

	public static void main(String[] args)
	{
		view = View.init(queue);
		model = new Model(false);
		Controller control = new Controller(model, view, queue);

		control.mainLoop();

		view.dispose();

		queue.clear();
	}
}