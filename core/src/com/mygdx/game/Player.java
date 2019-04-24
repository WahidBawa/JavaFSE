package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Player {
    int x, y, speed;
    Sprite player = new Sprite(new Texture("ASSETS/SPRITES/1.png"));
    Rectangle rect;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        speed = MyGdxGame.speed;
    }

    private void render(SpriteBatch batch) {
        player.draw(batch);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)

        player.setX(World.camera.position.x - player.getWidth() / 2);
        player.setY(World.camera.position.y - player.getHeight() / 2);

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

    public void goLeft(){
        x += -speed;
    }
    public void goRight(){
        x += speed;
    }
    public void goDown(){
        y += -speed;
    }
    public void  goUp(){
        y += speed;
    }
}
