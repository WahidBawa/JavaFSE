package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Portal {
    Body body;
    Rectangle rect;
    String type;
    float newX, newY;
    public Portal(Rectangle rect, String type, int newX, int newY){
        this.rect = rect;
        this.type = type;
        this.newX = newX;
        this.newY = newY;
        createBody();
    }


    public void createBody() {
        BodyDef bdef = new BodyDef();
        FixtureDef def = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;

        bdef.position.set(rect.getX() * Main.PPM + rect.getWidth() / 2 * Main.PPM, rect.getY() * Main.PPM + rect.getHeight() / 2 * Main.PPM);

        body = Main.world.createBody(bdef);

        shape.setAsBox(rect.getWidth() / 2 * Main.PPM, rect.getHeight() / 2 * Main.PPM);

        def.shape = shape;

        this.body.createFixture(def);

        this.body.getFixtureList().get(0).setUserData(this);
    }

    public Body getBody() {
        return body;
    }

    public String getType() {
        return type;
    }

    public float getNewX() {
        return newX;
    }

    public float getNewY() {
        return newY;
    }
}
