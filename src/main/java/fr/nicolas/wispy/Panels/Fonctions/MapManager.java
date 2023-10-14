package fr.nicolas.wispy.Panels.Fonctions;

import fr.nicolas.wispy.Panels.InGameMap;
import xyz.someboringnerd.wispy.Entities.PlayerEntity;
import xyz.someboringnerd.wispy.content.Block;
import xyz.someboringnerd.wispy.content.Map;

import java.awt.*;

public class MapManager {

	private int mapBLeftNum = -1, mapBCenterNum = 0, mapBRightNum = 1;
	private Block[][] mapBLeft, mapBRight, mapBCenter;
	private String worldName;
	private PlayerEntity player;
	private boolean hasFoundFallingCollision = false, hasFoundUpCollision = false, hasFoundRightCollision = false,
			hasFoundLeftCollision = false;

	// Random generation variables
	private int state = 0, changeStateNum = 5, currentNum = 0, lastY = 10, lastState = 0;

	public enum RefreshPaintMap {
		PAINT, COLLISION, SELECTION;
	}

	public MapManager(PlayerEntity player) {
		this.player = player;
	}

	public void loadWorld(String worldName)
	{

		player.x = 0;
		player.y = getPlayerSpawnY();
	}

	public int getPlayerSpawnY() {
		// TODO: Syst�me � refaire
		int y = 0;
		while (mapBCenter[0][y] == null) {
			y++;
		}
		return y;
	}

	private Map generateMap(int num)
	{
		return new Map();
	}

	private int random(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	public void refreshPaintAllDisplayedBlocks(Graphics g, RefreshPaintMap mode, int width, int height,
											   int newBlockWidth, int newBlockHeight, int playerX, int playerY, int playerWidth, int playerHeight,
											   InGameMap gamePanel, Point mouseLocation) {
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

	// TODO: Fonction a r�organiser
	private void refreshPaintMapDisplayedBlocks(Graphics g, RefreshPaintMap mode, Block[][] mapB, int times, int width,
												int height, int newBlockWidth, int newBlockHeight, InGameMap gamePanel, int playerWidth, int playerHeight,
												int playerX, int playerY, Point mouseLocation) {
		// Voir modes dans la fonction "refreshPaintAllDisplayedBlocks"

		if (mapB != null) {
			for (int x = 0; x < mapB.length; x++) {
				int newX = ((x + times * mapB.length) * newBlockWidth)
						- (int) (player.getX() / InGameMap.BLOCK_SIZE * newBlockWidth);
				if (newX >= -350 && newX <= width + 350) {
					for (int y = 0; y < mapB[0].length; y++) {
						int newY = (y * newBlockHeight) - (int) (player.getY() / InGameMap.BLOCK_SIZE * newBlockHeight);
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

											hasFoundFallingCollision = true;

										} else {
										}
									}
									if (!hasFoundUpCollision) {
										if (new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
												.contains(new Point(playerX, playerY - 1))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
														.contains(new Point(playerX + playerWidth - 1, playerY - 1))) {

											hasFoundUpCollision = true;

										} else {
										}
									}
									if (!hasFoundRightCollision) {
										if (new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
												.contains(new Point(playerX + playerWidth, playerY))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight).contains(
														new Point(playerX + playerWidth, playerY + playerHeight - 1))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight).contains(
														new Point(playerX + playerWidth, playerY + playerHeight / 2))) {

											hasFoundRightCollision = true;

										} else {
										}
									}
									if (!hasFoundLeftCollision) {
										if (new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
												.contains(new Point(playerX - 1, playerY))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
														.contains(new Point(playerX - 1, playerY + playerHeight - 1))
												|| new Rectangle(newX, newY, newBlockWidth, newBlockHeight)
														.contains(new Point(playerX - 1, playerY + playerHeight / 2))) {

											hasFoundLeftCollision = true;

										} else
										{
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
