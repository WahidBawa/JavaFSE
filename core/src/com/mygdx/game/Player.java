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
    } // render image on screen

    public void update(SpriteBatch batch){ // all data will be updated here (pos, char states, etc)
        this.setX(x);
        this.setY(y);
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

    public void goLeft(){
        x -= 8;
    }
    public void goRight(){
        x += 8;
    }
    public void goUp(){
        y += 8;
    }
    public void goDown(){
        y -= 8;
    }
}
