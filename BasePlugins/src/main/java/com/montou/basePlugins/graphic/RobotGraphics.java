package com.montou.basePlugins.graphic;

import java.awt.Color;
import java.awt.Graphics;

import com.montou.game.plugins.annotations.AGraphic;
import com.montou.game.shared.GameInformations;

@AGraphic(useCustomData = true)
public class RobotGraphics {
	
	private Color playerOneColor;
	private Color playerTwoColor;
	
	// Implementation d'une methode graphique
	
	public void draw(GameInformations gameInformations, Graphics g) {
		
		// Assignation des couleurs...
		if (playerOneColor == null)
			playerOneColor = new Color((int)(Math.random() * 0x1000000));
		
		if (playerTwoColor == null)
			playerTwoColor = new Color((int)(Math.random() * 0x1000000));
		
		// Generation des graphismes pour les deux robots...
		for(int i = 0; i < 2; i++) {
			g.setColor((i <= 0 ? playerOneColor : playerTwoColor));
			
			// Lignes...
			int rowHt = gameInformations.getHeight() / gameInformations.getLines();
			// Colonnes...
			int rowWid = gameInformations.getWidth() / gameInformations.getCols();
			
			int subRowHt = (rowHt / 2);
			int subRowWid = (rowWid / 2);
			
			int playerX = ((i <= 0 ? gameInformations.getPlayerOne().getCurrentColumn()
					: gameInformations.getPlayerTwo().getCurrentColumn()) * rowWid) - subRowWid;
			int playerY = ((i <= 0 ? gameInformations.getPlayerOne().getCurrentLine()
					: gameInformations.getPlayerTwo().getCurrentLine()) * rowHt) - subRowHt;

			g.fillRect(playerX - (subRowWid / 2), playerY - (subRowHt / 2), subRowWid, subRowHt);
		}
	}
	
	// Implementation d'une persistence personnalisee
	// Ces methodes sont appelees car l'attribut useCustomData est e true
	
	public void onLoad(Object[] objects) {
		this.playerOneColor = (Color) objects[0];
		this.playerTwoColor = (Color) objects[1];
	}
	
	public Object[] onSave() {
		return new Object[] { this.playerOneColor, this.playerTwoColor };
	}
}
