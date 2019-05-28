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
    public Portal(Rectangle rect, String type){
        this.rect = rect;
        this.type = type;
        createBody();
    }


    public void createBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fdef.shape = shape;

        shape.setAsBox(rect.getWidth() * (float) Math.pow(Main.PPM, 2), rect.getHeight() * (float) Math.pow(Main.PPM, 2));

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData(this);

        this.body.setTransform(rect.getX() * Main.PPM, rect.getY() * Main.PPM, 0);
    }
}
