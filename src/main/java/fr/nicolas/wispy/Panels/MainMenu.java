package fr.nicolas.wispy.Panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import fr.nicolas.wispy.Frames.MainFrame;
import fr.nicolas.wispy.Panels.Components.Menu.WButton;
import fr.nicolas.wispy.Panels.Components.Menu.WPanel;
import xyz.someboringnerd.wispy.managers.TextureManager;

public class MainMenu extends WPanel implements MouseListener, MouseMotionListener {

	private BufferedImage bg, title;
	private Point mouseLocation = new Point(0, 0);
	private WButton buttonStart, buttonSettings, buttonQuit;
	private MainFrame mainFrame;

	public MainMenu(Rectangle frameBounds, MainFrame mainFrame) {
		super(frameBounds);
		this.mainFrame = mainFrame;

		// Cr�ation dossier config
		if (!new File("Wispy").exists()) {
			new File("Wispy/worlds").mkdirs();
		}

		bg = TextureManager.getTexture("img/bg.png");
		title = TextureManager.getTexture("img/title.png");

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);

		init();
	}

	private void init() {
		this.setLayout(null);

		buttonStart = new WButton("Start", new Rectangle((int) frameBounds.getWidth() / 2 - 450 / 2,
				(int) frameBounds.getHeight() / 2 - 93 - 110, 450, 93));
		buttonSettings = new WButton("Settings", new Rectangle((int) frameBounds.getWidth() / 2 - 450 / 2,
				(int) frameBounds.getHeight() / 2 - 93, 450, 93));
		buttonQuit = new WButton("Quit", new Rectangle((int) frameBounds.getWidth() / 2 - 450 / 2,
				(int) frameBounds.getHeight() / 2 - 93 - 110, 450, 93));

		add(buttonStart);
		add(buttonSettings);
		add(buttonQuit);

		setFrameBounds(frameBounds);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
		g.setColor(new Color(0, 0, 0, 220));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		int newWidth = 393 * (int) frameBounds.getWidth() / MainFrame.INIT_WIDTH;
		int newHeight = 142 * (int) frameBounds.getHeight() / MainFrame.INIT_HEIGHT;
		g.drawImage(title, (int) frameBounds.getWidth() / 2 - newWidth / 2,
				(int) 55 * (int) frameBounds.getHeight() / MainFrame.INIT_HEIGHT, newWidth, newHeight, null);
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
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (buttonStart.mouseClick(mouseLocation)) {
				mainFrame.newGame();
			} else if (buttonSettings.mouseClick(mouseLocation)) {
				System.out.println(2);
			} else if (buttonQuit.mouseClick(mouseLocation)) {
				System.exit(0);
			}
		}

		repaint();
	}

	// MouseMotionListener

	public void mouseDragged(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		buttonStart.mouseMove(mouseLocation);
		buttonSettings.mouseMove(mouseLocation);
		buttonQuit.mouseMove(mouseLocation);

		repaint();
	}

	public void mouseMoved(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		buttonStart.mouseMove(mouseLocation);
		buttonSettings.mouseMove(mouseLocation);
		buttonQuit.mouseMove(mouseLocation);

		repaint();
	}

	public void setFrameBounds(Rectangle frameBounds) {
		this.frameBounds = frameBounds;

		int newWidth = 450 * (int) frameBounds.getWidth() / MainFrame.INIT_WIDTH;
		int newHeight = 93 * (int) frameBounds.getHeight() / MainFrame.INIT_HEIGHT;
		buttonStart.changeBounds(new Rectangle((int) frameBounds.getWidth() / 2 - newWidth / 2,
				(int) frameBounds.getHeight() / 2 - newHeight - 50 * (int) frameBounds.getHeight() / 700, newWidth,
				newHeight));

		buttonSettings.changeBounds(new Rectangle((int) frameBounds.getWidth() / 2 - newWidth / 2,
				(int) frameBounds.getHeight() / 2 - newHeight + 70 * (int) frameBounds.getHeight() / 700, newWidth,
				newHeight));

		buttonQuit.changeBounds(new Rectangle((int) frameBounds.getWidth() / 2 - newWidth / 2,
				(int) frameBounds.getHeight() / 2 - newHeight + 190 * (int) frameBounds.getHeight() / 700, newWidth,
				newHeight));

		buttonStart.reSize(40 * ((int) frameBounds.getWidth() * (int) frameBounds.getHeight()) / 1200000);
		buttonSettings.reSize(40 * ((int) frameBounds.getWidth() * (int) frameBounds.getHeight()) / 1200000);
		buttonQuit.reSize(40 * ((int) frameBounds.getWidth() * (int) frameBounds.getHeight()) / 1200000);
	}

}
