package com.montou.game.engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.montou.game.plugins.Plugin;
import com.montou.game.shared.GameInformations;

public class MenuFrame extends JFrame {
	String title;
	GameInformations gameInformations;
	List<Plugin> plugins;
	private JPanel contentPane;
	JPanel panelHome;
	PluginsList panelPlugins;
	boolean start = false;
	boolean loadLastGame = false;
	
	public MenuFrame (String title, GameInformations gameInformations, List<Plugin> plugins) {

		this.gameInformations = gameInformations;
		this.plugins = plugins;
		
		super.setTitle(title);
		super.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, gameInformations.getWidth(), gameInformations.getHeight());
		
		panelHome = new JPanel();
		panelPlugins = new PluginsList(plugins, gameInformations);
		
		panelHome.setBounds(0, 0, gameInformations.getWidth(), gameInformations.getHeight());
		panelHome.setLayout(null);
	
		//Code 1st screen
		JButton bNewGame = new JButton("Nouvelle partie");
		bNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContentPane(panelPlugins);
				revalidate();
			}
		});
		bNewGame.setBounds(135, 97, 196, 61);
			
		JButton bContinue = new JButton("Poursuivre");
		bContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadLastGame = true;
				start();
			}
		});
		bContinue.setBounds(135, 267, 196, 61);
		
		panelHome.add(bNewGame);
		panelHome.add(bContinue);
		
		setContentPane(panelHome);
		revalidate();
	}
	
	public void goBack() {
		setContentPane(panelHome);
		revalidate();
	}
	
	public void start() {
		this.start = true;
	}
	
	public boolean canStart() {
		return this.start;
	}
	
	public boolean tryLoadLastGame() {
		return this.loadLastGame;
	}
	
	List<Plugin> getP1Plugins(){
		List<Plugin> listPlugins = panelPlugins.getPluginP1();
		return listPlugins;
	}
	List<Plugin> getP2Plugins(){
		List<Plugin> listPlugins = panelPlugins.getPluginP2();
		return listPlugins;
	}
	
	List<Plugin> getGraphPlugins(){
		List<Plugin> listPlugins = panelPlugins.getPluginGraph();
		return listPlugins;
	}
}
