package com.zohar.test.graphics2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TwodMapScreen implements Screen {

	final TwodGame game;
	OrthographicCamera camera;
	Viewport viewport;
	
	private Texture iconTex;
	private Texture skyTex;
	private Texture walkActor4Tex;
	private Sprite bgSp;
	private Array<Sprite> stoneSpArr;
	private TextureRegion[] walkFrames;
	private Animation walkAnime;
	private float stateTime;
	
	private final int ICON_WH = 24;
	private final int WALK_COL = 12;
	private final int WALK_ROW = 8;
	
	private Rectangle scissors;
	private Rectangle clipBounds;
    
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
        
        // animation
        walkActor4Tex = new Texture(Gdx.files.internal("texture/WalkActor4.png"));
        TextureRegion[][] tr = TextureRegion.split(walkActor4Tex, 
        		walkActor4Tex.getWidth()/WALK_COL, walkActor4Tex.getHeight()/WALK_ROW);
        walkFrames = new TextureRegion[WALK_COL*WALK_ROW];
        for (int i=0; i<WALK_ROW; i++){
        	for (int j=0; j<WALK_COL; j++){
        		walkFrames[i*WALK_COL+j] = tr[i][j];
        	}
        }
        walkAnime = new Animation(0.3f, walkFrames);
        stateTime = 0;
        
        // test clipping
        scissors = new Rectangle();
        clipBounds = new Rectangle(50,100,400,200);
        ScissorStack.calculateScissors(camera, game.batch.getTransformMatrix(), clipBounds, scissors);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		handleInput();
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		// background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		// logic
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion curFrame = walkAnime.getKeyFrame(stateTime, true);
		
		// gl draw
		game.batch.begin();
		game.batch.disableBlending();
		bgSp.draw(game.batch);
		game.batch.enableBlending();
//		ScissorStack.pushScissors(scissors);
		for (Sprite sp: stoneSpArr){
			sp.draw(game.batch);
		}
		game.batch.draw(curFrame, 500, 300);
//		game.batch.flush();
//		ScissorStack.popScissors();
		game.font.draw(game.batch, "今天天气不错", 0, 100);
		game.font.draw(game.batch, "风和日丽的天气有的字不显示真好！", 0, 150);
		
		game.batch.end();
	}
	
	
	private void genStone(){
		Sprite stoneSp = new Sprite(iconTex, 4*ICON_WH, 6*ICON_WH, ICON_WH, ICON_WH);
		stoneSp.setPosition(MathUtils.random(0, TwodGame.SCW-ICON_WH), MathUtils.random(0, TwodGame.SCH-ICON_WH));
		stoneSpArr.add(stoneSp);
	}
	
	private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
        	camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        	camera.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        	camera.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        	camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        	camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        	camera.rotate(-0.5f, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
        	camera.rotate(0.5f, 0, 0, 1);
        }

//        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);
//
//        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
//        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
//
//        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
//        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
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
		walkActor4Tex.dispose();
		bgSp.getTexture().dispose();
		for (Sprite sp: stoneSpArr){
			sp.getTexture().dispose();
		}
	}

}
