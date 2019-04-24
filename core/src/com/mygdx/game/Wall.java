package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import com.badlogic.gdx.graphics.Color;

public class Wall {
    Rectangle rect;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public Wall(float x, float y, float width, float height){
        float floatWidth = width / (World.camera.viewportWidth / MyGdxGame.WIDTH);
        float floatHeight = height / (World.camera.viewportHeight / MyGdxGame.HEIGHT);

        float floatX = x / (World.camera.viewportWidth / MyGdxGame.WIDTH);
        float floatY = y / (World.camera.viewportHeight / MyGdxGame.HEIGHT);

        int newX = (int) floatX;
        int newY = (int) floatY;

        int newWidth = (int) floatWidth;
        int newHeight = (int) floatHeight;

        rect = new Rectangle(newX, newY, newWidth, newHeight);
//        rect = new Rectangle((int) x, (int) y, (int) width, (int) height);
        MyGdxGame.walls.add(this);
    }

    public void update(){
//        rect = new Rectangle(rect.x + (int) World.camera.position.x, rect.y + (int) World.camera.position.y, rect.width, rect.height);

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
