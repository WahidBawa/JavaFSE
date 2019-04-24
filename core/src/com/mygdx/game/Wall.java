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

//        rect.x = rect.x * (1 / 2);
//        rect.y = rect.y * (1 / 2);
//        rect.width = rect.width * (1 / 2);
//        rect.height = rect.height * (1 / 2);

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
