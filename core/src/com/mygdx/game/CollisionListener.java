/*
    Author: Wahid Bawa & Andi Morarescu
    Purpose: handles all collisions that occur
 */
package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

import static com.mygdx.game.Main.player;

public class CollisionListener implements ContactListener {

    public ArrayList<Fixture> objs; // stores all the objects that are colliding

    public CollisionListener() {
        objs = new ArrayList<Fixture>();
    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() == "PLAYER") { // if the player is colliding with ...
            if (contact.getFixtureB().getUserData().getClass() == Chest.class) { // if the player is colliding with chest
                Main.chestCollide = true; // will set chestCollide to true
                objs.add(contact.getFixtureB()); // adds the object to objs
            } else if (contact.getFixtureB().getUserData().getClass() == NPC.class) { // if the player is colliding with NPCs
                Main.npcCollide = true; // sets npcCllide to true
                objs.add(contact.getFixtureB()); // adds to objs
            } else if (contact.getFixtureB().getUserData().getClass() == Portal.class) { // if the player is colliding with a portal
                objs.add(contact.getFixtureB()); // the portal is added to objs
            } else if (contact.getFixtureB().getUserData().getClass() == Quest_NPC.class){ // if the player collides with quest_npcs
                Main.npcQuestCollide = true; // sets npc quest collide to true
                objs.add(contact.getFixtureB()); // adds to objs
            }
            if (contact.getFixtureB().getUserData().getClass() == Enemy.class) { // if the player is colliding with the enemy
                ((Enemy) contact.getFixtureB().getBody().getUserData()).attack(player); // the enemy attacks the player
            }

        } else if (contact.getFixtureA().getUserData().getClass() == Enemy.class) { // if the enemy is clicked on
            if (contact.getFixtureB().getUserData() == "CLICK") {
                System.out.println("aaaa0" + contact.getFixtureA().getUserData());
                Enemy enemy = (Enemy) contact.getFixtureA().getUserData();
                enemy.damage((Integer) player.getStats().get("Attack")); // player damages the enemey
            }
        }

        Main.objs = objs; // sets the objs in main to the objs in this class
    }

    @Override
    public void endContact(Contact contact) { // used for resetting previous booleans and checks
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
            } else if (contact.getFixtureB().getUserData().getClass() == Quest_NPC.class){
                Main.npcQuestCollide = false;
                objs.add(contact.getFixtureB());
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