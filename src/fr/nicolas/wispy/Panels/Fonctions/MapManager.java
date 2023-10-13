package fr.nicolas.wispy.Panels.Fonctions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.nicolas.wispy.Panels.Components.Game.Block;
import fr.nicolas.wispy.Panels.Components.Game.Player;
import fr.nicolas.wispy.Runner;
import fr.nicolas.wispy.Panels.GamePanel;
import fr.nicolas.wispy.Panels.GamePanel.BlockID;

public class MapManager {

	private int mapBLeftNum = -1, mapBCenterNum = 0, mapBRightNum = 1;
	private Block[][] mapBLeft, mapBRight, mapBCenter;
	private String worldName;
	private Player player;
	private boolean hasFoundFallingCollision = false, hasFoundUpCollision = false, hasFoundRightCollision = false,
			hasFoundLeftCollision = false;

	// Random generation variables
	private int state = 0, changeStateNum = 5, currentNum = 0, lastY = 10, lastState = 0;

	public enum RefreshPaintMap {
		PAINT, COLLISION, SELECTION;
	}

	public MapManager(Player player) {
		this.player = player;
	}

	public void loadWorld(String worldName) {
		this.worldName = worldName;
		if (!new File("Wispy/worlds/" + worldName).exists()) {
			new File("Wispy/worlds/" + worldName).mkdirs();

			// Génération des maps
			int mapSize = 2; // Nombre de maps crées à gauche et à droite des 3 maps centrales
								// Ainsi: nombre total de map = mapSize*2+3

			mapBLeftNum = -1;
			mapBLeft = generateMap(mapBLeftNum);
			saveMap(mapBLeft, mapBLeftNum);

			mapBCenterNum = 0;
			mapBCenter = generateMap(mapBCenterNum);
			saveMap(mapBCenter, mapBCenterNum);

			mapBRightNum = 1;
			mapBRight = generateMap(mapBRightNum);
			saveMap(mapBRight, mapBRightNum);

			for (int i = -2; i >= -mapSize - 1; i--) {
				saveMap(generateMap(i), i);
			}

			for (int i = 2; i <= mapSize + 1; i++) {
				saveMap(generateMap(i), i);
			}

		} else {
			// Chargement des maps (pour l'instant les 3 principales mais TODO: système pour
			// charger les coords du joueurs pour charger les 3 maps par rapport à sa
			// position)

			mapBLeftNum = -1;
			mapBLeft = loadMap(mapBLeftNum);

			mapBCenterNum = 0;
			mapBCenter = loadMap(mapBCenterNum);

			mapBRightNum = 1;
			mapBRight = loadMap(mapBRightNum);

		}

		// Player spawnpoint
		player.x = 0;
		player.y = getPlayerSpawnY();
	}

	public int getPlayerSpawnY() {
		// TODO: Système à refaire
		int y = 0;
		while (mapBCenter[0][y] == null) {
			y++;
		}
		return y;
	}

	public void newLoadingMapThread(Runner runner, GamePanel gamePanel) {
		Thread loadNextMap = new Thread(new Runnable() {

			private void saveMap(Block[][] mapToSave, int num) {
				try {
					ObjectOutputStream objectOutputS = new ObjectOutputStream(
							new FileOutputStream("Wispy/worlds/" + worldName + "/" + num + ".wmap"));
					objectOutputS.writeObject(mapToSave);
					objectOutputS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			private Block[][] loadMap(int num) {
				Block[][] loadedMap = null;
				try {
					ObjectInputStream objectInputS = new ObjectInputStream(
							new FileInputStream("Wispy/worlds/" + worldName + "/" + num + ".wmap"));
					try {
						loadedMap = (Block[][]) objectInputS.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					objectInputS.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return loadedMap;
			}

			public void run() {
				while (true) {
					int newX;
					if (mapBRight != null) {
						newX = ((mapBRight.length / 2 + mapBRightNum * mapBRight.length) * gamePanel.getNewBlockWidth())
								- (int) (player.getX() * gamePanel.getNewBlockWidth() / GamePanel.BLOCK_SIZE);
						if (newX >= 0 && newX <= gamePanel.getWidth()) {
							saveMap(mapBLeft, mapBLeftNum);
							mapBLeft = mapBCenter;
							mapBLeftNum = mapBCenterNum;
							mapBCenter = mapBRight;
							mapBCenterNum = mapBRightNum;

							if (new File("Wispy/worlds/" + worldName + "/" + (mapBRightNum + 1) + ".wmap").exists()) {
								mapBRightNum++;
								mapBRight = loadMap(mapBRightNum);
							} else {
								mapBRight = null;
							}
						}
					}
					if (mapBLeft != null) {
						newX = ((mapBLeft.length / 2 + mapBLeftNum * mapBLeft.length) * gamePanel.getNewBlockWidth())
								- (int) (player.getX() * gamePanel.getNewBlockWidth() / GamePanel.BLOCK_SIZE);
						if (newX >= 0 && newX <= gamePanel.getWidth()) {
							saveMap(mapBRight, mapBRightNum);
							mapBRight = mapBCenter;
							mapBRightNum = mapBCenterNum;
							mapBCenter = mapBLeft;
							mapBCenterNum = mapBLeftNum;

							if (new File("Wispy/worlds/" + worldName + "/" + (mapBLeftNum - 1) + ".wmap").exists()) {
								mapBLeftNum--;
								mapBLeft = loadMap(mapBLeftNum);
							} else {
								mapBLeft = null;
							}

						}

					}

					int waitTime = (int) runner.getWaitTime();
					if (waitTime < 4) {
						try {
							Thread.sleep(4);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						try {
							Thread.sleep(waitTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		loadNextMap.start();
	}

	private void saveMap(Block[][] mapToSave, int num) {
		try {
			ObjectOutputStream objectOutputS = new ObjectOutputStream(
					new FileOutputStream("Wispy/worlds/" + worldName + "/" + num + ".wmap"));
			objectOutputS.writeObject(mapToSave);
			objectOutputS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Block[][] loadMap(int num) {
		Block[][] loadedMap = null;
		try {
			ObjectInputStream objectInputS = new ObjectInputStream(
					new FileInputStream("Wispy/worlds/" + worldName + "/" + num + ".wmap"));
			try {
				loadedMap = (Block[][]) objectInputS.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			objectInputS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loadedMap;
	}

	private Block[][] generateMap(int num) {
		Block[][] mapToGenerate = new Block[82][100];

		int newY = 0;

		for (int x = 0; x < mapToGenerate.length; x++) {
			// TODO: Algo de génération
			// TODO: Actuellement: génération map par map au lieu de l'ensemble des maps
			// d'un coup ... (problèmes si grottes, montagnes, bâtiments ...)

			if (random(1, 2) == 1) {
				if (state == 0) {
					newY = random(lastY - 1, lastY + 1);
				} else if (state == 1) {
					if (random(1, 3) == 1) {
						newY = random(lastY, lastY + 3);
					} else {
						newY = random(lastY, lastY + 2);
					}
				} else if (state == 2) {
					if (random(1, 3) == 1) {
						newY = random(lastY, lastY - 3);
					} else {
						newY = random(lastY, lastY - 2);
					}
				}
			} else {
				newY = lastY;
			}

			if (currentNum == changeStateNum) {
				currentNum = 0;
				changeStateNum = random(3, 7);

				if (random(1, 3) == 1) {
					state = random(1, 2);
				} else {
					state = 0;
				}

				if (lastState == state) {
					if (random(1, 3) != 1) {
						if (state == 1) {
							state = 2;
						} else if (state == 2) {
							state = 1;
						}
					}
				}

				lastState = state;

			} else {
				currentNum++;
			}

			if (newY > 25) { // Profondeur y max
				newY = 25;
			} else if (newY < 10) { // Hauteur y max
				newY = 10;
			}

			lastY = newY;
			mapToGenerate[x][newY] = new Block(BlockID.GRASS);

			for (int y = newY + 1; y < mapToGenerate[0].length; y++) {

				if (y < newY + 3) {
					mapToGenerate[x][y] = new Block(BlockID.DIRT);
				} else {
					mapToGenerate[x][y] = new Block(BlockID.STONE);
				}

			}

		}

		return mapToGenerate;

	}

	private int random(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	public void refreshPaintAllDisplayedBlocks(Graphics g, RefreshPaintMap mode, int width, int height,
			int newBlockWidth, int newBlockHeight, int playerX, int playerY, int playerWidth, int playerHeight,
			GamePanel gamePanel, Point mouseLocation) {
		// Si mode:
		// = 1: Paint
		// = 2: Test collisions
		// = 3: Block selection
		refreshPaintMapDisplayedBlocks(g, mode, mapBCenter, mapBCenterNum, width, height, newBlockWidth, newBlockHeight,
				gamePanel, playerWidth, playerHeight, playerX, playerY, mouseLocation);
		refreshPaintMapDisplayedBlocks(g, mode, mapBLeft, mapBLeftNum, width, height, newBlockWidth, newBlockHeight,
				gamePanel, playerWidth, playerHeight, playerX, playerY, mouseLocation);
		refreshPaintMapDisplayedBlocks(g, mode, mapBRight, mapBRightNum, width, height, newBlockWidth, newBlockHeight,
				gamePanel, playerWidth, playerHeight, playerX, playerY, mouseLocation);

		hasFoundFallingCollision = false;
		hasFoundUpCollision = false;
		hasFoundRightCollision = false;
		hasFoundLeftCollision = false;
	}

	// TODO: Fonction a réorganiser
	private void refreshPaintMapDisplayedBlocks(Graphics g, RefreshPaintMap mode, Block[][] mapB, int times, int width,
			int height, int newBlockWidth, int newBlockHeight, GamePanel gamePanel, int playerWidth, int playerHeight,
			int playerX, int playerY, Point mouseLocation) {
		// Voir modes dans la fonction "refreshPaintAllDisplayedBlocks"

		if (mapB != null) {
			for (int x = 0; x < mapB.length; x++) {
				int newX = ((x + times * mapB.length) * newBlockWidth)
						- (int) (player.getX() / GamePanel.BLOCK_SIZE * newBlockWidth);
				if (newX >= -350 && newX <= width + 350) {
					for (int y = 0; y < mapB[0].length; y++) {
						int newY = (y * newBlockHeight) - (int) (player.getY() / GamePanel.BLOCK_SIZE * newBlockHeight);
						if (newY >= -350 && newY <= height + 350) {
							if (mapB[x][y] != null) {
								if (mode == RefreshPaintMap.PAINT) {
									// Paint
									mapB[x][y].paint(g, newX, newY, newBlockWidth, newBlockHeight);
								} else if (mode == RefreshPaintMap.COLLISION) {
									// Test des collisions avec le joueur
									if (!hasFoundFallingCollision) {
										if (new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
												.contains(new Point(playerX, playerY + playerHeight))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight).contains(
														new Point(playerX + playerWidth - 1, playerY + playerHeight))) {

											gamePanel.getPlayer().setFalling(false);
											hasFoundFallingCollision = true;

										} else {
											gamePanel.getPlayer().setFalling(true);
										}
									}
									if (!hasFoundUpCollision) {
										if (new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
												.contains(new Point(playerX, playerY - 1))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
														.contains(new Point(playerX + playerWidth - 1, playerY - 1))) {

											gamePanel.getPlayer().setCanGoUp(false);
											hasFoundUpCollision = true;

										} else {
											gamePanel.getPlayer().setCanGoUp(true);
										}
									}
									if (!hasFoundRightCollision) {
										if (new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
												.contains(new Point(playerX + playerWidth, playerY))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight).contains(
														new Point(playerX + playerWidth, playerY + playerHeight - 1))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight).contains(
														new Point(playerX + playerWidth, playerY + playerHeight / 2))) {

											gamePanel.getPlayer().setCanGoRight(false);
											hasFoundRightCollision = true;

										} else {
											gamePanel.getPlayer().setCanGoRight(true);
										}
									}
									if (!hasFoundLeftCollision) {
										if (new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
												.contains(new Point(playerX - 1, playerY))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
														.contains(new Point(playerX - 1, playerY + playerHeight - 1))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
														.contains(new Point(playerX - 1, playerY + playerHeight / 2))) {

											gamePanel.getPlayer().setCanGoLeft(false);
											hasFoundLeftCollision = true;

										} else {
											gamePanel.getPlayer().setCanGoLeft(true);
										}
									}
								} else if (mode == RefreshPaintMap.SELECTION) {
									// Block selection
									if (new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
											.contains(mouseLocation)) {
										g.setColor(new Color(255, 255, 255, 50));
										g.drawRect(newX, newY, newBlockWidth, newBlockHeight);
									}
								}
							}
						} else if (newY > 0) {
							break;
						}
					}
				} else if (newX > 0) {
					break;
				}
			}
		}
	}

}
