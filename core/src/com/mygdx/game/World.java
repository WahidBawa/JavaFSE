package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
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

        for (int i = 0; i < map.getLayers().get("walls").getObjects().getCount(); i++) {
            System.out.println("i am being made");

            MapProperties currObj = map.getLayers().get("walls").getObjects().get(i).getProperties();

            float x = Float.parseFloat(currObj.get("x").toString());
            float y = Float.parseFloat(currObj.get("y").toString());
            float width = Float.parseFloat(currObj.get("width").toString());
            float height = Float.parseFloat(currObj.get("height").toString());

            new Wall(x, y, width, height);
        }
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }
}

