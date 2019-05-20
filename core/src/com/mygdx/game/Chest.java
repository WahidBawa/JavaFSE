package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.HashMap;

public class Chest {
    boolean chestOpened = false;

    Sprite chest = new Sprite(new Texture("ASSETS/CHESTS/0.png"));
    Body body;
    Rectangle rect;

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

    public void open() {
        if (!chestOpened) {
            String[] split = item.split("//");
            String name = split[0];
            HashMap tmp;
            if (Main.consumables.get(name) != null) {
                tmp = Main.consumables.get(name);
                Main.player.receiveItem(new Consumable(name, (Integer) tmp.get("stat") == 1 ? "health" : "mana", (Integer) tmp.get("replenishAmount"), (Integer) tmp.get("stackable") == 1));
            } else if (Main.weapons.get(name) != null) {
                tmp = Main.weapons.get(name);
                Main.player.receiveItem(new Weapon(name, null, (Integer) tmp.get("damage"), (Integer) tmp.get("stackable") == 1));
            }
//            else if (split[1].equals("A")) {
//                Main.player.receiveItem(new Armour(split[0], split[2], Integer.parseInt(split[3])));
//            }
        }

        chest.set(new Sprite(new Texture("ASSETS/CHESTS/1.png")));
        chestOpened = true;
    }
}
