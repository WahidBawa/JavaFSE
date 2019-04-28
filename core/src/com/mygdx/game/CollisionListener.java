package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class CollisionListener implements ContactListener {

    public CollisionListener () {
        System.out.println("usu");
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        for (Body i : Main.wc.getAsList()){
            if((contact.getFixtureA().getBody() == Main.player.getBody() && contact.getFixtureB().getBody() == i) || (contact.getFixtureA().getBody() == i && contact.getFixtureB().getBody() == Main.player.getBody())) {
                System.out.println("uwu");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
