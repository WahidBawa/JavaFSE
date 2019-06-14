package com.mygdx.game;
//Player Class
//inits the Player entity
//This is the main player that you control as you play this game
//this class holds all of stats of the character and and the methods need to for this character
//
//
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Player {
    //class fields
    Sprite player = new Sprite(new Texture("ASSETS/SPRITES/PLAYER/Sans/1.png"));
    Body body;
    Rectangle rect;
    private int counter = 0;
    private int animation_speed = 7;
    private int pos = 0;
    public boolean frozen = false;//if frozen true player will appear frozen
    //player stats
    int health = 100;
    int mana = 100;
    int stamina = 100;
    Weapon weapon;
    Armour helmet, chest, legs, boots;

    private ArrayList<ArrayList<Texture>> sprites = new ArrayList<ArrayList<Texture>>();//different sprites for different directions of moment
    private ArrayList<Texture> tmpSprites;


    HashMap stats = new HashMap();

    ArrayList<Item> inventory = new ArrayList<Item>();

    public Player() {//constructor
        player.setSize(player.getWidth() / 1.5f, player.getHeight() / 1.5f);//shrinks the player sprite

        createBody();//creates body2d body

        loadSprites();

        loadData();//data as in player stats
    }

    private void render(SpriteBatch batch) {//the method that runs in the main loop draws player on screen adjusted to the map size using Main.PPM and translated to the middle of the screen
        batch.draw(player, body.getPosition().x - player.getWidth() / 4 * (float) Math.pow(Main.PPM, 2), body.getPosition().y - player.getHeight() / 4 * (float) Math.pow(Main.PPM, 2), player.getWidth() * (float) Math.pow(Main.PPM, 2) / 2, player.getHeight() * (float) Math.pow(Main.PPM, 2) / 2);
    }

    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        player.setPosition(body.getPosition().x, body.getPosition().y);//sets to its current position

        animationCount();//keeps track of every frame on witch in order to display a walking animation

        player.set(new Sprite(sprites.get(Main.dir).get(pos)));//dir being the diction that the character is moving towards on the screen, pos bing on witch frame of the walking animation
        player.setSize(player.getWidth() / 1.5f, player.getHeight() / 1.5f);//shrinks the character by desired value

        this.render(batch);//draws the player on the screen
    }

    public void createBody() {//constructor

        player.setPosition(500, 500);//start position
        rect = new Rectangle((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;//dynamic body suits it best as it facilitates collision and for a body that need to move and have forces applied onto it
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        fdef.shape = shape;
        shape.setAsBox(rect.width * (float) Math.pow(Main.PPM, 2) / 4, rect.height * (float) Math.pow(Main.PPM, 2) / 4);//make character hitbox into a box sized to the sprite
        this.body.createFixture(fdef);

        this.body.getFixtureList().get(0).setUserData("PLAYER");//do identify the during collision as a player

        this.body.setTransform((float) rect.getX() * Main.PPM, (float) rect.getY() * Main.PPM, 0);//scales it to the map tile size using Main.PPM
        MassData mass = new MassData();
        mass.mass = 90f;//in kg
        this.body.setMassData(mass);
    }

    public void loadSprites() {//adds all the the different spite frames into the sprites arraylist
        for (String i : new String[]{"Up", "Down", "Left", "Right"}) {//individually loops through the sprites of every different direction using a temporary arraylist
            tmpSprites = new ArrayList<Texture>();

            for (int n = 0; n < 4; n++) {
                tmpSprites.add(new Texture("ASSETS/SPRITES/PLAYER/Sans/" + i + "/" + n + ".png")); // change this to current sprites
            }
            sprites.add(tmpSprites);
        }
    }

    public void lock() {//locks the character place so he doesnt move on the scrren
        frozen = true;
    }

    public void unLock() {
        frozen = false;
    }//unlocks

    public void loadData() {//load the stats in the the states hash tables
        stats.put("Health", 16);
        stats.put("Mana", 25);
        stats.put("Attack", 8 );
        stats.put("Defense", 11);
        stats.put("Speed", 6);
        stats.put("Smarts", 10);
        stats.put("Inventory", inventory);
    }

    public void animationCount() {//keeps track of what frame of animation they are one using the pos variable
        if (Main.moving) {//Main.moving is the player is not stationary
            counter += 1;
            if (counter > animation_speed) {// it takes 7 tick to swish to next frame
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

    public void receiveItem(Item item) {//when receiving an item from npc or chest this is used to put in inventory
        Main.inventory.addItem(item);//put item in the inventory
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

    public boolean isFrozen() {
        return frozen;
    }

    public void use(Item item) {//to consume an item like a potion
        item.use(this);//the affect it has on player
        Main.inventory.removeItem(item);//removes the one time use item from inventory form main which is used to draw on the screen
        inventory.remove(item);//removes from local player inventory
    }

    //getter and setters
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