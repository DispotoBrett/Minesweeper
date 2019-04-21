package edu.sjsu.cs.cs151.minesweeper.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class fojps{
	public static void main( String... args) throws InterruptedException
	{
		JFrame f = new JFrame();
		f.setSize(new Dimension(400, 400));
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Explosion e = new Explosion(400,400, 400,400);
		f.add(e);
		

			while(!e.isDone())	
			{
				Thread.sleep(12);
				e.explode();
					f.repaint();
				
			}
		
	}
}
