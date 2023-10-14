package fr.nicolas.wispy.Panels.Components.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class EscapeMenu {

	// Escape menu in game

	public void paint(Graphics g, int frameHeight) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(0, 0, 0, 200));
		g2d.fillRect(0, 0, 250, frameHeight);
		g2d.setColor(new Color(255, 255, 255, 200));
		g2d.setFont(new Font("Arial", Font.PLAIN, 40));
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g2d.drawString("Options", 20, 55);
	}

}
