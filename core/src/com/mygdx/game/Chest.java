package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.HashMap;

public class Chest {

    Sprite chest = new Sprite(new Texture("ASSETS/CHESTS/0.png"));
    Body body;
    Rectangle rect;

    BitmapFont font = new BitmapFont(Gdx.files.internal("ASSETS/FONTS/myFont.fnt"), false);
    Sprite textBox = new Sprite(new Texture("ASSETS/UI/DIALOGUE_BOX/box2.png"));

    boolean chestOpened = false;
    boolean textFinished = false;
    boolean go = false;

    String name, item;

    public Chest(Rectangle rect, String name, String item) {
        chest.setPosition(rect.x, rect.y);

        this.rect = rect;

        createBody();

        this.name = name; // name will be used to differentiate the chest and to tell if it has been opened before
        this.item = item;
    }

    public void render(SpriteBatch batch) {
        batch.draw(chest, body.getPosition().x - chest.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - chest.getHeight() * (float) Math.pow(Main.PPM, 2), chest.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, chest.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);

    }

    public void update(SpriteBatch batch) {
        this.render(batch);
    }

    public void createBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fdef.shape = shape;

        shape.setAsBox(chest.getWidth() * (float) Math.pow(Main.PPM, 2), chest.getHeight() * (float) Math.pow(Main.PPM, 2));

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData(this);

        this.body.setTransform(chest.getX() * Main.PPM, chest.getY() * Main.PPM, 0);
    }

    public void open(SpriteBatch batch) {
        String[] split = item.split("//");
        String name = split[0];
        HashMap tmp = (Main.weapons.get(name) != null ? Main.weapons.get(name) : Main.consumables.get(name));

        if (!chestOpened) {
            if (Main.consumables.get(name) != null) {
                Main.player.receiveItem(new Consumable(name, (Integer) tmp.get("stat") == 1 ? "health" : "mana", (Integer) tmp.get("replenishAmount"), true));
            } else if (Main.weapons.get(name) != null) {
                Main.player.receiveItem(new Weapon(name, null, (Integer) tmp.get("damage"), false));
            }
            chest.set(new Sprite(new Texture("ASSETS/CHESTS/1.png")));
            chestOpened = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if (go) textFinished = true;
            go = true;
        }

        textBox.draw(batch);
        font.setColor(Color.RED);
        font.draw(batch, "Chest", 10, 180 + font.getCapHeight());
        font.setColor(Color.WHITE);
        font.draw(batch, "You received " + name + " from chest!!", 112, 140 + font.getCapHeight());

    }

    public Body getBody(){
        return body;
    }
}
