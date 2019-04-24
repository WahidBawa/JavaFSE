package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Gdx.graphics;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	int x, y = 0;
	Player player;
	World world;
	@Override
	public void create () {
		batch = new SpriteBatch();
		graphics.setWindowedMode(1366, 1024);
		player = new Player(x, y);
		world = new World();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// all logic happens here
		movePlayer();

		batch.begin();
		// updating of classes and drawing happens here
		world.render(batch);
//		player.update(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void movePlayer(){
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.goLeft();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.goRight();
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.goDown();
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.goUp();
	}
}
