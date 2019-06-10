package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.sun.corba.se.impl.orbutil.graph.GraphImpl;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.pathfinding;

public class GraphGenerator {
    public static GraphImp generateGraph(TiledMap map) {
        Array<Node> nodes = new Array<Node>;
        TiledMapTileLayer tiled = (TiledMapTileLayer)map.getLayers().get(0);
        int mapHight = LevelManager.lvlTileHeight;
        int mapWidth = LevelManager.lvlTileWidth;

        //loops over the tiles in the map

        for (int y = 0; y< mapHight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                //generate a node for each tile
                Node node = new Node();
                node.type = Node.Type.REGULAR;
                nodes.add(node);
            }
        }
        for (int y = 0; y < mapHight; y++) {
            for (int x = 0; x < mapWidth; x++ ) {
                TiledMapTileLayer.Cell target = tiles.getCell(x,y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y+1);
                TiledMapTileLayer.Cell left = tiles.getCell(x-1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x+1, y);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y-1);

                Node targetNode = nodes.get(mapWidth * y + x);
                if (target == null) {
                    if (y != 0 && down == null) {
                        Node downNode = nodes.get(mapWidth * (y-1) + x);
                        targetNode.createConnections(downNode, 1);
                    }
                    if (x != 0 && left == null) {
                        Node leftNode = nodes.get(mapWidth * y +x -1);
                        targetNode.createConnections(leftNode, 1);
                    }
                    if (x != mapWidth - 1 && right == null) {
                        Node rightNode = nodes.get(mapWidth * y +x +1):
                        targetNode.createConnections(rightNode, 1);
                    }
                    if (y != mapHight -1 && up == null) {
                        Node upNode = nodes.get(mapWidth * (y + 1) +x);
                        targetNode.createConnections(upNode, 1);
                    }
                }
            }
        }
        return new GraphImp(nodes);
    }
}
