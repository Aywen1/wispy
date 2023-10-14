package fr.nicolas.wispy;

import fr.nicolas.wispy.Frames.MainFrame;
import xyz.someboringnerd.wispy.managers.BlockManager;
import xyz.someboringnerd.wispy.managers.InputManager;
import xyz.someboringnerd.wispy.managers.TextureManager;

public class Main {

	public static void main(String[] args)
	{
		new BlockManager();
		new InputManager();
		new MainFrame();
	}
}
