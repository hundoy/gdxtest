package com.zohar.test.graphics2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TwodMapScreen implements Screen {

	final TwodGame game;
	OrthographicCamera camera;
	Viewport viewport;
	
	private Texture iconTex;
	private Texture skyTex;
	private Sprite bgSp;
	private Array<Sprite> stoneSpArr;
	
	private final int ICON_WH = 24;
	
	
	public TwodMapScreen(TwodGame twodGame) {
		game = twodGame;
		
		camera = new OrthographicCamera();
        camera.setToOrtho(false, TwodGame.SCW, TwodGame.SCH);
        viewport = new FitViewport(TwodGame.SCW, TwodGame.SCH, camera);
        
        // graphic
        iconTex = new Texture(Gdx.files.internal("texture/IconSet.png"));
        skyTex = new Texture(Gdx.files.internal("texture/Ship.png"));
        bgSp = new Sprite(skyTex, 0, 0, TwodGame.SCW, TwodGame.SCH);
        bgSp.setPosition(0, 0);
        stoneSpArr = new Array<Sprite>();
        for (int i=0; i<16; i++){
        	genStone();
        }
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// gl draw
		game.batch.begin();
		game.batch.disableBlending();
		bgSp.draw(game.batch);
		game.batch.enableBlending();
		for (Sprite sp: stoneSpArr){
			sp.draw(game.batch);
		}
		game.batch.end();
	}
	
	
	private void genStone(){
		Sprite stoneSp = new Sprite(iconTex, 4*ICON_WH, 6*ICON_WH, ICON_WH, ICON_WH);
		stoneSp.setPosition(MathUtils.random(0, TwodGame.SCW-ICON_WH), MathUtils.random(0, TwodGame.SCH-ICON_WH));
		stoneSpArr.add(stoneSp);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		skyTex.dispose();
		iconTex.dispose();
	}

}
