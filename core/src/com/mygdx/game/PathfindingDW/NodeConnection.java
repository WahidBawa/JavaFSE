package com.mygdx.game.PathfindingDW;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

public class NodeConnection implements Connection<Node>{
    private Node origin;
    private Node destination;
    private float cost;

    public NodeConnection(Node from, Node to) {
        this.origin = from;
        this.destination = to;
        cost = Vector2.dst(from.x, from.y, to.x, to.y);
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Node getFromNode() {
        return origin;
    }

    @Override
    public Node getToNode() {
        return destination;
    }
}
