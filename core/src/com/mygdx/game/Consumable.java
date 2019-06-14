/*
    Author: Wahid Bawa & Andi Morarescu
    Purpose: stores the data and the methods for all consumable items
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Consumable extends Item {
    public Consumable(String name, String type, int value, boolean stackable) {
        super(name, type, value, stackable); // initializes using the Item class constructor
    }

    @Override
    public void use(Player player) { // uses the item
        player.stats.put((type.equals("health") ? "Health" : "Mana"), (Integer) Main.player.stats.get((type.equals("health") ? "Health" : "Mana")) + value); // adds to mana or health depending on the type of item
    }

    @Override
    public Sprite getImg() {
        return super.getImg();
    } // returns image
}
