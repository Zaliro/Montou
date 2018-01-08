package com.montou.game.engine;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		JButton bNewGame = new JButton("New game");
		bNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContentPane(panelPlugins);
				revalidate();
			}
		});
		bNewGame.setBounds(135, 97, 196, 61);
			
		JButton bContinue = new JButton("Continue last game");
		bContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
