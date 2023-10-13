package fr.nicolas.wispy.Panels;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.nicolas.wispy.Runner;
import fr.nicolas.wispy.Frames.MainFrame;
import fr.nicolas.wispy.Panels.Components.Game.Player;
import fr.nicolas.wispy.Panels.Components.Menu.EscapeMenu;
import fr.nicolas.wispy.Panels.Components.Menu.WPanel;
import fr.nicolas.wispy.Panels.Fonctions.MapManager;
import fr.nicolas.wispy.Panels.Fonctions.MapManager.RefreshPaintMap;

public class GamePanel extends WPanel implements KeyListener, MouseListener, MouseMotionListener {

	public static final int BLOCK_SIZE = 25, INIT_PLAYER_X = 605, INIT_PLAYER_Y = 315;
	private int newBlockWidth = BLOCK_SIZE, newBlockHeight = BLOCK_SIZE, playerX, playerY, playerWidth, playerHeight;
	private Runner runner;
	private MapManager mapManager;
	private BufferedImage sky;
	private Player player;
	private Point mouseLocation;

	private boolean keyDPressed = false, keyQPressed = false, keySpacePressed = false, isEscapeMenuOpen = false;

	public GamePanel(Rectangle frameBounds, boolean isNewGame) {
		super(frameBounds);
		newBlockWidth = BLOCK_SIZE;
		newBlockHeight = BLOCK_SIZE;

		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);

		// Chargement des textures
		for (int i = 0; i < BlockID.values().length; i++) {
			loadBlockImage(BlockID.values()[i]);
		}

		try {
			sky = ImageIO.read(getClass().getResource("Components/Game/res/map/sky.png"));
			player = new Player(ImageIO.read(getClass().getResource("Components/Game/res/player/p_stop.png")),
					ImageIO.read(getClass().getResource("Components/Game/res/player/p_walk1.png")),
					ImageIO.read(getClass().getResource("Components/Game/res/player/p_walk2.png")), this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Création/Chargement nouveau monde
		mapManager = new MapManager(player);
		mapManager.loadWorld("TestWorld");

		// Lancement des threads
		runner = new Runner(this); // Actualiser les blocs puis les textures
		mapManager.newLoadingMapThread(runner, this); // Charger et décharger les maps

		setFrameBounds(new Rectangle(MainFrame.INIT_WIDTH, MainFrame.INIT_HEIGHT));
	}

	// Gestion blocs (textures)
	public enum BlockID {
		STONE, DIRT, GRASS, SAND;

		private BufferedImage img = null;

		public void setImg(BufferedImage img) {
			this.img = img;
		}

		public BufferedImage getImg() {
			return img;
		}
	}

	private void loadBlockImage(BlockID blockID) {
		try {
			blockID.setImg(ImageIO.read(
					getClass().getResource("Components/Game/res/blocks/" + blockID.toString().toLowerCase() + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Refresh / Paint methods
	public void refresh() {
		if (keyDPressed) {
			player.setWalking(true);
			player.setToRight(true);
		}

		if (keyQPressed) {
			player.setWalking(true);
			player.setToRight(false);
		}

		if (!keyQPressed && !keyDPressed) {
			player.setWalking(false);
		}

		if (keySpacePressed) {
			player.setJumping(true);
			keySpacePressed = false;
		}

		player.refresh(playerX, playerY, playerWidth, playerHeight);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(sky, 0, 0, this.getWidth(), this.getHeight(), null);
		// Le paint des blocs intègre le test de collision avec le joueur
		mapManager.refreshPaintAllDisplayedBlocks(g, RefreshPaintMap.PAINT, this.getWidth(), this.getHeight(),
				newBlockWidth, newBlockHeight, 0, 0, 0, 0, this, null);
		player.paint(g, playerX, playerY, playerWidth, playerHeight);

		if (isEscapeMenuOpen) {
			new EscapeMenu().paint(g, this.getHeight());
		}

		mapManager.refreshPaintAllDisplayedBlocks(g, RefreshPaintMap.SELECTION, this.getWidth(), this.getHeight(),
				newBlockWidth, newBlockHeight, 0, 0, 0, 0, this, mouseLocation);
	}

	public void setFrameBounds(Rectangle frameBounds) {
		newBlockWidth = BLOCK_SIZE * (int) frameBounds.getWidth() / MainFrame.INIT_WIDTH;
		newBlockHeight = BLOCK_SIZE * (int) frameBounds.getHeight() / MainFrame.INIT_HEIGHT;
		playerX = INIT_PLAYER_X / GamePanel.BLOCK_SIZE * newBlockWidth;
		playerY = INIT_PLAYER_Y / GamePanel.BLOCK_SIZE * newBlockHeight;
		playerWidth = (int) player.getWidth() / GamePanel.BLOCK_SIZE * newBlockWidth;
		playerHeight = (int) player.getHeight() / GamePanel.BLOCK_SIZE * newBlockHeight;
	}

	// Getters and Setters
	public int getNewBlockWidth() {
		return newBlockWidth;
	}

	public int getNewBlockHeight() {
		return newBlockHeight;
	}

	public Player getPlayer() {
		return player;
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	// KeyListener
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (isEscapeMenuOpen) {
				isEscapeMenuOpen = false;
			} else {
				isEscapeMenuOpen = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyDPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			keyQPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keySpacePressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyDPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			keyQPressed = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	// MouseListener

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	// MouseMotionListener
	public void mouseDragged(MouseEvent e) {
		this.mouseLocation = e.getPoint();
	}

	public void mouseMoved(MouseEvent e) {
		this.mouseLocation = e.getPoint();
	}

}
