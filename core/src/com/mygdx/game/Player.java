package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.awt.*;

public class Player {
    float x, y, speed;
    Sprite player = new Sprite(new Texture("ASSETS/SPRITES/1.png"));
    Body body;
    Rectangle rect;



    public Player() {
        x = Main.WIDTH / 2- player.getWidth();
        y = Main.HEIGHT / 2- player.getHeight();

        player.setPosition(x, y);

        speed = Main.speed;

        rect = new Rectangle((int) x, (int) y, (int) player.getWidth(), (int) player.getHeight());

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = Main.world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fdef.shape = shape;

        shape.setAsBox(rect.width / 2, rect.height / 2);

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("PLAYER");

        this.body.setTransform((float) rect.getX(), (float) rect.getY(), 0);

    }

    private void render(SpriteBatch batch) {
        player.draw(batch);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        player.setPosition(Main.WIDTH / 2- player.getWidth(), Main.HEIGHT / 2 - player.getHeight());

        rect.setLocation((int) x, (int) y);

        body.setTransform((float) rect.getX(), (float) rect.getY(), body.getAngle());

        System.out.println(this.body.getPosition());

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

    public void goLeft(){
        x += -speed;
    }
    public void goRight(){
        x += speed;
    }
    public void goDown(){
        y += -speed;
    }
    public void  goUp(){
        y += speed;
    }
}
