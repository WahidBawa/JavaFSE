package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;

	public static int speed = 8;

	public static int WIDTH = 1366, HEIGHT = 1024;

	public World world;

	WorldCreator wc;

	OrthogonalTiledMapRenderer renderer;
	OrthographicCamera camera;

	Box2DDebugRenderer dbr;

	@Override
	public void create() {
		graphics.setWindowedMode(WIDTH, HEIGHT);
		player = new Player(0, 0);
		world = new World(new Vector2(0, 0), true);


		TmxMapLoader loader = new TmxMapLoader();
		TiledMap map = loader.load("ASSETS/MAPS/grasslands.tmx");

		camera = new OrthographicCamera(800f, 600f);

		renderer = new OrthogonalTiledMapRenderer(map);

		batch = new SpriteBatch();

		wc = new WorldCreator(world, map);

		dbr = new Box2DDebugRenderer();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();

		movePlayer();

		batch.begin();
		// updating of classes and drawing happens here
		player.update(batch);

		batch.end();

		dbr.render(world, camera.combined);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void movePlayer() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			player.goLeft();
			camera.translate(-speed, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			player.goRight();
			camera.translate(speed, 0);

		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			player.goDown();
			camera.translate(0, -speed);

		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			player.goUp();
			camera.translate(0, speed);

		}
	}
}
