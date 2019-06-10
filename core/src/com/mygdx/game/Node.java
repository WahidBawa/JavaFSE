package com.mygdx.game;
import com.badlogic.gdx.ai.pfa.Connection;
import  com.badlogic.gdx.utils.Array;
import java.util.Iterator;


    public class Node implements IndexedNode<Node> {
    private Array<Connection<Node>> connections = new Array<Connection<Node>>();
    public int type;
    public int index;

    public Node() {
        index = Node.Indexer.getIndex();
    }


    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public void createConnections(Node toNode, float coat) {
        connections.add(new ConnectionImp(this, toNode, coat));
    }

    private static class Indexer {
        private static int index = 0;

        public static  int getIndex() {
            return index++;
        }
    }
    public static class type {
        public static final int REDULAR = 1;
    }
}
