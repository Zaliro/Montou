package com.montou.basePlugins.movement;

import com.montou.basePlugins.utilities.EnumUtilities;
import com.montou.game.entities.Robot;
import com.montou.game.plugins.annotations.AMovement;
import com.montou.game.shared.Direction;
import com.montou.game.shared.GameInformations;

@AMovement(energyCost = 5)
public class ConvergentMovements {

	private int abs(int n) {
		return Math.abs(n);
	}

	public Direction move(GameInformations gameInformations, int currentPlayerId) {
		Robot opponent;
		Robot currentPlayer;
		if (currentPlayerId == 1) {
			opponent = gameInformations.getPlayerTwo();
			currentPlayer = gameInformations.getPlayerOne();
		} else {
			opponent = gameInformations.getPlayerOne();
			currentPlayer = gameInformations.getPlayerTwo();
		}

		int xAdv = opponent.getCurrentColumn();
		int yAdv = opponent.getCurrentLine();

		int xJ = currentPlayer.getCurrentColumn();
		int yJ = currentPlayer.getCurrentLine();
		int deltax = xAdv - xJ;
		int deltay = yAdv - yJ;

		// difference de colonnes > differences lignes
		if (abs(deltax) > abs(deltay)) {

			if ((deltax / abs(deltax)) == 1) {
				return Direction.RIGHT;
			} else {
				return Direction.LEFT;
			}
		} else {
			if ((deltay / abs(deltay)) == 1) {
				return Direction.DOWN;
			} else {
				return Direction.UP;
			}
		}
	}
}
