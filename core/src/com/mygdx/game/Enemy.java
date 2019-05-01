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
    float speed;
    Sprite enemy = new Sprite(new Texture("ASSETS/SPRITES/1.png"));
    Body body;
    Rectangle rect;


    public Enemy() {
        enemy.setPosition(100, 100);

        speed = 10000;
        rect = new Rectangle((int) enemy.getX(), (int) enemy.getY(), (int) enemy.getX(), (int) enemy.getY());

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = Main.world.createBody(bodyDef);

        FixtureDef fixDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fixDef.shape = shape;

        shape.setAsBox(rect.width / 2 * Main.PPM, rect.height / 2 * Main.PPM);

        this.body.createFixture(fixDef);

        this.body.getFixtureList().get(0).setUserData("ENEMY");

        this.body.setTransform((float) rect.getX(), (float) rect.getY(), 0);

    }

    private void render(SpriteBatch batch) {
        enemy.draw(batch);

        rect = new Rectangle((int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth(), (int) enemy.getHeight());

        this.body.setTransform((float) rect.getX() * Main.PPM, (float) rect.getY() * Main.PPM, 0);

    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        this.render(batch);
//        enemy.setPosition(Main.WIDTH / 2 - enemy.getWidth(), Main.HEIGHT / 2 - enemy.getHeight());
        enemy.setPosition(100, 100);

    }


}
