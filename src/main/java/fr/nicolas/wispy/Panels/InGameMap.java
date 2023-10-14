package fr.nicolas.wispy.Panels;

import fr.nicolas.wispy.Frames.MainFrame;
import fr.nicolas.wispy.Panels.Components.Menu.EscapeMenu;
import fr.nicolas.wispy.Panels.Components.Menu.WPanel;
import fr.nicolas.wispy.Panels.Fonctions.MapManager;
import fr.nicolas.wispy.Panels.Fonctions.MapManager.RefreshPaintMap;
import fr.nicolas.wispy.Runner;
import xyz.someboringnerd.wispy.Entities.PlayerEntity;
import xyz.someboringnerd.wispy.managers.InputManager;
import xyz.someboringnerd.wispy.managers.TextureManager;
import xyz.someboringnerd.wispy.math.Vector2D;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class InGameMap extends WPanel implements KeyListener, MouseListener, MouseMotionListener {

	public static final int BLOCK_SIZE = 32;
	private int newBlockWidth = BLOCK_SIZE, newBlockHeight = BLOCK_SIZE, playerX, playerY, playerWidth, playerHeight;
	private Runner runner;
	private MapManager mapManager;
	private BufferedImage sky;
	private PlayerEntity player;
	private Point mouseLocation;

	private boolean keyDPressed = false, keyQPressed = false, keySpacePressed = false, isEscapeMenuOpen = false;

	public InGameMap(Rectangle frameBounds, boolean isNewGame)
	{
		super(frameBounds);
		newBlockWidth = BLOCK_SIZE;
		newBlockHeight = BLOCK_SIZE;

		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);

		sky = TextureManager.getTexture("map/sky.png");
		player = new PlayerEntity(new Vector2D<Integer>(605, 250));

		// Cr�ation/Chargement nouveau monde
		mapManager = new MapManager(player);
		mapManager.loadWorld("TestWorld");

		// Lancement des threads
		runner = new Runner(this); // Actualiser les blocs puis les textures

		setFrameBounds(new Rectangle(MainFrame.INIT_WIDTH, MainFrame.INIT_HEIGHT));
	}

	// Refresh / Paint methods
	public void refresh()
	{
		player.Update();
	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(sky, 0, 0, this.getWidth(), this.getHeight(), null);

		// Le paint des blocs int�gre le test de collision avec le joueur
		mapManager.refreshPaintAllDisplayedBlocks(g, RefreshPaintMap.PAINT, null, mouseLocation);

		player.Draw(g);

		if (isEscapeMenuOpen) {
			new EscapeMenu().paint(g, this.getHeight());
		}

		mapManager.refreshPaintAllDisplayedBlocks(g, RefreshPaintMap.SELECTION, null, mouseLocation);
	}

	public void setFrameBounds(Rectangle frameBounds) {
		newBlockWidth = BLOCK_SIZE * (int) frameBounds.getWidth() / MainFrame.INIT_WIDTH;
		newBlockHeight = BLOCK_SIZE * (int) frameBounds.getHeight() / MainFrame.INIT_HEIGHT;
		playerX = newBlockWidth;
		playerY = 64 / InGameMap.BLOCK_SIZE * newBlockHeight;
		playerWidth = (int) player.getWidth() / InGameMap.BLOCK_SIZE * newBlockWidth;
		playerHeight = (int) player.getHeight() / InGameMap.BLOCK_SIZE * newBlockHeight;
	}

	// Getters and Setters
	public int getNewBlockWidth() {
		return newBlockWidth;
	}

	public int getNewBlockHeight() {
		return newBlockHeight;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	// KeyListener
	public void keyPressed(KeyEvent e)
	{
		InputManager.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		InputManager.keyReleased(e);
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
