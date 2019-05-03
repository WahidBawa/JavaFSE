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


    public static int speed = 10000;

    public static int WIDTH = 1366, HEIGHT = 1024;

    public static World world;

    public static WorldCreator wc;

    public static final float PPM = 0.3f;

    OrthogonalTiledMapRenderer renderer;
    public static OrthographicCamera camera;

    Box2DDebugRenderer dbr;

    @Override
    public void create() {
        graphics.setWindowedMode(WIDTH, HEIGHT);
        world = new World(new Vector2(0, 0), true);
        player = new Player();


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

        world.step(1 / 60f, 6, 2);
        Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        camera.update();
        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        movePlayer();


        batch.begin();
        // updating of classes and drawing happens here
        player.update(batch);
        for (Enemy i : wc.getEnemies()) i.update(batch, player);

        batch.end();

        dbr.render(world, camera.combined);

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void movePlayer() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.getBody().applyLinearImpulse(new Vector2(-speed * 2, 0), player.getBody().getWorldCenter(), true);

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getBody().applyLinearImpulse(new Vector2(speed * 2, 0), player.getBody().getWorldCenter(), true);

        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.getBody().applyLinearImpulse(new Vector2(0, -speed * 2), player.getBody().getWorldCenter(), true);

        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.getBody().applyLinearImpulse(new Vector2(0, speed * 2), player.getBody().getWorldCenter(), true);

        } else {
            player.getBody().applyLinearImpulse(new Vector2(player.getBody().getLinearVelocity().x * -1, player.getBody().getLinearVelocity().y * -1), player.getBody().getWorldCenter(), true);
        }

        player.setX(player.body.getPosition().x);
        player.setY(player.body.getPosition().y);

        camera.position.x = player.getX();
        camera.position.y = player.getY();

        for (Enemy i : wc.getEnemies()){
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                i.getBody().applyLinearImpulse(new Vector2(-speed * 2, 0), i.getBody().getWorldCenter(), true);

            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                i.getBody().applyLinearImpulse(new Vector2(speed * 2, 0), i.getBody().getWorldCenter(), true);

            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                i.getBody().applyLinearImpulse(new Vector2(0, -speed * 2), i.getBody().getWorldCenter(), true);

            } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                i.getBody().applyLinearImpulse(new Vector2(0, speed * 2), i.getBody().getWorldCenter(), true);

            } else {
                i.getBody().applyLinearImpulse(new Vector2(i.getBody().getLinearVelocity().x * -1, i.getBody().getLinearVelocity().y * -1), i.getBody().getWorldCenter(), true);
            }
        }
    }
}
