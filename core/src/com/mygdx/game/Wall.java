package com.mygdx.game;

import java.awt.*;

public class Wall {
    Rectangle rect;
    public Wall(float x, float y, float width, float height){
        rect = new Rectangle((int) x, (int) y, (int) width, (int) height);
        MyGdxGame.walls.add(this);
    }

    public void update(){

    }

    public void render(){

    }

    public Rectangle getRect(){
        return rect;
    }
}
