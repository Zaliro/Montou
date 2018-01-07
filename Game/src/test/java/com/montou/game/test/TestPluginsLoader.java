package com.montou.game.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.montou.game.plugins.Plugin;
import com.montou.game.plugins.PluginsLoader;

public class TestPluginsLoader {

	// Rentrer le path du BasePlugin dans : " clique droit sur le test => Run as =>
	// Run configuration => arguments => vm arguments"
	// sous la forme -Durl MonJarPath
	
	/*
	private String jarPath = "C:/Users/Zeam/Desktop/Montou/BasePlugins/target/BasePlugins-0.0.1.jar";
	private PluginsLoader pl;
	
	@Before
	public void setup() {
		//this.jarPath = System.getProperty("url");
		if (jarPath == null) {
			System.err.println("Le chemin du jar BasePlugin est invalide");
		} else {
			try {
				pl = new PluginsLoader(this.jarPath);
			} catch (ClassNotFoundException e) {
				System.err.println("Le chemin du jar BasePlugin est invalide");
			}
		}
	}

	@Test
	public void loadPluginsTest() {
		List<Plugin> listPlugins = pl.loadPlugins();
		for (Plugin plugin : listPlugins) {
			System.out.println(plugin.getClazz().getName());
		}
		assertNotNull(listPlugins);
	}
	*/
}
