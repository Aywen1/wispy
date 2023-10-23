package xyz.someboringnerd.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.TheGame;
import org.lwjgl.glfw.GLFW;

public class DesktopLauncher
{

	public static String debugInformations;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.useVsync(false);
		config.setWindowedMode(1280, 720);
		config.disableAudio(false);

		config.setWindowIcon("rescources/textures/icon.png");

		if(GLFW.glfwPlatformSupported(GLFW.glfwGetPlatform()))
		{
			LogManager.print("User's platform is valid under GLFW. Platform ID : " + GLFW.glfwGetPlatform());
			LogManager.print("GLFW Version String : " + GLFW.glfwGetVersionString());
		}
		else
		{
			LogManager.error("Whatever platform you are running just dont work for some reason. Platform ID : " + GLFW.glfwGetPlatform());
			LogManager.error("GLFW Version String : " + GLFW.glfwGetVersionString());
			LogManager.error("Valid platform IDs :");
			LogManager.error("" + GLFW.GLFW_ANY_PLATFORM);
			LogManager.error("" + GLFW.GLFW_PLATFORM_WIN32);
			LogManager.error("" + GLFW.GLFW_PLATFORM_COCOA);
			LogManager.error("" + GLFW.GLFW_PLATFORM_WAYLAND);
			LogManager.error("" + GLFW.GLFW_PLATFORM_X11);
			LogManager.error("" + GLFW.GLFW_PLATFORM_NULL);
			LogManager.print("If the game still start, then dismiss this issue, otherwise please contact me at contact@someboringnerd.xyz");
		}

		new Lwjgl3Application(new TheGame(), config);
	}
}
