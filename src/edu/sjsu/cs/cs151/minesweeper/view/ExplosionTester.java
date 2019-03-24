package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import java.awt.*;

public class ExplosionTester
{
	public static final int DELAY = 15;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	public static final int EXPLOSION_X = 200;
	public static final int EXPLOSION_Y = 200;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("ExplosionTester");

		Explosion explosion = new Explosion(EXPLOSION_X, EXPLOSION_Y);
		frame.add(explosion);

//		FadeableString gameOverText = new FadeableString("Game over.", Color.WHITE, 48);
//		frame.add(gameOverText);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		
		Timer t = new Timer(DELAY, event ->
								{
									explosion.explode();
									//gameOverText.fadeIn();
									frame.repaint();
								});
		t.start();
	}
}