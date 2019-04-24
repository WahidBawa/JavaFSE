package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import com.badlogic.gdx.graphics.Color;

public class Wall {
    Rectangle rect;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public Wall(float x, float y, float width, float height){
        rect = new Rectangle((int) x, (int) y, (int) width, (int) height);
        MyGdxGame.walls.add(this);
    }

    public void update(){
        this.render();
    }

    public void render(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        shapeRenderer.end();
    }

    public Rectangle getRect(){
        return rect;
    }
}
