package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class CollisionListener implements ContactListener {

    public CollisionListener() {
        System.out.println("usu");
    }

    @Override
    public void beginContact(Contact contact) {
//        for (Body i : Main.wc.getAsList()) {
//            if ((contact.getFixtureA().getBody() == Main.player.getBody() && contact.getFixtureB().getBody() == i) || (contact.getFixtureA().getBody() == i && contact.getFixtureB().getBody() == Main.player.getBody())) {
//                // this is just sample code of how you would do shit if you hit a specific block
//            }
//        }
//        System.out.println(contact.isTouching());
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
