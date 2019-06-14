package com.mygdx.game;
//Enemy class
//This class ints the the Enemys (geese)
//These Enemys are dynamic bodies that have as goal to kill the player
//they will move towards the player and they  collide with other entities on the map such as chests, npcs, the player, walls
//
//

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Enemy {
    //inits variables
    float speed = 6;
    Sprite enemy = new Sprite(new Texture("ASSETS/SPRITES/ENEMIES/purple_bird/DOWN/1.png"));
    Body body;//using box2d for collision
    Random rand = new Random();
    boolean seen;
    public boolean frozen = false;
    Timer time = new Timer();
    int health = 5;


    public Enemy(Rectangle rect) {//takes a rect and changes the rect to the size of the sprite
        enemy.setPosition(rect.x, rect.y);//set tto the start position
        enemy.setSize(enemy.getWidth() * 2, enemy.getHeight() * 2);//doubles size
        createBody();
    }

    private void render(SpriteBatch batch) {//renders the enemy and scales it to the maps tiles size using the Main.PPM variable
        batch.draw(enemy, body.getPosition().x - enemy.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - enemy.getHeight() * (float) Math.pow(Main.PPM, 2), enemy.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, enemy.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);
        if (health <= 0) {//since this is in the main loop it also checks if the enemy dies and kills it
            this.kill();
        }
    }
    public void kill() {//where the enemy body would be removed

    }


    public void update(SpriteBatch batch) { // all data will be updated here (pos, char states, etc)
        this.render(batch);

    }

    public void createBody() {
        seen = false;
        BodyDef bdef = new BodyDef();//for the types of body
        bdef.type = BodyDef.BodyType.DynamicBody;//Dynamic best suits the enemys which will move and have forces applied on them
        this.body = Main.world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();//for the collision  we need to define it as Enemy
        PolygonShape shape = new PolygonShape();
        fdef.shape = shape;//makes it a hitbox
        shape.setAsBox(enemy.getWidth() * (float) Math.pow(Main.PPM, 2), enemy.getHeight() * (float) Math.pow(Main.PPM, 2));
        this.body.createFixture(fdef);//defines the collision data
        MassData massOfEnemy = new MassData();//inits mass
        massOfEnemy.mass = (float) (6.5 + 5.5) / 2;//took the avrarage mass of a male and female goose to to discriminate against a certain sex
        this.body.setMassData(massOfEnemy);//defines the mass
        this.body.setUserData(this);//defines its collision data as itself so that we can easily access all of its data in the collision file

        this.body.setTransform(rand.nextInt(Main.MAP_WIDTH - (int) enemy.getWidth()) * Main.PPM, rand.nextInt(Main.MAP_HEIGHT - (int) enemy.getHeight()) * Main.PPM, 0);//enemy starts at a random part of the map
    }
    public void lock() {
        frozen = true;
    }//lock the animation so it doest move on command
    public void unLock() {
        frozen = false;
    }//unlocks it and lets it move freely

    public void encounter(Player player) {//similar to pokemon there is a dramatic zoom in when the player gets close to an enemy
        if (!seen && Math.pow(body.getPosition().x - player.getBody().getPosition().x, 2) + Math.pow(body.getPosition().y - player.getBody().getPosition().y, 2) < 500) { // if you are withing 500 units close to player
            player.lock();//locks the player
            this.lock();//locks itself
            Main.PPM +=0.1;
            Main.camera.update();

            if (Main.PPM > 5) {//zooms util it reaches a certain zoom and pauses

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                seen = true;
                Main.PPM = 0.3f;//resets it
            }


            player.unLock();//unlocks
            this.unLock();//unlocks
        }
        if (seen) {
            move(player);
        }
     }
    public void move(Player player) {
        if (!frozen) {

            if (Math.pow(body.getPosition().x - player.getBody().getPosition().x, 2) + Math.pow(body.getPosition().y - player.getBody().getPosition().y, 2) < 3000) {//if within 3000 units of player it will move in a straight line towards the player
                body.applyLinearImpulse(new Vector2((-speed * body.getMass() * (body.getWorldCenter().x - player.getBody().getWorldCenter().x) / Math.abs(body.getWorldCenter().x - player.getBody().getWorldCenter().x)), (-speed * body.getMass() * (body.getWorldCenter().y - player.getBody().getWorldCenter().y) / Math.abs(body.getWorldCenter().y - player.getBody().getWorldCenter().y))), body.getWorldCenter(), true);
            } else {
                body.applyLinearImpulse(new Vector2((0), (0)), body.getWorldCenter(), true);// if not moving towards the player stop it sets the body in place

            }
        }

    }

    public void attack(Player player) {
        player.setHealth(player.getHealth() - 5);//damage the player
    }
    public void damage(int damage) {
        health -= damage;
    }//damage to itself
    //getter setters
    public float getX() {
        return enemy.getX();
    }

    public float getY() {
        return enemy.getY();
    }

    public void setX(float x) {
        enemy.setX(x);
    }

    public void setY(float y) {
        enemy.setY(y);
    }

    public Body getBody() {
        return body;
    }


}
