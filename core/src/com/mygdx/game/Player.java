package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

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

    //player stats
    int health = 100;
    int mana = 100;
    int stamina = 100;


    private ArrayList<ArrayList<Texture>> sprites = new ArrayList<ArrayList<Texture>>();
    private ArrayList<Texture> tmpSprites;


    HashMap stats = new HashMap();
    HashMap<String, Item> equipment = new HashMap<String, Item>();

    ArrayList<Item> inventory = new ArrayList<Item>();

    public Player() {
        createBody();

        loadSprites();

        stats.put("health", 16);
        stats.put("mana", 25);
        stats.put("attack", 8);
        stats.put("defense", 11);
        stats.put("speed", 6);
        stats.put("smarts", 10);
        stats.put("inventory", inventory);

    }

    private void render(SpriteBatch batch) {
        batch.draw(player, body.getPosition().x - player.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - player.getHeight() * (float) Math.pow(Main.PPM, 2), player.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, player.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        player.setPosition(body.getPosition().x, body.getPosition().y);

        animationCount();

        player.set(new Sprite(sprites.get(Main.dir).get(pos)));
//        System.out.println(stats.get("inventory"));

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

        shape.setAsBox(rect.width * (float) Math.pow(Main.PPM, 2), rect.height * (float) Math.pow(Main.PPM, 2));

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("PLAYER");
        this.body.getFixtureList().get(0).setUserData("PLAYER");

        this.body.setTransform((float) rect.getX() * Main.PPM, (float) rect.getY() * Main.PPM, 0);
        MassData thiccc = new MassData();
        thiccc.mass = 90f;//in kg
        this.body.setMassData(thiccc);
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
            pos = 1;
        }
    }

    public void receiveItem(Item item) {
        inventory.add(item);
        stats.put("inventory", inventory);
    }
    public void setHealth(int hp) {
        health = hp;
    }

    public int getHealth() {
        return health;
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

    public Object get(String key) {
        return stats.get(key);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void use(Item item) {
        item.use();
        inventory.remove(item);

    }
}
