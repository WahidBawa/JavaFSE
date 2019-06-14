package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    Sprite player = new Sprite(new Texture("ASSETS/SPRITES/PLAYER/Sans/1.png"));
    Body body;
    Rectangle rect;

    private int counter = 0;
    private int animation_speed = 7;
    private int pos = 0;
    public boolean frozen = false;
    //player stats
    int health = 100;
    int mana = 100;
    int stamina = 100;
    Weapon weapon;
    Armour helmet, chest, legs, boots;

    private ArrayList<ArrayList<Texture>> sprites = new ArrayList<ArrayList<Texture>>();
    private ArrayList<Texture> tmpSprites;


    HashMap stats = new HashMap();
    HashMap<String, Item> equipment = new HashMap<String, Item>();

    ArrayList<Item> inventory = new ArrayList<Item>();

    public Player() {
        player.setSize(player.getWidth() / 1.5f, player.getHeight() / 1.5f);

        createBody();

        loadSprites();

        loadData();
    }

    private void render(SpriteBatch batch) {
        batch.draw(player, body.getPosition().x - player.getWidth() / 4 * (float) Math.pow(Main.PPM, 2), body.getPosition().y - player.getHeight() / 4 * (float) Math.pow(Main.PPM, 2), player.getWidth() * (float) Math.pow(Main.PPM, 2) / 2, player.getHeight() * (float) Math.pow(Main.PPM, 2) / 2);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        player.setPosition(body.getPosition().x, body.getPosition().y);

        animationCount();

        player.set(new Sprite(sprites.get(Main.dir).get(pos)));
        player.setSize(player.getWidth() / (float) 1.5, player.getHeight() / (float) 1.5);

        this.render(batch);
//        this.loadData();
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

        shape.setAsBox(rect.width * (float) Math.pow(Main.PPM, 2) / 4, rect.height * (float) Math.pow(Main.PPM, 2) / 4);

        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("PLAYER");

        this.body.setTransform((float) rect.getX() * Main.PPM, (float) rect.getY() * Main.PPM, 0);
        MassData thiccc = new MassData();
        thiccc.mass = 90f;//in kg
        this.body.setMassData(thiccc);

    }

    public void loadSprites() {
        for (String i : new String[]{"Up", "Down", "Left", "Right"}) {
            tmpSprites = new ArrayList<Texture>();

            for (int n = 0; n < 4; n++) {
                tmpSprites.add(new Texture("ASSETS/SPRITES/PLAYER/Sans/" + i + "/" + n + ".png")); // change this to current sprites
            }
            sprites.add(tmpSprites);
        }
    }

    public void lock() {

        frozen = true;

    }

    public void unLock() {
        frozen = false;
    }

    public void loadData() {
        stats.put("Health", 16);
        stats.put("Mana", 25);
        stats.put("Attack", 8 );
        stats.put("Defense", 11);
        stats.put("Speed", 6);
        stats.put("Smarts", 10);
//        stats.put("Inventory", inventory);
    }

    public void animationCount() {
        if (Main.moving) {
            counter += 1;
            if (counter > animation_speed) {
                counter = 0;
                pos += 1;
                if (pos >= 4) {
                    pos = 0;
                }
            }
        } else {
            pos = 1;
        }
    }

    public void receiveItem(Item item) {
        Main.inventory.addItem(item);
//        stats.put("inventory", inventory);
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

    public boolean isFrozen() {
        return frozen;
    }

    public void use(Item item) {
        item.use(this);
        Main.inventory.removeItem(item);
        inventory.remove(item);
    }

    public HashMap getStats() {
        return stats;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Armour getHelmet() {
        return helmet;
    }

    public Armour getChest() {
        return chest;
    }

    public Armour getLegs() {
        return legs;
    }

    public Armour getBoots() {
        return boots;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setHelmet(Armour helmet) {
        this.helmet = helmet;
    }

    public void setChest(Armour chest) {
        this.chest = chest;
    }

    public void setLegs(Armour legs) {
        this.legs = legs;
    }

    public void setBoots(Armour boots) {
        this.boots = boots;
    }
}
