package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public class Player {
    float x, y, speed;
    Sprite player = new Sprite(new Texture("ASSETS/SPRITES/1.png"));
    Body body;
    Rectangle rect;



    public Player() {
        x = 0;
        y = 0;

        player.setPosition(x, y);

        speed = Float.MAX_VALUE;

        rect = new Rectangle((int) x, (int) y, (int) player.getWidth(), (int) player.getHeight());

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fdef.shape = shape;

        shape.setAsBox(rect.width / 2  * Main.PPM, rect.height / 2  * Main.PPM);

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("PLAYER");

        this.body.setTransform((float) rect.getX()  * Main.PPM, (float) rect.getY()  * Main.PPM, 0);

    }

    private void render(SpriteBatch batch) {
        player.draw(batch);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        player.setPosition(Main.WIDTH / 2- player.getWidth(), Main.HEIGHT / 2 - player.getHeight());

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

    public Body getBody(){
        return body;
    }

    public void goLeft(){
        body.applyLinearImpulse(new Vector2(-10000 * 2, 0), body.getWorldCenter(), true);
    }
    public void goRight(){
        body.applyLinearImpulse(new Vector2(10000 * 2, 0), body.getWorldCenter(), true);
    }
    public void goDown(){
        body.applyLinearImpulse(new Vector2(0, -10000 * 2), body.getWorldCenter(), true);
    }
    public void  goUp(){
        body.applyLinearImpulse(new Vector2(0, 10000 * 2), body.getWorldCenter(), true);
    }
}
