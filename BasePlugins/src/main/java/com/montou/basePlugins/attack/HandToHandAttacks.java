package com.montou.basePlugins.attack;

import com.montou.game.plugins.annotations.AAttack;
import com.montou.game.shared.GameInformations;

@AAttack(energyCost = 10)
public class HandToHandAttacks {
	private int damage = 15;
	
	public int attack(GameInformations gameInformations){
		if(gameInformations.getPlayerOne().getCurrentLine() - gameInformations.getPlayerTwo().getCurrentLine() == 0){
			if(gameInformations.getPlayerOne().getCurrentColumn() - gameInformations.getPlayerTwo().getCurrentColumn() == 1){
				return damage;
			}else if(gameInformations.getPlayerOne().getCurrentColumn() - gameInformations.getPlayerTwo().getCurrentColumn() == -1){
				return damage;
			}
		}else if(gameInformations.getPlayerOne().getCurrentColumn() - gameInformations.getPlayerTwo().getCurrentColumn() == 0){
			if(gameInformations.getPlayerOne().getCurrentLine() - gameInformations.getPlayerTwo().getCurrentLine() == 1){
				return damage;
			}
			else if(gameInformations.getPlayerOne().getCurrentLine() - gameInformations.getPlayerTwo().getCurrentLine() == -1){
				return damage;
			}
		}
		return 0;
	}
}
