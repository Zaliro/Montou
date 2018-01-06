package com.montou.game.shared;

import java.io.Serializable;

import com.montou.game.entities.Robot;

public class GameInformations implements Serializable, Cloneable {
	
	// Tailles
	private int width, height;
	private int lines, cols;
	
	// Joueurs
	private Robot playerOne;
	private Robot playerTwo;
	
	// Divers
	private boolean isFinished;
	private int exitCode;
	
	public GameInformations(int width, int height, int lines, int cols, Robot playerOne, Robot playerTwo) {
		this.width = width;
		this.height = height;
		this.lines = lines;
		this.cols = cols;
		
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		
		this.isFinished = false;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getLines() {
		return this.lines;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public Robot getPlayerOne() {
		return this.playerOne;
	}
	
	public Robot getPlayerTwo() {
		return this.playerTwo;
	}
	
	public boolean isFinished() {
		return this.isFinished;
	}
	
	public void finish() {
		this.finish(-1);
	}
	
	public void finish(int exitCode) {
		this.exitCode = exitCode;
		this.isFinished = true;
	}
}
