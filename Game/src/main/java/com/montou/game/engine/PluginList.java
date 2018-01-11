package com.montou.game.engine;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.montou.game.plugins.Plugin;
import com.montou.game.plugins.PluginType;
import com.montou.game.shared.GameInformations;

class PluginsList extends JPanel {

	JList<Plugin> listAll;
	JList<Plugin> listP1;
	JList<Plugin> listP2;
	JList<Plugin> listGraph;
	DefaultListModel<Plugin> listAllPlugin;
	DefaultListModel<Plugin> listGraphPlugin;
	DefaultListModel<Plugin> listP1Plugin;
	DefaultListModel<Plugin> listP2Plugin;
	JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	Plugin selectedAll = null;
	Plugin selectedGraph = null;
	Plugin selectedP1 = null;
	Plugin selectedP2 = null;
	Boolean p1HasMovement = false;
	Boolean p2HasMovement = false;
	Boolean p1HasAttack = false;
	Boolean p2HasAttack = false;

	public PluginsList(List<Plugin> plugins, GameInformations gameInformations) {

		setBounds(0, 0, gameInformations.getWidth(), gameInformations.getHeight());
		setLayout(new GridLayout(8, 1));

		listAllPlugin = new DefaultListModel<Plugin>();
		listAll = new JList<Plugin>(listAllPlugin);
		listGraphPlugin = new DefaultListModel<Plugin>();
		listGraph = new JList<Plugin>(listGraphPlugin);
		listP1Plugin = new DefaultListModel<Plugin>();
		listP1 = new JList<Plugin>(listP1Plugin);
		listP2Plugin = new DefaultListModel<Plugin>();
		listP2 = new JList<Plugin>(listP2Plugin);
		
		// Improvements
		
		JPanel headerLabelPanel = new JPanel(new GridLayout(1, 1));
		JLabel headerLabel = new JLabel("Configuration des plugins");
		Font baseFont = headerLabel.getFont();
		headerLabel.setFont(new Font(baseFont.getName(), Font.BOLD, 25));
		headerLabel.setHorizontalAlignment(JLabel.CENTER);
		headerLabelPanel.add(headerLabel);
		
		add(headerLabelPanel);
		
		JPanel playersLabelsPanel = new JPanel(new GridLayout(1, 2));
		
		JLabel playerOnePluginsLabel = new JLabel("Joueur 1");
		playerOnePluginsLabel.setFont(new Font(baseFont.getName(), Font.ITALIC, 15));
		playerOnePluginsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel playerTwoPluginsLabel = new JLabel("Joueur 2");
		playerTwoPluginsLabel.setFont(new Font(baseFont.getName(), Font.ITALIC, 15));
		playerTwoPluginsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		playersLabelsPanel.add(playerOnePluginsLabel, BorderLayout.WEST);
		playersLabelsPanel.add(playerTwoPluginsLabel, BorderLayout.EAST);
		
		JPanel graphicsLabelPanel = new JPanel(new GridLayout(1, 1));
		JLabel graphicsPluginsLabel = new JLabel("Graphique(s)");
		graphicsPluginsLabel.setFont(new Font(baseFont.getName(), Font.ITALIC, 15));
		graphicsPluginsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		graphicsLabelPanel.add(graphicsPluginsLabel);
		
		// Improvements (End)

		JScrollPane panelAll = new JScrollPane(listAll);
		JScrollPane panelGraph = new JScrollPane(listGraph);
		JButton bRemGraph = new JButton("Supprimer");
		JPanel panelGraphContainer = new JPanel();
		panelGraphContainer.setLayout(new GridLayout(2, 1));
		JScrollPane panelp1 = new JScrollPane(listP1);
		JScrollPane panelp2 = new JScrollPane(listP2);
		JPanel players = new JPanel();
		JButton bAdd1 = new JButton("Ajouter");
		JButton bAdd2 = new JButton("Ajouter");
		JButton bRem1 = new JButton("Supprimer");
		JButton bRem2 = new JButton("Supprimer");
		JPanel buttonsPlayers = new JPanel();
		players.setLayout(new GridLayout(1, 2));

		JButton bStart = new JButton("Lancer");
		JButton bBack = new JButton("Retour");
		JPanel buttonsConfig = new JPanel();
		buttonsConfig.setLayout(new GridLayout(3, 1));
		buttonsPlayers.setLayout(new GridLayout(2, 2));

		for (Iterator iterator = plugins.iterator(); iterator.hasNext();) {
			Plugin plugin = (Plugin) iterator.next();
			listAllPlugin.addElement(plugin);
		}

		DefaultListSelectionModel m = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m.setLeadAnchorNotificationEnabled(false);
		listAll.setSelectionModel(m);
		DefaultListSelectionModel mg = new DefaultListSelectionModel();
		mg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mg.setLeadAnchorNotificationEnabled(false);
		listGraph.setSelectionModel(mg);
		DefaultListSelectionModel m1 = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m.setLeadAnchorNotificationEnabled(false);
		listP1.setSelectionModel(m1);
		DefaultListSelectionModel m2 = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m.setLeadAnchorNotificationEnabled(false);
		listP2.setSelectionModel(m2);

		// LISTENER LIST
		listAll.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boolean adjust = e.getValueIsAdjusting();
				if (!adjust) {
					JList<Plugin> tmpList = (JList<Plugin>) e.getSource();
					if (tmpList.getSelectedIndex() >= 0)
						selectedAll = listAllPlugin.getElementAt(tmpList.getSelectedIndex());
				}
			}
		});
		listGraph.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boolean adjust = e.getValueIsAdjusting();
				if (!adjust) {
					JList tmpList = (JList) e.getSource();
					if (tmpList.getSelectedIndex() >= 0)
						selectedGraph = listGraphPlugin.getElementAt(tmpList.getSelectedIndex());
				}
			}
		});
		listP1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boolean adjust = e.getValueIsAdjusting();
				if (!adjust) {
					JList<Plugin> tmpList = (JList<Plugin>) e.getSource();
					if (tmpList.getSelectedIndex() >= 0)
						selectedP1 = listP1Plugin.getElementAt(tmpList.getSelectedIndex());
				}
			}
		});
		listP2.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boolean adjust = e.getValueIsAdjusting();
				if (!adjust) {
					JList<Plugin> tmpList = (JList<Plugin>) e.getSource();
					if (tmpList.getSelectedIndex() >= 0)
						selectedP2 = listP2Plugin.getElementAt(tmpList.getSelectedIndex());
				}
			}
		});

		// LISTENER BOUTTONS
		bAdd1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean exist = false;
				if (selectedAll != null) {
					if (selectedAll.getType() == PluginType.GRAPHIC) {
						for (int i = 0; i < listGraphPlugin.size(); i++) {
							Plugin p = listGraphPlugin.getElementAt(i);
							if (p == selectedAll) {
								exist = true;
							}
						}
					} else {
						for (int i = 0; i < listP1Plugin.size(); i++) {
							Plugin p = listP1Plugin.getElementAt(i);
							if (p == selectedAll) {
								exist = true;
							}
						}
					}
				}
				if (!exist) {
					if (selectedAll.getType() == PluginType.ATTACK) {
						if (!p1HasAttack) {
							listP1Plugin.addElement(selectedAll);
							p1HasAttack = true;
						}
					} else if (selectedAll.getType() == PluginType.MOVEMENT) {
						if (!p1HasMovement) {
							listP1Plugin.addElement(selectedAll);
							p1HasMovement = true;
						}
					} else if (selectedAll.getType() == PluginType.GRAPHIC) {
						listGraphPlugin.addElement(selectedAll);
					}
				}
			}
		});
		bAdd2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean exist = false;
				if (selectedAll != null) {
					if (selectedAll.getType() == PluginType.GRAPHIC) {
						for (int i = 0; i < listGraphPlugin.size(); i++) {
							Plugin p = listGraphPlugin.getElementAt(i);
							if (p == selectedAll) {
								exist = true;
							}
						}
					} else {
						for (int i = 0; i < listP2Plugin.size(); i++) {
							Plugin p = listP2Plugin.getElementAt(i);
							if (p == selectedAll) {
								exist = true;
							}
						}
					}
				}
				if (!exist) {
					if (selectedAll.getType() == PluginType.ATTACK) {
						if (!p2HasAttack) {
							listP2Plugin.addElement(selectedAll);
							p2HasAttack = true;
						}
					} else if (selectedAll.getType() == PluginType.MOVEMENT) {
						if (!p2HasMovement) {
							listP2Plugin.addElement(selectedAll);
							p2HasMovement = true;
						}
					} else if (selectedAll.getType() == PluginType.GRAPHIC) {
						listGraphPlugin.addElement(selectedAll);
					}
				}
			}
		});
		bRem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean exist = false;
				if (selectedP1 != null) {
					for (int i = 0; i < listP1Plugin.size(); i++) {
						Plugin p = listP1Plugin.getElementAt(i);
						if (p == selectedP1) {
							exist = true;
						}
					}
				}
				if (exist) {
					if (selectedP1.getType() == PluginType.ATTACK) {
						p1HasAttack = false;
					} else if (selectedP1.getType() == PluginType.MOVEMENT) {
						p1HasMovement = false;
					}
					listP1Plugin.removeElement(selectedP1);
				}
			}
		});
		bRem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean exist = false;
				if (selectedP2 != null) {
					for (int i = 0; i < listP2Plugin.size(); i++) {
						Plugin p = listP2Plugin.getElementAt(i);
						if (p == selectedP2) {
							exist = true;
						}
					}
				}
				if (exist) {
					if (selectedP2.getType() == PluginType.ATTACK) {
						p2HasAttack = false;
					} else if (selectedP2.getType() == PluginType.MOVEMENT) {
						p2HasMovement = false;
					}
					listP2Plugin.removeElement(selectedP2);
				}
			}
		});
		bRemGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean exist = false;
				if (selectedGraph != null) {
					for (int i = 0; i < listGraphPlugin.size(); i++) {
						Plugin p = listGraphPlugin.getElementAt(i);
						if (p == selectedGraph) {
							exist = true;
						}
					}
				}
				if (exist) {
					listGraphPlugin.removeElement(selectedGraph);
				}
			}
		});
		bStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});

		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});

		// FIN LISTENER
		add(panelAll);
		
		// Improvements
		add(playersLabelsPanel);
		
		players.add(panelp1, BorderLayout.LINE_START);
		players.add(panelp2, BorderLayout.LINE_END);
		add(players);

		buttonsPlayers.add(bAdd1);
		buttonsPlayers.add(bAdd2);
		buttonsPlayers.add(bRem1);
		buttonsPlayers.add(bRem2);
		add(buttonsPlayers);
		
		// Improvements
		add(graphicsLabelPanel);
		
		panelGraphContainer.add(panelGraph);
		panelGraphContainer.add(bRemGraph);
		add(panelGraphContainer);
		buttonsConfig.add(new JPanel());
		buttonsConfig.add(bStart);
		buttonsConfig.add(bBack);
		add(buttonsConfig);
	}

	public void goBack() {
		MenuFrame frame = (MenuFrame) SwingUtilities.getWindowAncestor(this);
		frame.goBack();
	}

	public void start() {
		MenuFrame frame = (MenuFrame) SwingUtilities.getWindowAncestor(this);
		frame.start();
	}

	public List<Plugin> getPluginP1() {
		List<Plugin> tmp = new ArrayList<>();
		if (listP1Plugin.size() != 0) {
			for (int i = 0; i < listP1Plugin.size(); i++) {
				Plugin p = listP1Plugin.getElementAt(i);
				tmp.add(p);
			}
		}
		return tmp;
	}
	
	public List<Plugin> getPluginP2() {
		List<Plugin> tmp = new ArrayList<>();
		if (listP2Plugin.size() != 0) {
			for (int i = 0; i < listP2Plugin.size(); i++) {
				Plugin p = listP2Plugin.getElementAt(i);
				tmp.add(p);
			}
		}
		return tmp;
	}
	
	public List<Plugin> getPluginGraph() {
		List<Plugin> tmp = new ArrayList<>();
		if (listGraphPlugin.size() != 0) {
			for (int i = 0; i < listGraphPlugin.size(); i++) {
				Plugin p = listGraphPlugin.getElementAt(i);
				tmp.add(p);
			}
		}
		return tmp;
	}
}