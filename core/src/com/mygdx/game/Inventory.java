package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Inventory {
    Sprite inventory = new Sprite(new Texture("ASSETS/INVENTORY/Inventory.png"));
    Item[][] items = new Item[3][7];

    public Inventory(){
//        inventory.setSize(inventory.getWidth() * Main.PPM, inventory.getHeight() * Main.PPM);
    }

    public void render(SpriteBatch batch){
//        batch.draw(inventory, 0, 0, inventory.getWidth() * 0.2f, inventory.getHeight() * 0.2f);
        inventory.setPosition(0, 0);
        inventory.draw(batch);
    }

    public void update(SpriteBatch batch){

        render(batch);
    }

    public void open(){
        int pos = 0;
        for (int i = 0; i < Main.player.getInventory().size(); i++){
            items[pos][i - pos * 7] = Main.player.getInventory().get(i);
            if (i % 7 == 0){
                pos++;
            }
        }
    }

    public Sprite getSprite() {
        return inventory;
    }
}
