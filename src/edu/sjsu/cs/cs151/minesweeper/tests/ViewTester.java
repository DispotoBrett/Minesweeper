package edu.sjsu.cs.cs151.minesweeper.tests;

import edu.sjsu.cs.cs151.minesweeper.view.View;

public class ViewTester 
{
    public static void main(String[] args) throws InterruptedException
    {
        View view = new View(9,9);
		view.reveal(0,0);
		view.reveal(1,1);
		view.reveal(2,2);
		view.reveal(3,3);
		view.reveal(4,4);
		view.reveal(5,5);
		view.flag(6,6);
		view.flag(5,6);
		view.flag(6,6);
		view.flag(0,0);
		Thread.sleep(5000);//you have 5 second to press a button 


		//Having problems with the below loop,
		//Unless I pause the program and quickly click.
		while(true)
		{
			 if(!view.getQueue().isEmpty())
			 {
		        int[] msg = view.getQueue().remove();
		        if(msg[2] == View.LEFT_CLICK)
			        view.reveal(msg[0], msg[1]);
		        else
			        view.flag(msg[0], msg[1]);
	
			 }
		}
	        	
    }
}