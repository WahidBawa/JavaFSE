package com.mygdx.game;

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
        if (contact.getFixtureA().getUserData() == "PLAYER") {
            if (contact.getFixtureB().getUserData().getClass() == Chest.class) {
                Main.chestCollide = true;
                objs.add(contact.getFixtureB());
            } else if (contact.getFixtureB().getUserData().getClass() == NPC.class) {
                Main.npcCollide = true;
                objs.add(contact.getFixtureB());
            } else if (contact.getFixtureB().getUserData().getClass() == Portal.class) {
                objs.add(contact.getFixtureB());
            }
            if (contact.getFixtureB().getUserData().getClass() == Enemy.class) {
                ((Enemy) contact.getFixtureB().getBody().getUserData()).attack(player);
            }

        } else if (contact.getFixtureA().getUserData().getClass() == Enemy.class) {
            if (contact.getFixtureB().getUserData() == "CLICK") {
                System.out.println("aaaa0" + contact.getFixtureA().getUserData());
                Enemy enemy = (Enemy) contact.getFixtureA().getUserData();
                enemy.damage((Integer) player.getStats().get("Attack"));
            }
        }

        Main.objs = objs;
    }

    @Override
    public void endContact(Contact contact) {
        Main.interactable = true;
        if (contact.getFixtureA().getUserData() == "PLAYER") {
            if (contact.getFixtureB().getUserData().getClass() == Chest.class) {
                Main.chestCollide = false;
                objs.remove(contact.getFixtureB());
            } else if (contact.getFixtureB().getUserData().getClass() == NPC.class) {
                Main.npcCollide = false;
                objs.remove(contact.getFixtureB());
            } else if (contact.getFixtureB().getUserData().getClass() == Portal.class) {
                objs.remove(contact.getFixtureB());
            }
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