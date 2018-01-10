package com.montou.basePlugins.graphic;

import java.awt.Color;
import java.awt.Graphics;

import com.montou.game.entities.Robot;
import com.montou.game.plugins.annotations.AGraphic;
import com.montou.game.shared.GameInformations;

@AGraphic(useCustomData = false)
public class RangeAttacksViewer {
	
	private int rowHt,rowWid;
	
	// Implementation d'une methode graphique
	
	private void fillRange(Robot robot,int range, Graphics g) {
		int robotCol = robot.getCurrentColumn();
		int robotLine = robot.getCurrentLine();
		int rangePos = range+1;
		for(int x = 0;x<=rangePos;x++) {
			int decalage = 0;
			for(int j = 1;j <= rangePos-x;j++) {
				decalage = j;
			}
			int largeur = 0;
			for(int z = 1;z <= x*2-1;z++){
				largeur = z;
			}
			if(robot.getName() == "P1") {
				g.setColor(new Color(255, 0, 0, 50));
			}
			else {
				g.setColor(new Color(0,255, 0, 50));
			}
			g.fillRect((robotCol-rangePos+decalage)*rowWid, (robotLine - (rangePos - x + 1))*rowHt, largeur*rowWid, rowHt);
			if(x != rangePos) {
			g.fillRect((robotCol-rangePos+decalage)*rowWid, (robotLine + (range - x))*rowHt, largeur*rowWid, rowHt);
			
			}
			
			
			
		}
	
		for(int x=0;x<=range;x++) {
			
		}
		
	}
	
	public void draw(GameInformations gameInformations, Graphics g) {
	
		// Generation des graphismes pour les deux robots...
		for(int i = 0; i < 2; i++) {
		
			
			// Lignes...
			this.rowHt = gameInformations.getHeight() / gameInformations.getLines();
			// Colonnes...
			this.rowWid = gameInformations.getWidth() / gameInformations.getCols();
			
			
			
			Robot playerOne = gameInformations.getPlayerOne();
			Robot playerTwo = gameInformations.getPlayerTwo();
			fillRange(playerOne,playerOne.getRangeAttack(),g);
			fillRange(playerTwo,playerTwo.getRangeAttack(),g);
		}
	}
	
}
