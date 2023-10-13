package fr.nicolas.wispy.Panels.Components.Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import fr.nicolas.wispy.Panels.GamePanel;
import fr.nicolas.wispy.Panels.Fonctions.MapManager.RefreshPaintMap;

public class Player extends Rectangle {

	private BufferedImage playerStopImg, playerWalk1Img, playerWalk2Img;
	private boolean isFalling = false, isJumping = false, isWalking = false, isToRight = true, canGoLeft = true,
			canGoRight = true, canGoUp = true;
	private int jumpNum = 1, walkNum = 0;
	private GamePanel gamePanel;

	public Player(BufferedImage playerStopImg, BufferedImage playerWalk1Img, BufferedImage playerWalk2Img,
			GamePanel gamePanel) {
		this.playerStopImg = playerStopImg;
		this.playerWalk1Img = playerWalk1Img;
		this.playerWalk2Img = playerWalk2Img;
		this.gamePanel = gamePanel;
		this.width = GamePanel.BLOCK_SIZE;
		this.height = GamePanel.BLOCK_SIZE * 2;
	}

	public void refresh(int playerWidth, int playerHeight, int playerX, int playerY) {
		gamePanel.getMapManager().refreshPaintAllDisplayedBlocks(null, RefreshPaintMap.COLLISION, gamePanel.getWidth(),
				gamePanel.getHeight(), gamePanel.getNewBlockWidth(), gamePanel.getNewBlockHeight(), playerWidth,
				playerHeight, playerX, playerY, gamePanel, null);

		// Déplacements
		if (isWalking) {
			if (isToRight && canGoRight) {
				walkNum++;
				for (int i = 0; i < 2; i++) {
					if (canGoRight) {
						x += 1;
						gamePanel.getMapManager().refreshPaintAllDisplayedBlocks(null, RefreshPaintMap.COLLISION,
								gamePanel.getWidth(), gamePanel.getHeight(), gamePanel.getNewBlockWidth(),
								gamePanel.getNewBlockHeight(), playerWidth, playerHeight, playerX, playerY, gamePanel,
								null);
					} else {
						break;
					}
				}
			}
			if (!isToRight && canGoLeft) {
				walkNum++;
				for (int i = 0; i < 2; i++) {
					if (canGoLeft) {
						x -= 1;
						gamePanel.getMapManager().refreshPaintAllDisplayedBlocks(null, RefreshPaintMap.COLLISION,
								gamePanel.getWidth(), gamePanel.getHeight(), gamePanel.getNewBlockWidth(),
								gamePanel.getNewBlockHeight(), playerWidth, playerHeight, playerX, playerY, gamePanel,
								null);
					} else {
						break;
					}
				}
			}
		}

		if (walkNum > 20) {
			walkNum = 1;
		}

		// Jump
		if (isJumping && canGoUp) {
			if (jumpNum != 15) {
				for (int i = 0; i < 8 - jumpNum / 2; i++) {
					if (canGoUp) {
						y -= 1;
						gamePanel.getMapManager().refreshPaintAllDisplayedBlocks(null, RefreshPaintMap.COLLISION,
								gamePanel.getWidth(), gamePanel.getHeight(), gamePanel.getNewBlockWidth(),
								gamePanel.getNewBlockHeight(), playerWidth, playerHeight, playerX, playerY, gamePanel,
								null);
					} else {
						break;
					}
				}
				jumpNum++;
			} else {
				jumpNum = 1;
				isJumping = false;
			}
		}

		if (!canGoUp) {
			jumpNum = 1;
			isJumping = false;
		}

		// Gravité
		if (isFalling && !isJumping) {
			for (int i = 0; i < 4; i++) {
				if (isFalling) {
					y += 1;
					gamePanel.getMapManager().refreshPaintAllDisplayedBlocks(null, RefreshPaintMap.COLLISION,
							gamePanel.getWidth(), gamePanel.getHeight(), gamePanel.getNewBlockWidth(),
							gamePanel.getNewBlockHeight(), playerWidth, playerHeight, playerX, playerY, gamePanel,
							null);
				} else {
					break;
				}
			}
		}

	}

	public void paint(Graphics g, int x, int y, int width, int height) {
		// TODO: iswalking inutilisable car toujours faux donc frame playerStopImg n'est
		// pas affiché (toujours walk)
		if (isJumping) {
			drawImg(g, playerWalk2Img, x, y, width, height);
		} else if (isFalling || !isWalking || !canGoRight || !canGoLeft) {
			drawImg(g, playerStopImg, x, y, width, height);
		} else if (walkNum <= 10) {
			drawImg(g, playerWalk1Img, x, y, width, height);
		} else if (!isJumping && !isFalling && walkNum <= 20) {
			drawImg(g, playerWalk2Img, x, y, width, height);
		}
	}

	private void drawImg(Graphics g, BufferedImage img, int x, int y, int width, int height) {
		if (isToRight) {
			g.drawImage(img, x, y, width, height, null);
		} else {
			g.drawImage(img, x + width, y, -width, height, null);
		}
	}

	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}

	public void setWalking(boolean isWalking) {
		this.isWalking = isWalking;
	}

	public void setToRight(boolean isToRight) {
		this.isToRight = isToRight;
	}

	public void setJumping(boolean isJumping) {
		if (isJumping && !isFalling) {
			this.isJumping = isJumping;
		}
	}

	public void setCanGoLeft(boolean canGoLeft) {
		this.canGoLeft = canGoLeft;
	}

	public void setCanGoRight(boolean canGoRight) {
		this.canGoRight = canGoRight;
	}

	public void setCanGoUp(boolean canGoUp) {
		this.canGoUp = canGoUp;
	}

}
