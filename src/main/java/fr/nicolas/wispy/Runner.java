package fr.nicolas.wispy;

import fr.nicolas.wispy.Panels.InGameMap;

public class Runner implements Runnable {

	private boolean isRunning = false;
	private InGameMap gamePanel;
	private int maxFps = 60;
	private long waitTime = 4;

	public Runner(InGameMap gamePanel) {
		this.gamePanel = gamePanel;
		start();
	}

	private void start() {
		isRunning = true;
		new Thread(this).start();
	}

	public void run() {
		while (isRunning) {
			gamePanel.refresh();
			gamePanel.repaint();

			try
			{
				Thread.sleep((long) (0.016 * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public long getWaitTime() {
		return waitTime;
	}
	
}
