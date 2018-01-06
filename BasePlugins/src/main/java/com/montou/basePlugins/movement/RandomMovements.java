package com.montou.basePlugins.movement;

import com.montou.basePlugins.utilities.EnumUtilities;
import com.montou.game.plugins.annotations.AMovement;
import com.montou.game.shared.Direction;
import com.montou.game.shared.GameInformations;

@AMovement(energyCost = 5)
public class RandomMovements {
	
	public Direction move(GameInformations gameInformations, int currentPlayer) {
		return EnumUtilities.randomValue(Direction.class);
	}
}
