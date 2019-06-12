package com.mygdx.game.PathfindingDW;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Map implements IndexedGraph<Node> {
    private Array<Node> nodes;
    public Node[][] nodeMap;
    private CostHeuristic costHeuristic = new CostHeuristic();
    int width;
    int height;

    public Map(TiledMapTileLayer layer) {
        this.width = layer.getWidth();
        this.height = layer.getHeight();

        nodes = new Array<Node>(width * height);
        nodeMap = new Node[height][width];
        Node n;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                n = new Node(x, y, x+y*width);

                nodeMap[y][x] = n;
                nodes.add(n);
            }
        }

        Array<Connection<Node>> connectionArray;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                connectionArray = new Array<Connection<Node>>();

                for (int ofx = -1; ofx < 2; ofx++) {
                    for (int ofy = -1; ofy < 2; ofy++) {
                        if (x + ofx >= 0 && x + ofx < width && y + ofy >= 0 && y + ofy < height) {
                            connectionArray.add(new NodeConnection(nodeMap[y][x], nodeMap[y+ofy][x+ofx]));
                        }
                    }
                }

                nodeMap[y][x].setConnections(connectionArray);
            }
        }
    }

    public GraphPath<Node> findPath(int x, int y, int fx, int fy){
        return findPath(nodeMap[y][x], nodeMap[fy][fx]);
    }

    public DefaultGraphPath<Node> findPath(Node start, Node End){
        sGraphPath path = new sGraphPath();
        PathSmoother<Node, Vector2> s = new PathSmoother<Node, Vector2>(new RayC(nodeMap));
        new IndexedAStarPathFinder<Node>(this).searchNodePath(start, End, costHeuristic, path);
        s.smoothPath(path);

        return path;
    }

    @Override
    public int getIndex(Node node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }
}
