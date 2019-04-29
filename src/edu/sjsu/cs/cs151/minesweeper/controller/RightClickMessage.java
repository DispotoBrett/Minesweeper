package edu.sjsu.cs.cs151.minesweeper.controller;

public class RightClickMessage extends Message
{
	public RightClickMessage(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	public int getRow()
	{
		return row;
	}

	public int getColumn()
	{
		return col;
	}

	private int row;
	private int col;
}
