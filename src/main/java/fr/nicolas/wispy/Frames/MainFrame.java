package fr.nicolas.wispy.Frames;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import fr.nicolas.wispy.Panels.InGameMap;
import fr.nicolas.wispy.Panels.MainMenu;
import fr.nicolas.wispy.Panels.Components.Menu.WPanel;

public class MainFrame extends JFrame {

	private WPanel panel;
	public static final int INIT_WIDTH = 1250, INIT_HEIGHT = 720;

	public MainFrame() {
		this.setTitle("Wispy | Forked by SomeBoringNerd");
		this.setSize(INIT_WIDTH, INIT_HEIGHT);
		this.setMinimumSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new MainMenu(this.getBounds(), this);

		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				panel.setFrameBounds(getBounds());
			}

			public void componentHidden(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}
		});

		this.setContentPane(panel);

		this.setVisible(true);
	}

	public void newGame() {
		panel = new InGameMap(this.getBounds(), true);
		this.setContentPane(panel);
		this.validate();
		panel.requestFocus();
	}

}
