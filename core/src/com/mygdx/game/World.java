package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class World {
    TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public void update() {
        this.render();
    }

    public void render() {
        renderer.setView(camera);
        renderer.render();
    }

    public World() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("ASSETS/MAPS/grasslands.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera(MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
    }

    public OrthogonalTiledMapRenderer getRenderer(){
        return renderer;
    }
}

