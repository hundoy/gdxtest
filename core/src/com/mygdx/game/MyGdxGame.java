package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture yao;
	Music bgm;
	float bgmv = 0.5f;
	
	Sound jiu;
	Sound zwei;
	
	private int posx = 0;
	private int posy = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture(Gdx.files.internal("texture/suntik.jpg"));
		img = new Texture(Gdx.files.internal("texture/yao.jpg"));
		bgm = Gdx.audio.newMusic(Gdx.files.getFileHandle("bgm/feiyu.mp3", FileType.Internal));
		bgm.setVolume(bgmv);
		bgm.play();
		bgm.setLooping(true);
		
		jiu = Gdx.audio.newSound(Gdx.files.getFileHandle("se/jiu.wav", FileType.Internal));
		zwei = Gdx.audio.newSound(Gdx.files.getFileHandle("se/zwei.wav", FileType.Internal));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isTouched()) {
//		  System.out.println("Input occurred at x=" + Gdx.input.getX() + ", y=" + Gdx.input.getY());
			posx = Gdx.input.getX();
			posy = Gdx.graphics.getHeight() - Gdx.input.getY();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			Gdx.app.log("press ", "left!");
			bgmv = Math.max(0, bgmv-0.01f);
			bgm.setVolume(bgmv);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			Gdx.app.log("press ", "right!");
			bgmv = Math.min(1, bgmv+0.01f);
			bgm.setVolume(bgmv);
		}
		
		batch.begin();
		batch.draw(img, posx, posy);
		batch.end();
	}
}
