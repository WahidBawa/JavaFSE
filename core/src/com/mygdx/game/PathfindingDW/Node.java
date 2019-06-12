package com.mygdx.game.PathfindingDW;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class Node {
    public int x;
    public int y;
    private int index;
    private Array<Connection<Node>> connections;

    public Node(int x, int y, int index){
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public void setConnections(Array<Connection<Node>> connections) {
        this.connections = connections;
    }

    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public int getIndex() {
        return index;
    }
}
