package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Chest {
    Sprite chest = new Sprite(new Texture("ASSETS/CHESTS/0.png"));
    Body body;
    public Chest(Rectangle rect){
        chest.setPosition(rect.x, rect.y);

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fdef.shape = shape;

        shape.setAsBox(chest.getWidth() * (float) Math.pow(Main.PPM, 2), chest.getHeight() * (float) Math.pow(Main.PPM, 2));

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("Enemy");

        this.body.setTransform(chest.getX() * Main.PPM, chest.getY() * Main.PPM, 0);
    }

    public void render(SpriteBatch batch){
        batch.draw(chest, body.getPosition().x - chest.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - chest.getHeight() * (float) Math.pow(Main.PPM, 2), chest.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, chest.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);

    }

    public void update(SpriteBatch batch){
        this.render(batch);
    }
}
