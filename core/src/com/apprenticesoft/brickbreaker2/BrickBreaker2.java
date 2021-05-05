package com.apprenticesoft.brickbreaker2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.apprenticesoft.brickbreaker2.screens.LoadingScreen;
import com.apprenticesoft.brickbreaker2.utils.ObjectPools;

public class BrickBreaker2 extends Game {
	public SpriteBatch batch;
	Texture img;

	public ObjectPools pools;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		pools = new ObjectPools(this);

		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {

		super.render();
		/*
		ScreenUtils.clear(1, 0.6f, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		 */
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
