package com.mygdx.game;
//this is the Enemy class the objects are Enemys that:
//attack you
//can be attacked by you
//will pathfind towards you
//will spawn in certain locations
//will drop loot that can be picked up
//they all have cool names


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.awt.*;

public class Enemy {
    float x, y, speed;
    Sprite enemy = new Sprite(new Texture("ASSETS/SPRITES/goose.jpg"));
    Body body;
    Rectangle rect;


    public Enemy() {
        x = 100;
        y = 100;
        enemy.setPosition(x,y);

        speed = 10000;
        rect = new Rectangle((int) x, (int) y, (int) enemy.getWidth(), (int) enemy.getHeight());

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = Main.world.createBody(bodyDef);

        FixtureDef fixDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fixDef.shape = shape;

        shape.setAsBox(20, 20);

        this.body.createFixture(fixDef);

        //this.body.getFixtureList().get(0).setUserData("PLAYER");

        //this.body.setTransform((float) rect.getX(), (float) rect.getY(), 0);

    }
    private void render(SpriteBatch batch) {
        enemy.draw(batch);

    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        this.render(batch);
        //enemy.setPosition(Main.WIDTH / 2- enemy.getWidth(), Main.HEIGHT / 2 - enemy.getHeight());

    }




}
