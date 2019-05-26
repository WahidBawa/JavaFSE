package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class NPC {
    Body body;
    Sprite npc = new Sprite(new Texture("ASSETS/SPRITES/NPC/0/DOWN.png"));
    String name, dialogue, item;
    public NPC(Rectangle rect, String name, String dialogue, String item){
        this.name = name;
        this.dialogue = dialogue;
        this.item = item;
        System.out.println("UEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        npc.setPosition(rect.getX(), rect.getY());
        npc.setSize(npc.getWidth() * 2, npc.getHeight() * 2);
        createBody();
    }

    public void render(SpriteBatch batch){
        batch.draw(npc, body.getPosition().x - npc.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - npc.getHeight() * (float) Math.pow(Main.PPM, 2), npc.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, npc.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);

    }

    public void update(SpriteBatch batch){

        render(batch);
    }

    public void createBody() {
        BodyDef bdef = new BodyDef();
        FixtureDef def = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;

        bdef.position.set(npc.getX() * Main.PPM + npc.getWidth() / 2 * Main.PPM, npc.getY() * Main.PPM + npc.getHeight() / 2 * Main.PPM);

        body = Main.world.createBody(bdef);

        shape.setAsBox(npc.getWidth() / 2 * Main.PPM, npc.getHeight() / 2 * Main.PPM);

        def.shape = shape;

        this.body.createFixture(def);

        this.body.getFixtureList().get(0).setUserData(this);

    }

    public void talk(){
        System.out.println(dialogue);
    }
}
