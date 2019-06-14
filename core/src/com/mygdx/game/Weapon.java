/*
    Author: Wahid Bawa & Andi Morarescu
    Purpose: weapon class which holds all of the properties of weapons
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class  Weapon extends Item {
    public Weapon(String name, String type, int value, boolean stackable) { // uses parent class constructor to initialize
        super(name, type, value, stackable);
    }

    @Override
    public void use(Player player) {
        player.stats.put("Attack", (Integer) Main.player.stats.get("Attack") + value); // increases the players attack
    }

    @Override
    public Sprite getImg() { // returns image
        return super.getImg();
    }
}