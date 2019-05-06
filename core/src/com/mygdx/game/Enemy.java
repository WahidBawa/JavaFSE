package com.mygdx.game;
//this is the Enemy class the objects are Enemys that:
//attack you
//can be attacked by you
//will pathfind towards you
//will spawn in certain locations
//will drop loot that can be picked up
//they all have cool names


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.awt.*;
import java.util.Random;

public class Enemy {
    float speed = 10000;
    Sprite enemy = new Sprite(new Texture("ASSETS/SPRITES/goose.jpg"));
    Body body;
    Random rand = new Random();


    public Enemy(Rectangle rect) {
        enemy.setPosition(rect.x, rect.y);
        createBody();
    }

    private void render(SpriteBatch batch) {
        batch.draw(enemy, body.getPosition().x - enemy.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - enemy.getHeight() * (float) Math.pow(Main.PPM, 2), enemy.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, enemy.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        this.render(batch);

    }

    public void createBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fdef.shape = shape;

        shape.setAsBox(enemy.getWidth() * (float) Math.pow(Main.PPM, 2), enemy.getHeight() * (float) Math.pow(Main.PPM, 2));

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("Enemy");

        this.body.setTransform(rand.nextInt(Main.MAP_WIDTH - (int) enemy.getWidth()) * Main.PPM, rand.nextInt(Main.MAP_HEIGHT - (int) enemy.getHeight()) * Main.PPM, 0);
    }
    public void move(Player player) {
        if (Math.pow(body.getPosition().x -player.getBody().getPosition().x,2) + Math.pow(body.getPosition().y - player.getBody().getPosition().y,2) < 20000) {
            //body.setTransform(speed, speed, body.getWorldCenter().angle(player.getBody().getWorldCenter()));
            Gdx.app.log("#INFO", "" + Main.PPM);
            //enemy.setPosition(enemy.getX() +(enemy.getX() - player.getSprite().getX() / Math.abs(enemy.getX() - player.getSprite().getX())) * speed, enemy.getY() + (enemy.getY() - player.getSprite().getY()/ Math.abs(enemy.getY() - player.getSprite().getY())*speed));
            //enemy.setPosition(enemy.getX() + 10, enemy.getY() + 10);
            //body.setTransform((-10 * (body.getWorldCenter().x - player.getBody().getWorldCenter().x) / Math.abs(body.getWorldCenter().x - player.getBody().getWorldCenter().x)), (-10 * (body.getWorldCenter().y - player.getBody().getWorldCenter().y) / Math.abs(body.getWorldCenter().y - player.getBody().getWorldCenter().y)),0);
            // body.setTransform((-10 * (body.getWorldCenter().x - player.getBody().getWorldCenter().x) / Math.abs(body.getWorldCenter().x - player.getBody().getWorldCenter().x)), 0,0);
            //body.setTransform(-10,-10,0);
            body.applyLinearImpulse(new Vector2((-10 * (body.getWorldCenter().x - player.getBody().getWorldCenter().x) / Math.abs(body.getWorldCenter().x - player.getBody().getWorldCenter().x)), (-10 * (body.getWorldCenter().y - player.getBody().getWorldCenter().y) / Math.abs(body.getWorldCenter().y - player.getBody().getWorldCenter().y))), body.getWorldCenter(),true);

        }
    }

    public float getX() {
        return enemy.getX();
    }

    public float getY() {
        return enemy.getY();
    }

    public void setX(float x) {
        enemy.setX(x);
    }

    public void setY(float y) {
        enemy.setY(y);
    }

    public Body getBody() {
        return body;
    }


}
