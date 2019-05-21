package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

import static com.mygdx.game.Main.player;

public class CollisionListener implements ContactListener {

    public ArrayList<Fixture> objs;

    public CollisionListener() {
        objs = new ArrayList<Fixture>();
    }

    @Override
    public void beginContact(Contact contact) {

        if (contact.getFixtureA().getUserData() == "PLAYER" && contact.getFixtureB().getUserData().getClass() == Chest.class) {
            Main.chestCollide = true;
            objs.add(contact.getFixtureB());
        }
        if (contact.getFixtureA().getUserData() == "PLAYER" && contact.getFixtureB().getUserData() == "ENEMY") {
            ((Enemy) contact.getFixtureB().getBody().getUserData()).attack(player);
        }
        Main.objs = objs;
    }

    @Override
    public void endContact(Contact contact) {
        if (contact.getFixtureA().getUserData() == "PLAYER" && contact.getFixtureB().getUserData().getClass() == Chest.class) {
            Main.chestCollide = false;
            objs.remove(contact.getFixtureB());
        }
        Main.objs = objs;

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
