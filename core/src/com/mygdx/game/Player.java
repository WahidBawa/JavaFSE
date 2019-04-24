package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    int x, y;
    Texture player = new Texture("badlogic.jpg");

    public Player(int x, int y){
        this.x = x;
        this.y = y;
    }

    private void render(SpriteBatch batch){
        batch.draw(player, x, y);
    }

    public void update(SpriteBatch batch){

        this.render(batch);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
