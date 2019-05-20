package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item {
    String name, type;
    int value;
    Sprite img;

    public Item(String name, String type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
        img = new Sprite(new Texture("ASSETS/INVENTORY/ITEMS/" + name + ".png"));
    }

    public void use(Player player) {

    }

    public Sprite getImg(){
        return img;
    }
}
