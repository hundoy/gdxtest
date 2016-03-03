package com.zohar.test.graphics2d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TwodGame extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	
	public static final int SCW = 1069;
	public static final int SCH = 600;	
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		this.setScreen(new TwodMapScreen(this));
	}
	
	@Override
	public void render(){
		super.render();
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		font.dispose();
	}
}
