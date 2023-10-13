package fr.nicolas.wispy.Panels.Components.Game;

import java.awt.Graphics;
import java.awt.Rectangle;

import fr.nicolas.wispy.Panels.GamePanel;
import fr.nicolas.wispy.Panels.GamePanel.BlockID;

public class Block extends Rectangle {

	private BlockID blockID;

	public Block(BlockID blockID) {
		this.blockID = blockID;

		if (blockID == BlockID.STONE || blockID == BlockID.DIRT || blockID == BlockID.GRASS
				|| blockID == BlockID.SAND) {
			width = GamePanel.BLOCK_SIZE;
			height = GamePanel.BLOCK_SIZE;
		}
	}

	public void paint(Graphics g, int newX, int newY, int newBlockWidth, int newBlockHeight) {
		g.drawImage(blockID.getImg(), newX, newY, newBlockWidth, newBlockHeight, null);
	}

}
