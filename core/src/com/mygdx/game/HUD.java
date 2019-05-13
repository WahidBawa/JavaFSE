package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
    Sprite inventory = new Sprite(new Texture("ASSETS/UI/inventoryScreen.png"));
    public HUD(){

    }

    public void render(SpriteBatch batch){
        batch.draw(inventory, 0, 0);
    }

    public void update(SpriteBatch batch){


        render(batch);
    }
}
