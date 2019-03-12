package edu.sjsu.cs.cs151.minesweeper.app;

public class Board
{
	
	public void revealTile(int row, int col)
	{
		if(!tiles[col][row].isRevealed() && !tiles[row][col].isFlagged()) //Mine is neither revealed nor flagged
		{
			tiles[col][row].reveal();
			
			for(int i = row + 1; i >= row - 1; i--) //Begins with the row directly above the clicked tile and moves down
			{
				for(int j = col -1; j <= col - 1; j++) //Begins from the column directly left of the clicked tile and moves right
				{
					if(i >= 0 && j >= 0 && i < tiles.length && j < tiles[0].length) //Only true if i and j are valid indices 
					{
						revealTile(i, j); 
					}
				}
			}
		}
	}
	
	
	public int adjacentMines(int row, int col)
	{
		int mines = 0;
		
		for(int i = row + 1; i >= row - 1; i--) //Begins with the row directly above the clicked tile
		{
			for(int j = col -1; j <= col - 1; j++) //Begins from the column directly left of the clicked tile
			{
				if(i >= 0 && j >= 0 && i < tiles.length && j < tiles[0].length) //Only true if i and j are valid indices 
				{
					if(tiles[i][j].isMine()) 
						mines++;
				}
			}
		}
		
		return mines; 
	}
	
	private Tile[][] tiles;
}
