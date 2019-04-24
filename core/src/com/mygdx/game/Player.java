package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Player {
    int x, y;
    Sprite player = new Sprite(new Texture("badlogic.jpg"));
    Rectangle rect;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void render(SpriteBatch batch) {
        player.draw(batch);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        player.setX(x);
        player.setY(y);
        rect = new Rectangle((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight()); // creates a rect based on the sprite's dimensions

        this.render(batch);
    }

    public float getX(){
        return player.getX();
    }

    public float getY(){
        return player.getY();
    }

    public Rectangle getRect(){
        return rect;
    }

    public void goLeft() {
        x -= 1;
    }

    public void goRight() {
        x += 1;
    }

    public void goUp() {
        y += 1;
    }

    public void goDown() {
        y -= 1;
    }
}
