package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	
	final DropEx game;
	
	OrthographicCamera camera;
	
	private Texture img;
	private Texture yao;
	private Rectangle tikRect;
	private Array<Rectangle> yaoRectArr;
	private long lastDropTime;
	
	Music bgm;
	float bgmv = 0.0f;
	
	Sound jiu;
	Sound zwei;
	
	private int posx = 0;
	private int posy = 0;
	
	Vector3 touchPos = new Vector3();
	int yaoGot;
	
	public GameScreen(final DropEx game) {
		this.game = game;
		
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 1069, 600);
		
		// graphic
		img = new Texture(Gdx.files.internal("texture/suntik.jpg"));
		yao = new Texture(Gdx.files.internal("texture/yao.jpg"));
		tikRect = new Rectangle();
		tikRect.x = 1069/2-120/2;
		tikRect.y = 20;
		tikRect.width = 120;
		tikRect.height = 120;
		yaoRectArr = new Array<Rectangle>();
		spawnYao();
		
		// bgm
		bgm = Gdx.audio.newMusic(Gdx.files.getFileHandle("bgm/feiyu.mp3", FileType.Internal));
		bgm.setVolume(bgmv);
		bgm.setLooping(true);
		
		// sound
		jiu = Gdx.audio.newSound(Gdx.files.getFileHandle("se/jiu.wav", FileType.Internal));
		zwei = Gdx.audio.newSound(Gdx.files.getFileHandle("se/zwei.wav", FileType.Internal));
		
		yaoGot = 0;
	}

	@Override
	public void show() {
		bgm.play();
	}

	@Override
	public void render(float delta) {
		// background
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// logic
		if (TimeUtils.nanoTime() - lastDropTime > 200000000){
			spawnYao();
		}
		Iterator<Rectangle> yaoIt = yaoRectArr.iterator();
		while (yaoIt.hasNext()){
			Rectangle rect = yaoIt.next();
			rect.y -= 200*Gdx.graphics.getDeltaTime();
			if (rect.y+37<0){
				yaoIt.remove();
			} else{
				if (rect.overlaps(tikRect)){
					zwei.play();
					yaoGot++;
					yaoIt.remove();
				}
			}
		}
		
		// camera
		camera.update();
		
		// render
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Pills Collected: " + yaoGot, 0, 600);
		game.batch.draw(img, tikRect.x, tikRect.y);
		for(Rectangle rect: yaoRectArr){
			game.batch.draw(yao, rect.x, rect.y);
		}
		game.batch.end();
		
		// input
		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			tikRect.x = touchPos.x - 120/2;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)){
			tikRect.x -= 800*Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)){
			tikRect.x += 800*Gdx.graphics.getDeltaTime();
		}
		tikRect.x = Math.min(1069-120, Math.max(0, tikRect.x));
		
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
	}
	
	private void spawnYao(){
		Rectangle rect = new Rectangle();
		rect.x = MathUtils.random(0, 1069-37);
		rect.y = 600;
		rect.width = 37;
		rect.height = 37;
		yaoRectArr.add(rect);
		lastDropTime = TimeUtils.nanoTime();
	}
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		img.dispose();
		yao.dispose();
		bgm.dispose();
		zwei.dispose();
		jiu.dispose();
	}

}
