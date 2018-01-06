package com.montou.game.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.montou.game.plugins.Plugin;
import com.montou.game.shared.GameInformations;

public class GridCanvas extends Canvas {
	
	private int width, height;
	private int lines;
	private int cols;
	
	private GameInformations gameInformations;
	private List<Plugin> graphicsPlugins;
	
	public GridCanvas(int width, int height, int lines, int cols,
			List<Plugin> graphicsPlugins, GameInformations gameInformations) {
		super.setSize(width, height);
		this.lines = lines;
		this.cols = cols;
		
		this.graphicsPlugins = graphicsPlugins;
		this.gameInformations = gameInformations;
	}
	
	@Override
	public void paint(Graphics g) {
		int i;
		
		this.width = super.getSize().width;
		this.height = super.getSize().height;
		
		// Lignes...
		int rowHt = this.height / this.lines;
		for (i = 0; i < this.lines; i++) {
			g.drawLine(0, i * rowHt, this.width, i * rowHt);
		}
		
		// Colonnes...
		int rowWid = this.width / this.cols;
		for (i = 0; i < cols; i++) {
			g.drawLine(i * rowWid, 0, i * rowWid, this.height);
		}
		
		// Nous permettons au plugins graphiques de dessiner sur le canvas...
		for(Plugin p : this.graphicsPlugins) {
			try {
				Method drawMethod = p.getClazz().getMethod("draw", GameInformations.class, Graphics.class);
				if (drawMethod != null) {
					drawMethod.invoke(p.getInstance(), gameInformations, g);
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
}
