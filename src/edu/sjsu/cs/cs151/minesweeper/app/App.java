package edu.sjsu.cs.cs151.minesweeper.app;

import edu.sjsu.cs.cs151.minesweeper.controller.Controller;

/**
 * The main class, which initializes all other classes.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */

public class App
{
	public static void main(String[] args)
	{
		Controller control = new Controller();
		
		control.mainLoop();
	}
}