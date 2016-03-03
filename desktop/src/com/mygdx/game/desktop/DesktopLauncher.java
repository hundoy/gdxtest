package com.mygdx.game.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tests.ViewportTest2;
import com.mygdx.game.DropEx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SimplerTouchTest;
import com.zohar.test.graphics2d.TwodGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		
		config.title = "2d graphics test";
//		config.width = 1280;
//		config.height = 720;
		config.width = 1069;
		config.height = 600;
		config.vSyncEnabled = true;
		
//		config.setFromDisplayMode(displayMode); // full screen
		
		new LwjglApplication(new TwodGame(), config);
	}
}
