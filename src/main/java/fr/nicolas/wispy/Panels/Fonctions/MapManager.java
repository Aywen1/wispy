package fr.nicolas.wispy.Panels.Fonctions;

import fr.nicolas.wispy.Panels.InGameMap;
import xyz.someboringnerd.wispy.Entities.PlayerEntity;
import xyz.someboringnerd.wispy.content.Block;
import xyz.someboringnerd.wispy.content.Map;

import java.awt.*;

public class MapManager
{
	private Map GameMap;
	private String worldName;
	private PlayerEntity player;

	public enum RefreshPaintMap
	{
		PAINT, COLLISION, SELECTION;
	}

	public MapManager(PlayerEntity player)
	{
		this.player = player;
	}

	public void loadWorld(String worldName)
	{
		GameMap = new Map();
		player.x = 0;
		player.y = GameMap.getSafeSpawn(0);
	}

	private Map generateMap(int num)
	{
		return new Map();
	}

	private int random(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	public void refreshPaintAllDisplayedBlocks(Graphics g, RefreshPaintMap mode, InGameMap gamePanel, Point mouseLocation)
	{
		refreshPaintMapDisplayedBlocks(g, mode, GameMap, gamePanel, mouseLocation);
	}

	private void refreshPaintMapDisplayedBlocks(Graphics g, RefreshPaintMap mode, Map mapB, InGameMap gamePanel, Point mouseLocation)
	{
		mapB.draw(g, mouseLocation);

		player.Draw(g);
	}
}