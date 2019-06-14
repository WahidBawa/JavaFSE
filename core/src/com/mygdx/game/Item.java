/*
    Author: Wahid Bawa & Andi Morarescu
    Purpose: Is a parent class to all other types of items
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Item {
    String name, type; // stores name and type
    int value; // stores the values, this can either be health, mana, or attack damage
    Sprite img; // sprite of the object
    boolean stackable;

    public Item(String name, String type, int value, boolean stackable) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.stackable = stackable;
        img = new Sprite(new Texture("ASSETS/UI/INVENTORY/ITEMS/" + name + ".png")); // loads the appropriate sprite
        img.setSize(40, 40); // sets the size
    }

    public void use(Player player) {

    }

    public Sprite getImg() {
        return img;
    }
}
