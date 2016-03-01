package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DropEx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SimplerTouchTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "tik pill";
//		config.width = 1280;
//		config.height = 720;
		config.width = 1069;
		config.height = 600;
		new LwjglApplication(new DropEx(), config);
	}
}
