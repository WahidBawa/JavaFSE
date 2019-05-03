package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.awt.*;

public class Player {
    float speed;
    Sprite player = new Sprite(new Texture("ASSETS/SPRITES/1.png"));
    Body body;
    Rectangle rect;

    public Player() {
        speed = 10000;

        player.setPosition(100, 100);

        rect = new Rectangle((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fdef.shape = shape;

        shape.setAsBox(rect.width / 2 * Main.PPM, rect.height / 2 * Main.PPM);

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("PLAYER");

        this.body.setTransform((float) rect.getX() * Main.PPM, (float) rect.getY() * Main.PPM, 0);
    }

    private void render(SpriteBatch batch) {
        batch.draw(player, body.getPosition().x - player.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - player.getHeight() * (float) Math.pow(Main.PPM, 2), player.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, player.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        player.setPosition(body.getPosition().x, body.getPosition().y);

        this.render(batch);
    }

    public float getX() {
        return player.getX();
    }

    public float getY() {
        return player.getY();
    }

    public float getWidth() {
        return player.getWidth();
    }

    public float getHeight() {
        return player.getHeight();
    }

    public void setX(float x) {
        player.setX(x);
    }

    public void setY(float y) {
        player.setY(y);
    }

    public Body getBody() {
        return body;
    }
}
