package com.mygdx.game.PathfindingDW;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class sGraphPath extends DefaultGraphPath<Node> implements SmoothableGraphPath<Node, Vector2> {
    @Override
    public Vector2 getNodePosition(int index) {
        return new Vector2(nodes.get(index).x, nodes.get(index).y);
    }

    @Override
    public void swapNodes(int index1, int index2) {
        nodes.set(index1, nodes.get(index2));
    }

    @Override
    public void truncatePath(int newLength) {
        nodes.truncate(newLength);
    }
}
