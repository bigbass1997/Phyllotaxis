package com.bigbass.phyllotaxis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bigbass.phyllotaxis.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1100;
		config.height = 800;
		config.resizable = false;
		
		config.vSyncEnabled = false;
		
		config.title = "Phyllotaxis";
		
		new LwjglApplication(new Main(), config);
	}
}
