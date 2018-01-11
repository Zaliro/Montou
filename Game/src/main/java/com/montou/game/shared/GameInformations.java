package com.montou.game.shared;

import java.util.List;
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
	
	// Plugins
	private List<String> playerOnePlugins;
	private List<String> playerTwoPlugins;
	private List<String> graphicsPlugins;
	
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
	
	public List<String> getPlayerOnePlugins() {
		return this.playerOnePlugins;
	}
	
	public void setPlayerOnePlugins(List<String> playerOnePlugins) {
		this.playerOnePlugins = playerOnePlugins;
	}
	
	public List<String> getPlayerTwoPlugins() {
		return this.playerTwoPlugins;
	}
	
	public void setPlayerTwoPlugins(List<String> playerTwoPlugins) {
		this.playerTwoPlugins = playerTwoPlugins;
	}
	
	public List<String> getGraphicsPlugins() {
		return this.graphicsPlugins;
	}
	
	public void setGraphicsPlugins(List<String> graphicsPlugins) {
		this.graphicsPlugins = graphicsPlugins;
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
