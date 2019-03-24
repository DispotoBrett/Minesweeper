package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import java.awt.*;

public class ExplosionTester
{
	public static final int DELAY = 18;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	public static final int EXPLOSION_X = 200;
	public static final int EXPLOSION_Y = 200;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("ExplosionTester");

		Explosion explosion = new Explosion(EXPLOSION_X, EXPLOSION_Y, WIDTH, HEIGHT);
		frame.add(explosion);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		
		Timer t = new Timer(DELAY, event ->
								{
									explosion.explode();
									frame.repaint();
								});
		t.start();
	}
}