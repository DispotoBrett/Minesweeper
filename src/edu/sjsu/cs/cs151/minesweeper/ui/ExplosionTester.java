package edu.sjsu.cs.cs151.minesweeper.ui;

import javax.swing.*;

public class ExplosionTester
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("ExplosionTester");
		Explosion explosion = new Explosion(200,200);
		frame.add(explosion);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.setVisible(true);

		Timer t = new Timer(10, event -> 
								{
									explosion.explode();
									frame.repaint();
								});
		t.start();
	}
}