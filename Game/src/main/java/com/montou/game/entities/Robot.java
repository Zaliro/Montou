package com.montou.game.entities;

import java.io.Serializable;

public class Robot implements Serializable {
	
	private String name;
	
	private int currentLine;
	private int currentColumn;
	
	private int lifePoints;
	private int energyPoints;

	public Robot(String name, int startLine, int startColumn) {
		this.name = name;
		this.currentLine = startLine;
		this.currentColumn = startColumn;
		
		this.lifePoints = 100;
		this.energyPoints = 100;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCurrentLine() {
		return this.currentLine;
	}
	
	public void setCurrentLine(int line) {
		this.currentLine = line;
	}
	
	public int getCurrentColumn() {
		return this.currentColumn;
	}
	
	public void setCurrentColumn(int column) {
		this.currentColumn = column;
	}
	
	public int getLifePoints() {
		return this.lifePoints;
	}
	
	public void substractLifePoints(int value) {
		this.lifePoints -= value;
		if (this.lifePoints < 0)
			this.lifePoints = 0;
		
		System.out.println(String.format("Le joueur %s a perdu %s point(s) de vie (%s/100) !", this.name, value, this.lifePoints));
	}
	
	public int getEnergyPoints() {
		return this.energyPoints;
	}
	
	public void substractEnergyPoints(int value) {
		this.energyPoints -= value;
		if (this.energyPoints < 0)
			this.energyPoints = 0;
		
		System.out.println(String.format("Le joueur %s a perdu %s point(s) d'énergie (%s/100) !", this.name, value, this.energyPoints));
	}
	
	public void regen(double ratePerTurn) {
		int sum = (int)(2 * ratePerTurn);
		
		if (this.energyPoints < 100)
			this.energyPoints += sum;
		
		if (this.energyPoints > 100)
			this.energyPoints = 100;
		
		System.out.println(String.format("Le joueur %s a récupéré %s points d'énergie (%s/100) !", this.name, sum, this.energyPoints));
	}
	
	public boolean isActive() {
		return this.lifePoints > 0;
	}
	
	@Override
	public String toString() {
		return String.format("Name : %s, Line : %s, Column : %s, LifePoints : %s, EnergyPoints : %s, IsActive : %s", this.name,
				this.currentLine, this.currentColumn, this.lifePoints, this.energyPoints, this.isActive());
	}
}
