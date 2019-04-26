package edu.sjsu.cs.cs151.minesweeper.controller;

import edu.sjsu.cs.cs151.minesweeper.model.Board;
import edu.sjsu.cs.cs151.minesweeper.model.Model;
import edu.sjsu.cs.cs151.minesweeper.view.View;

public class StartDifficultyValve implements Valve
{
	public StartDifficultyValve(View view, Model model)
	{
		this.view = view;
		this.model = model;
	}
	
	@Override
	public ValveResponse execture(Message message)
	{
		if(message.getClass() != StartDifficultyMessage.class)
				return ValveResponse.MISS;
		
		StartDifficultyMessage msg = (StartDifficultyMessage) message;
		
		switch(msg.getDifficulty())
		{
		case View.EASY_DIFFICULTY: model.setDifficulty(Model.Difficulty.EASY); break;
		
		case View.MEDIUM_DIFFICULTY: model.setDifficulty(Model.Difficulty.MEDIUM); break;
		
		case View.HARD_DIFFICULTY: model.setDifficulty(Model.Difficulty.HARD); break;
		
		default: model.setDifficulty(Model.Difficulty.EASY);
		}
		
		Board gameBoard = model.getBoard();
		view.startGame(gameBoard.getRows(), gameBoard.getColumns(), gameBoard.adjacentMines(), msg.getDifficulty());
		
		return ValveResponse.EXECUTED;
	}
	
	private View view;
	private Model model;
}
