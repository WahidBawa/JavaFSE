package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class World {
    TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public static OrthographicCamera camera;

    public void update() {
        this.render();
    }

    public void render() {
        Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    public World() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("ASSETS/MAPS/grasslands.tmx");
        MapProperties properties = map.getProperties();

        int tileWidth = properties.get("tilewidth", Integer.class);
        int tileHeight = properties.get("tileheight", Integer.class);
        int mapWidthInTiles = properties.get("width", Integer.class);
        int mapHeightInTiles = properties.get("height", Integer.class);

        int mapWidthInPixels = mapWidthInTiles * tileWidth;
        int mapHeightInPixels = mapHeightInTiles * tileHeight;

        camera = new OrthographicCamera(800.f, 600.f);
        camera.position.x = mapWidthInPixels * .5f;
        camera.position.y = mapHeightInPixels * .35f;

        renderer = new OrthogonalTiledMapRenderer(map);


        for (int i = 0; i < map.getLayers().get("walls").getObjects().getCount(); i++) {

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