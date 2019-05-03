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
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.awt.*;

public class Enemy {
    float speed = 10000;
    Sprite enemy = new Sprite(new Texture("ASSETS/SPRITES/goose.jpg"));
    Body body;
    Rectangle rect;


    public Enemy(float x, float y) {
        enemy.setPosition(x, y);

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fdef.shape = shape;

        shape.setAsBox(enemy.getWidth() * (float) Math.pow(Main.PPM, 2), enemy.getHeight() * (float) Math.pow(Main.PPM, 2));

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("Enemy");

        this.body.setTransform(enemy.getX() * Main.PPM, enemy.getY() * Main.PPM, 0);

//        this.body.setTransform(enemy.getX() * Main.PPM, enemy.getY() * Main.PPM, 0);

    }

    private void render(SpriteBatch batch) {
        batch.draw(enemy, body.getPosition().x - enemy.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - enemy.getHeight() * (float) Math.pow(Main.PPM, 2), enemy.getWidth() * (float) Math.pow(Main.PPM, 2)*2, enemy.getHeight() * (float) Math.pow(Main.PPM, 2)*2);

//        rect = new Rectangle((int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth(), (int) enemy.getHeight());

//        this.body.setTransform(enemy.getX() * Main.PPM * Main.PPM, enemy.getY() * Main.PPM * Main.PPM, 0);

    }

    public void update(SpriteBatch batch, Player player) { // all data will be updated here (pos, char states, etc)
        //enemy.setPosition(  (body.getPosition().x + player.getBody().getPosition().x) *(float)(-5.7) +Main.WIDTH/2 ,  (body.getPosition().y + player.getBody().getPosition().y) * (float) (-5.7) + Main.HEIGHT/2);
        Gdx.app.log("#INFO", "" + player.getBody().getPosition().x);
        this.render(batch);
//        enemy.setPosition(Main.player.getX() - Main.camera.position.x + 100, Main.player.getY() - Main.camera.position.y + 100);

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
