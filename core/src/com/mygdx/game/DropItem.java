package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DropItem implements Poolable {

	public int sort;
	public Rectangle rect;
	public boolean alive;
	private int volc;
	
	public DropItem() {
		sort = 0;
		volc = 0;
		rect = new Rectangle();
		rect.set(0, 0, 37, 37);
		alive = false;
	}

	public void init(int sort, float x, float y){
		this.sort = sort;
		rect.setPosition(x, y);
		volc = MathUtils.random(150, 300);
		alive = true;
	}
	
	public void update(float dt){
		if (alive){
			rect.y -= volc*dt;
		}
	}


	@Override
	public void reset() {
		sort = 0;
		volc = 0;
		rect.setPosition(0, 0);
		alive = false;
	}

}
