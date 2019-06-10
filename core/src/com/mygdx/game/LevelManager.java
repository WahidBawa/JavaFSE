package com.mygdx.game;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class LevelManager {
    public static int lvlTileWidth;
    public static int lvlTileHeight;
    public static int lvlPixelWidth;
    public static int lvlPixelHeight;
    public static int tilePixelWidth;
    public static int tilePixelHeight;
    public static TiledMap tiledMap;
    public static GraphImp graph;//tutorial sias Imp

    public static void loadLevel(TiledMap map) {
        MapProperties properties = map.getProperties();
        lvlTileWidth = properties.get("width", Integer.class);
        lvlTileHeight = properties.get("height" , Integer.class);
        tilePixelWidth = properties.get("tilewidth", Integer.class);
        tilePixelHeight = properties.get("tileheight", Integer.class);
        lvlTileWidth = lvlTileWidth * lvlPixelWidth;
        lvlTileHeight = lvlPixelHeight * lvlTileHeight;

        graph = GraphGenerator.generateGraph(tiledMap);
    }
}
