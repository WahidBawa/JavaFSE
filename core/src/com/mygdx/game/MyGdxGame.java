package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    int x, y = 0;
    Player player;
    World world;

    public static ArrayList<Wall> walls;

    public static int WIDTH = 1366, HEIGHT = 1024;

    @Override
    public void create() {
        graphics.setWindowedMode(WIDTH, HEIGHT);
        player = new Player(x, y);
        walls = new ArrayList<Wall>();
        world = new World();
        batch = (SpriteBatch) world.getRenderer().getBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // all logic happens here
        movePlayer();

        world.update(); // renders world

        batch.begin();
        // updating of classes and drawing happens here
        player.update(batch);

        batch.end();

        for (Wall w : walls) w.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void movePlayer() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.goLeft();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.goRight();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.goDown();
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.goUp();
    }
}
