package edu.sjsu.cs.cs151.minesweeper.tests;

import edu.sjsu.cs.cs151.minesweeper.model.Board;
import edu.sjsu.cs.cs151.minesweeper.model.Model;
import edu.sjsu.cs.cs151.minesweeper.view.View;

public class ViewTester 
{
    static View view = new View(Board.NUM_ROWS,Board.NUM_COLS);
    static Model model = new Model();
    static final int ROW_INDEX = 0;
    static final int COL_INDEX = 1;

    public static void main(String[] args) throws InterruptedException
    {
    	sync(); //to flag every mine
    	
		while(true)
		{
			 if(!view.getQueue().isEmpty())
			 {
		        int[] msg = view.getQueue().remove();
		        
		        if(msg[2] == View.LEFT_CLICK)
		        {
			        model.getBoard().revealTile(msg[ROW_INDEX], msg[COL_INDEX]);
		        }
		        else if(msg[2] == View.RIGHT_CLICK)
		        {
			        model.getTileAt(msg[ROW_INDEX], msg[COL_INDEX]).toggleFlag();
		        }
		        sync();
			 }
		}
    }
    
    public static void sync()
    {
    	for(int i = 0; i < Board.NUM_ROWS; i++)
    	{
    		for(int j = 0; j < Board.NUM_COLS; j++)
    		{
    			if(model.getBoard().getTileAt(i, j).isRevealed())
    			{
    				view.reveal(i, j);
    			} 
    			else
    			{
    				view.flag(i, j, model.getBoard().getTileAt(i, j).isFlagged());	
    			}
    			
   				//-------------To see Mine placement-----------
    			if(model.getBoard().getTileAt(i, j).isMine())
    			{
    				view.flag(i, j, true);
    			} 
    		}
    	}
    }  
}