package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    Sprite player = new Sprite(new Texture("ASSETS/SPRITES/1.png"));
    Body body;
    Rectangle rect;

    private int counter = 0;
    private int animation_speed = 7;
    private int pos = 0;

    private ArrayList<ArrayList<Texture>> sprites = new ArrayList<ArrayList<Texture>>();
    private ArrayList<Texture> tmpSprites;


    HashMap stats = new HashMap();

    ArrayList<Item> inventory = new ArrayList<Item>();

    public Player() {
        createBody();

        loadSprites();


        stats.put("strength", 8);
        stats.put("agility", 6);
        stats.put("intelligence", 10);


        System.out.println(stats);

    }

    private void render(SpriteBatch batch) {
        batch.draw(player, body.getPosition().x - player.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - player.getHeight() * (float) Math.pow(Main.PPM, 2), player.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, player.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        player.setPosition(body.getPosition().x, body.getPosition().y);

        animationCount();

        player.set(new Sprite(sprites.get(Main.dir).get(pos)));

        this.render(batch);
    }

    public void createBody() {
        player.setPosition(500, 500);

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

    public void loadSprites() {
        for (String i : new String[]{"Up", "Down", "Left", "Right"}) {
            tmpSprites = new ArrayList<Texture>();
            for (int n = 0; n < 3; n++) {
                tmpSprites.add(new Texture("ASSETS/SPRITES/" + i + "/" + n + ".png")); // change this to current sprites
            }
            sprites.add(tmpSprites);
        }
    }

    public void animationCount() {
        if (Main.moving) {
            counter += 1;
            if (counter > animation_speed) {
                counter = 0;
                pos += 1;
                if (pos >= 3) {
                    pos = 0;
                }
            }
        } else {
            pos = 0;
        }
    }

    public float getX() {
        return player.getX();
    }

    public float getY() {
        return player.getY();
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
