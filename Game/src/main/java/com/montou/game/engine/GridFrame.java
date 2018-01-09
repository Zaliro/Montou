package com.montou.game.engine;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import com.montou.game.plugins.Plugin;
import com.montou.game.shared.GameInformations;

public class GridFrame extends Frame {
	
	private GridCanvas gridCanvas;
	private GameInformations gameInformations;
	
	public GridFrame(String title, GameInformations gameInformations, List<Plugin> graphicsPlugins) {
		super.setTitle(title);
		super.setResizable(false);
		this.gameInformations = gameInformations;
		
		// Creation du canvas...
		this.gridCanvas = new GridCanvas(this.gameInformations.getWidth(), this.gameInformations.getHeight(),
				this.gameInformations.getLines(), this.gameInformations.getCols(), graphicsPlugins, gameInformations);
		super.add(gridCanvas);
		
		// Abonnement e onWindowClosing...
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GridFrame.this.onWindowClosing();
		        setVisible(false);
		        dispose();
		        System.exit(0);
		      }
		});
		
		super.pack();
	}
	
	public void onWindowClosing() {
		// Nous forcons l'arret de la partie...
		this.gameInformations.finish();
	}
	
	@Override
	public void repaint() {
		this.gridCanvas.repaint();
		super.repaint();
	}
}
