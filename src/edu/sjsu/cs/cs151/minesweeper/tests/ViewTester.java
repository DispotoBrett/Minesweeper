package edu.sjsu.cs.cs151.minesweeper.tests;

import edu.sjsu.cs.cs151.minesweeper.view.View;

public class ViewTester 
{
    public static void main(String[] args) throws InterruptedException
    {
        View view = new View(9,9);

		while(true)
		{
			 if(!view.getQueue().isEmpty())
			 {
		        int[] msg = view.getQueue().remove();
		        if(msg[2] == View.LEFT_CLICK)
			        view.reveal(msg[0], msg[1]);
		        else if(msg[2] == View.RIGHT_CLICK)
			        view.flag(msg[0], msg[1]);
			 }
		}
	        	
    }
}