package com.github.macylion.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.macylion.Minecraft2D;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Minecraft 2D";
		config.width = 1024;
		config.height = 768;
		config.fullscreen = false;
		config.addIcon("icon.png", Files.FileType.Local);
		new LwjglApplication(new Minecraft2D(), config);
	}
}