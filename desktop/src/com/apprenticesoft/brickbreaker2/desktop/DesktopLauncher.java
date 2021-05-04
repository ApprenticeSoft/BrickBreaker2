package com.apprenticesoft.brickbreaker2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.apprenticesoft.brickbreaker2.BrickBreaker2;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Minimal Brick Breaker 2";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new BrickBreaker2(), config);
	}
}
