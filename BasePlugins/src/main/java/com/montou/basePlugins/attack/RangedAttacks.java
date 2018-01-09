package com.montou.basePlugins.attack;

import com.montou.game.plugins.annotations.AAttack;
import com.montou.game.shared.GameInformations;

@AAttack(energyCost = 20, range = 3)
public class RangedAttacks {
	private int damage = 15;

	public int attack(GameInformations gameInformations) {
		int distanceLine = Math.abs(
				gameInformations.getPlayerOne().getCurrentLine() - gameInformations.getPlayerTwo().getCurrentLine());
		int distanceCollumn = Math.abs(gameInformations.getPlayerOne().getCurrentColumn()
				- gameInformations.getPlayerTwo().getCurrentColumn());
		int distance = distanceCollumn + distanceLine;

		if (distance <= 3) {
			return damage;
		}

		return 0;
	}
}
