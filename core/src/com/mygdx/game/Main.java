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
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	public static Player player;
	public static Enemy goose1;


	public static int speed = 8;

	public static int WIDTH = 1366, HEIGHT = 1024;

	public static World world;

	public static WorldCreator wc;

	public static final float PPM = 0.3f;

	OrthogonalTiledMapRenderer renderer;
	OrthographicCamera camera;

	Box2DDebugRenderer dbr;

	@Override
	public void create() {
		graphics.setWindowedMode(WIDTH, HEIGHT);
		world = new World(new Vector2(0, 0), true);
		player = new Player();
		goose1 = new Enemy();



		TmxMapLoader loader = new TmxMapLoader();
		TiledMap map = loader.load("ASSETS/MAPS/grasslands.tmx");

		camera = new OrthographicCamera(800f, 600f);

		renderer = new OrthogonalTiledMapRenderer(map, PPM);

		batch = new SpriteBatch();

		wc = new WorldCreator(world, map);

		dbr = new Box2DDebugRenderer();

		world.setContactListener(new CollisionListener());

	}

	@Override
	public void render() {

		camera.zoom = PPM;

		world.step(1/60f, 6, 2);
		Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		camera.update();
		renderer.setView(camera);
		renderer.render();

		movePlayer();

		batch.begin();
		// updating of classes and drawing happens here
        goose1.update(batch);
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
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			player.goRight();

		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			player.goDown();

		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			player.goUp();

		}

		player.x = player.body.getPosition().x;
		player.y = player.body.getPosition().y;
		camera.position.x = player.x;
		camera.position.y = player.y;
	}
}
