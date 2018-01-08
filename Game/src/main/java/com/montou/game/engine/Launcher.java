package com.montou.game.engine;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import com.montou.game.entities.Robot;
import com.montou.game.persistence.PersistenceManager;
import com.montou.game.plugins.Plugin;
import com.montou.game.plugins.PluginType;
import com.montou.game.plugins.PluginsLoader;
import com.montou.game.plugins.annotations.AAttack;
import com.montou.game.plugins.annotations.AMovement;
import com.montou.game.shared.Direction;
import com.montou.game.shared.GameInformations;

public class Launcher {

	private static GameInformations gameInformations;

	private static PluginsLoader pluginsLoader;
	private static List<Plugin> loadedPlugins;

	private static GridFrame mainFrame;

	// Paths
	private static File pluginsJarPath;
	private static File persistenceDirPath;

	// Constants
	private static final int WIDTH = 480;
	private static final int HEIGHT = 480;
	private static final int LINES = 16;
	private static final int COLS = 16;

	private static final int BASE_P1_LINE = 1;
	private static final int BASE_P2_LINE = 1;
	private static final int BASE_P1_COL = 1;
	private static final int BASE_P2_COL = 2;

	private static final long TIME_BETWEEN_TURN = 1500;
	private static final double ENERGY_REGEN_RATE = 5;

	@SuppressWarnings("incomplete-switch")
	public static void main(String[] args)
			throws NoSuchMethodException, SecurityException, InvocationTargetException, InterruptedException,
			ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InstantiationException {

		if (args.length == 2) {
			// Recuperation des chemins depuis les arguments de lancement...
			pluginsJarPath = new File(args[0]);
			persistenceDirPath = new File(args[1]);

			// Nous verifions la validite des chemins...
			if (pluginsJarPath.exists() && !pluginsJarPath.isDirectory()) {
				if (persistenceDirPath.exists() && persistenceDirPath.isDirectory()) {

					// Chargement des plugins...
					pluginsLoader = new PluginsLoader(pluginsJarPath.getAbsolutePath());
					loadedPlugins = pluginsLoader.loadPlugins();

					// Initialisation de la partie...
					if (!mustRecoverLastGame()) {
						// Si la derniere partie a ete terminee...
						System.out.println("Initialisation d'une nouvelle partie...");

						// Initialisation des joueurs...

						Robot playerOne = new Robot("P1", BASE_P1_LINE, BASE_P1_COL);
						Robot playerTwo = new Robot("P2", BASE_P2_LINE, BASE_P2_COL);

						gameInformations = new GameInformations(WIDTH, HEIGHT, LINES, COLS, playerOne, playerTwo);
					} else {
						// Si la derniere partie a ete arrete de maniere innatendue avant la fin...
						System.out.println("Recuperation de la derniere partie...");
						gameInformations = getLastSavedGameInformations();

						// Nous récupérons également l'état des plugins le nécessitant...
						List<Plugin> pluginsToRecover = loadedPlugins.stream().filter(x -> x.useCustomData())
								.collect(Collectors.toList());

						for (Plugin p : pluginsToRecover) {
							Method onLoadMethod = p.getClazz().getMethod("onLoad", Object[].class);
							if (onLoadMethod != null) {
								Object objectsToInject = getLastSavedPluginInformations(p);
								// Injection des donnees chargees...
								onLoadMethod.invoke(p.getInstance(), objectsToInject);
							}
						}
					}


					System.out.println("Tâche terminée.");


					MenuFrame frame = new MenuFrame("RobotWar - Menu", gameInformations, loadedPlugins);
					frame.setVisible(true);
					Thread t = new Thread(new Runnable() {
						public void run() {
							while(!frame.canStart()) {
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					});
					t.start();
					t.join();
					frame.setVisible(false);
					List<Plugin> p1Plugins = frame.getP1Plugins();
					List<Plugin> p2Plugins = frame.getP2Plugins();
					List<Plugin> graphPlugins = frame.getGraphPlugins();
					
					// Initialisation de l'environnement...
					mainFrame = new GridFrame(String.format("RobotWar - %s plugin(s) charge(s).", loadedPlugins.size()),
							gameInformations, graphPlugins);
					mainFrame.setVisible(true);

					// Deroulement de la partie, jusqu'a ce qu'un des robots ne soit plus actif...
					int turnCounter = 1;
					while (!gameInformations.isFinished()) {
						System.out.println(String.format("execution du tour %s...", turnCounter));

						// Les deux robots Bougent
						List<Plugin> currentPlugins;
						Robot currentPlayer;
						Robot opponent;
						for (int i = 1; i <= 2; i++) {
							if(i == 1) {
								currentPlayer = gameInformations.getPlayerOne();
								opponent = gameInformations.getPlayerTwo();
								currentPlugins = p1Plugins;
							}else {
								currentPlayer = gameInformations.getPlayerTwo();
								opponent = gameInformations.getPlayerOne();
								currentPlugins = p2Plugins;
							}
							for (Plugin p : currentPlugins) {
								try {
									if (p.getType() == PluginType.MOVEMENT) {
										// Si c'est un plugin concernant la gestion des mouvements..
										Method moveMethod = p.getClazz().getMethod("move", GameInformations.class,
												int.class);
										if (moveMethod != null) {

											// Nous soutrayons l'energie au joueur courant...
											int energyCost = ((AMovement) p.getClazz().getAnnotation(AMovement.class))
													.energyCost();
											// Nous verifions si le joueur dispose d'assez d'energie...
											if ((currentPlayer.getEnergyPoints() - energyCost) >= 0) {
												// Nous retirons l'energie au joueur...
												currentPlayer.substractEnergyPoints(energyCost);

												// Nous faisons bouger le joueur...
												Direction desiredDirection = (Direction) moveMethod
														.invoke(p.getInstance(), gameInformations, i);
												switch (desiredDirection) {
												case UP:
													int nextLineUp = currentPlayer.getCurrentLine() - 1;
													if(nextLineUp < 1) {
														currentPlayer.setCurrentLine(1);
													}
													else if (nextLineUp != opponent.getCurrentLine()) {
														currentPlayer.setCurrentLine(nextLineUp);
													}
													break;

												case DOWN:
													int nextLineDown = currentPlayer.getCurrentLine() + 1;
													if(nextLineDown > LINES) {
														currentPlayer.setCurrentLine(LINES);
													}
													else if (nextLineDown != opponent.getCurrentLine()) {
														currentPlayer.setCurrentLine(nextLineDown);
													}
													break;

												case LEFT:
													int nextColLeft = currentPlayer.getCurrentColumn() - 1;
													if(nextColLeft < 1) {
														currentPlayer.setCurrentColumn(1);
													}
													else if (nextColLeft != opponent.getCurrentColumn()) {
														currentPlayer.setCurrentColumn(nextColLeft);
													}
													
													break;

												case RIGHT:
													int nextColRight = currentPlayer.getCurrentColumn() + 1;
													if(nextColRight > COLS) {
														currentPlayer.setCurrentColumn(COLS);
													}
													else if (nextColRight != opponent.getCurrentColumn()) {
														currentPlayer.setCurrentColumn(nextColRight);
													}
													
													break;
												}
											}
										}
									}
								
							}catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
						}
						
							
						// Les deux robots Attaquent
								
								for (int i = 1; i <= 2; i++) {
									if(i == 1) {
										currentPlayer = gameInformations.getPlayerOne();
										opponent = gameInformations.getPlayerTwo();
										currentPlugins = p1Plugins;
									}else {
										currentPlayer = gameInformations.getPlayerTwo();
										opponent = gameInformations.getPlayerOne();
										currentPlugins = p2Plugins;
									}
									for (Plugin p : currentPlugins) {
										if(p.getType() == PluginType.ATTACK) {
											// Si c'est un plugin concernant la gestion des attaques...
											Method attackMethod = p.getClazz().getMethod("attack", GameInformations.class);
	
											if(attackMethod!=null){
												// Nous soutrayons l'ï¿½nergie au joueur courant...
												int energyCost = ((AAttack) p.getClazz().getAnnotation(AAttack.class)).energyCost();
												// Nous vï¿½rifions si le joueur dispose d'assez d'ï¿½nergie...
	
												if ((currentPlayer.getEnergyPoints() - energyCost) >= 0) {
													// Nous retirons l'ï¿½nergie au joueur...
													currentPlayer.substractEnergyPoints(energyCost);
	
													// Nous executons l'attaque du joueur courant et nous retirons des points de vie a son opposant
													opponent.substractLifePoints((int)attackMethod.invoke(p.getInstance(), gameInformations));
												}
											}
										}
									}
						/*
						for (int i = 1; i <= 2; i++) {
							Robot currentPlayer = (i == 1 ? gameInformations.getPlayerOne()
									: gameInformations.getPlayerTwo());
							Robot opponent = (i == 1 ? gameInformations.getPlayerTwo()
									: gameInformations.getPlayerOne());

							// Nous actionons les plugins ï¿½ chaque tour...
							for (Plugin p : actionsPlugins) {
								try {
									switch (p.getType()) {
									case MOVEMENT:
										// Si c'est un plugin concernant la gestion des mouvements..
										Method moveMethod = p.getClazz().getMethod("move", GameInformations.class,
												int.class);
										if (moveMethod != null) {

											// Nous soutrayons l'ï¿½nergie au joueur courant...
											int energyCost = ((AMovement) p.getClazz().getAnnotation(AMovement.class))
													.energyCost();
											// Nous vï¿½rifions si le joueur dispose d'assez d'ï¿½nergie...
											if ((currentPlayer.getEnergyPoints() - energyCost) >= 0) {
												// Nous retirons l'ï¿½nergie au joueur...
												currentPlayer.substractEnergyPoints(energyCost);

												// Nous faisons bouger le joueur...
												Direction desiredDirection = (Direction) moveMethod
														.invoke(p.getInstance(), gameInformations, i);
												switch (desiredDirection) {
												case UP:
													int nextLineUp = currentPlayer.getCurrentLine() - 1;
													if(nextLineUp < 1) {
														currentPlayer.setCurrentLine(1);
													}
													else if (nextLineUp != opponent.getCurrentLine()) {
														currentPlayer.setCurrentLine(nextLineUp);
													}
													break;

												case DOWN:
													int nextLineDown = currentPlayer.getCurrentLine() + 1;
													if(nextLineDown > LINES) {
														currentPlayer.setCurrentLine(LINES);
													}
													else if (nextLineDown != opponent.getCurrentLine()) {
														currentPlayer.setCurrentLine(nextLineDown);
													}
													break;

												case LEFT:
													int nextColLeft = currentPlayer.getCurrentColumn() - 1;
													if(nextColLeft < 1) {
														currentPlayer.setCurrentColumn(1);
													}
													else if (nextColLeft != opponent.getCurrentColumn()) {
														currentPlayer.setCurrentColumn(nextColLeft);
													}
													
													break;

												case RIGHT:
													int nextColRight = currentPlayer.getCurrentColumn() + 1;
													if(nextColRight > COLS) {
														currentPlayer.setCurrentColumn(COLS);
													}
													else if (nextColRight != opponent.getCurrentColumn()) {
														currentPlayer.setCurrentColumn(nextColRight);
													}
													
													break;
												}
											}
										}
										break;

									case ATTACK:
										// Si c'est un plugin concernant la gestion des attaques...
										Method attackMethod = p.getClazz().getMethod("attack", GameInformations.class);

										if(attackMethod!=null){
											// Nous soutrayons l'ï¿½nergie au joueur courant...
											int energyCost = ((AAttack) p.getClazz().getAnnotation(AAttack.class)).energyCost();
											// Nous vï¿½rifions si le joueur dispose d'assez d'ï¿½nergie...

											if ((currentPlayer.getEnergyPoints() - energyCost) >= 0) {
												// Nous retirons l'ï¿½nergie au joueur...
												currentPlayer.substractEnergyPoints(energyCost);

												// Nous executons l'attaque du joueur courant et nous retirons des points de vie a son opposant
												opponent.substractLifePoints((int)attackMethod.invoke(p.getInstance(), gameInformations));
											}
										}
										break;
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								*/
								// Regeneration des points d'energie...
								currentPlayer.regen(ENERGY_REGEN_RATE);

								// Affichage d'un recapitulatif des informations du joueur pour une meilleure
								// visibilite...
								System.out.println(currentPlayer.toString());
							}
						

						// Nous verifions si la partie est terminee...
						int gameStatus = checkGameStatus();
						if (gameStatus != 0) {
							gameInformations.finish(gameStatus);
						}

						// Nous rafraichissons la frame...
						mainFrame.repaint();


						// Sauvegarde de l'etat de la partie...

						saveTurn();

						Thread.sleep(TIME_BETWEEN_TURN);
						turnCounter += 1;
					}
				} else {
					System.out.println(String
							.format("Il semblerait que l'argument 'persistenceDirPath' soit invalide : %s !", args[1]));
				}
			} else {
				System.out.println(
						String.format("Il semblerait que l'argument 'pluginsJarPath' soit invalide : %s !", args[0]));
			}

		} else {
			System.out
					.println("Arguments de lancements incorrects : java Launcher -pluginsJarPath -persistenceDirPath");
		}
	}

	// -- Gestion du statut de la partie --

	public static int checkGameStatus() {
		// StatusCode : 0 = IN_PROGRESS, 1 = PLAYER1_WIN, 2 = PLAYER2_WIN, 3 =
		// ZERO_MATCH.
		int statusCode = -1;

		if (gameInformations.getPlayerOne().isActive() && gameInformations.getPlayerTwo().isActive()) {
			statusCode = 0;
		} else if (gameInformations.getPlayerOne().isActive() && !gameInformations.getPlayerTwo().isActive()) {
			statusCode = 1;
		} else if (!gameInformations.getPlayerOne().isActive() && gameInformations.getPlayerTwo().isActive()) {
			statusCode = 2;
		} else if (!gameInformations.getPlayerOne().isActive() && !gameInformations.getPlayerTwo().isActive()) {
			statusCode = 3;
		}

		return statusCode;
	}

	// -- Gestion de la sauvegarde --

	// Lecture...

	public static boolean mustRecoverLastGame() {
		boolean recoverLastGame = false;

		GameInformations lastSavedGameInformations = getLastSavedGameInformations();
		if (lastSavedGameInformations != null) {
			recoverLastGame = !lastSavedGameInformations.isFinished();
		}

		return recoverLastGame;
	}

	public static GameInformations getLastSavedGameInformations() {
		return (GameInformations) PersistenceManager.load(persistenceDirPath.getAbsolutePath(),
				PersistenceManager.GAME_INFORMATIONS_FILE);
	}

	public static Object getLastSavedPluginInformations(Plugin plugin) {
		return PersistenceManager.load(persistenceDirPath.getAbsolutePath(),
				String.format("Plugin_%s.save", plugin.getClazz().getSimpleName()));
	}

	// Ecriture...

	public static void saveTurn() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {
		// Sauvegarde de l'etat generale de la partie...
		PersistenceManager.save(persistenceDirPath.getAbsolutePath(), gameInformations);

		// Racuparation de la liste des plugins necessitant une sauvegarde...
		List<Plugin> pluginsToSave = loadedPlugins.stream().filter(x -> x.useCustomData()).collect(Collectors.toList());
		for (Plugin p : pluginsToSave) {
			Method onSaveMethod = p.getClazz().getMethod("onSave");
			if (onSaveMethod != null) {
				Object[] objectsToSave = (Object[]) onSaveMethod.invoke(p.getInstance());
				// Sauvegarde des donnees retournees par le plugin dans un fichier a son
				// nom...
				PersistenceManager.save(persistenceDirPath.getAbsolutePath(),
						String.format("Plugin_%s.save", p.getClazz().getSimpleName()), objectsToSave);
			}
		}
	}
}
