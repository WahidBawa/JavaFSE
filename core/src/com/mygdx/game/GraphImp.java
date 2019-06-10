package com.mygdx.game;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.DefaultIndecedGraph;
import com.badlogic.gdx.utils.Array;

public class GraphImp extends DefaultIndexedGraph<Node>{
    private Array<Node> nodes = new Array<Node>();

    public GraphImp() {
        super();
    }

    public GraphImp(int capacity) {
        super(capacity);
    }

    public GraphImp(Array<Node> nodes) {
        super(nodes);
        this.nodes = nodes;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return super.getConnections(fromNode);
    }

    @Override
    public int getNodeCount() {
        return super.getNodeCount();
    }

    public Node getNodeByXY(int x, int y) {
        int modX = x / LevelManager.tilePixelWidth;//tiledPixelWidth
        int modY = y / LevelManager.tilePixelHeight;

        return nodes.get(LevelManager.lvlTileWidth +modY + modX);
    }
}
