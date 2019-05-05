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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import static com.badlogic.gdx.Gdx.graphics;

public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    public static Player player;

    public static int speed = 9001;

    public static int WIDTH = 1366, HEIGHT = 1024;

    public static int MAP_WIDTH, MAP_HEIGHT;

    public final static int TILESIZE = 16;

    public static World world;

    public static WorldCreator wc;

    public static final float PPM = 0.3f;

    OrthogonalTiledMapRenderer renderer;

    public static OrthographicCamera camera;

    public static boolean moving = false;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static int dir = DOWN;

    Box2DDebugRenderer dbr;

    @Override
    public void create() {
        graphics.setWindowedMode(WIDTH, HEIGHT);
        world = new World(new Vector2(0, 0), true);
        player = new Player();


        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("ASSETS/MAPS/OWO.tmx");

        MAP_WIDTH = (Integer) map.getProperties().get("width") * TILESIZE;
        MAP_HEIGHT = (Integer) map.getProperties().get("height") * TILESIZE;

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

        world.step(1 / 60f, 6, 2);

        Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        renderer.setView(camera);
        renderer.render(new int[]{0, 1});

        batch.setProjectionMatrix(camera.combined);


        batch.begin();

        updateWorldObjects();

        batch.end();

        movePlayer();

        renderer.render(new int[]{2});

        dbr.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void movePlayer() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.getBody().applyLinearImpulse(new Vector2(-speed * 2, 0), player.getBody().getWorldCenter(), true);
            moving = true;
            dir = LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getBody().applyLinearImpulse(new Vector2(speed * 2, 0), player.getBody().getWorldCenter(), true);
            moving = true;
            dir = RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.getBody().applyLinearImpulse(new Vector2(0, -speed * 2), player.getBody().getWorldCenter(), true);
            moving = true;
            dir = DOWN;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.getBody().applyLinearImpulse(new Vector2(0, speed * 2), player.getBody().getWorldCenter(), true);
            moving = true;
            dir = UP;
        } else {
            player.getBody().applyLinearImpulse(new Vector2(player.getBody().getLinearVelocity().x * -1, player.getBody().getLinearVelocity().y * -1), player.getBody().getWorldCenter(), true);
            moving = false;
        }

        player.setX(player.body.getPosition().x);
        player.setY(player.body.getPosition().y);

        camera.position.x = player.getX();
        camera.position.y = player.getY();
    }

    public void updateWorldObjects() {
        player.update(batch);

        for (Enemy i : wc.getEnemies()) i.update(batch);

        for (Chest i : wc.getChests()) i.update(batch);
    }
}
