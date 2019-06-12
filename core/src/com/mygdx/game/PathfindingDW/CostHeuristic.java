package com.mygdx.game.PathfindingDW;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class CostHeuristic implements Heuristic<Node> {
    @Override
    public float estimate(Node node, Node endNode) {
        return Vector2.dst(node.x, node.y, endNode.x, endNode.y);
    }
}
