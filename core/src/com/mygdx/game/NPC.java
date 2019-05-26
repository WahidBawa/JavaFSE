//package com.mygdx.game;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.physics.box2d.*;
//
//public class NPC {
//    Body body;
//    Sprite npc = new Sprite(new Texture(""));
//    public NPC(){
//        createBody();
//    }
//
//    public void createBody() {
//        BodyDef bdef = new BodyDef();
//        bdef.type = BodyDef.BodyType.DynamicBody;
//        this.body = Main.world.createBody(bdef);
//        FixtureDef fdef = new FixtureDef();
//        PolygonShape shape = new PolygonShape();
//
//        fdef.shape = shape;
//
//        shape.setAsBox(npc.getWidth() * (float) Math.pow(Main.PPM, 2), npc.getHeight() * (float) Math.pow(Main.PPM, 2));
//
//        this.body.createFixture(fdef);
//
//        this.body.getFixtureList().get(0).setUserData("NPC");
//        this.body.setUserData(this);
//
//        this.body.setTransform(0 * Main.PPM, 0 * Main.PPM, 0);
//    }
//}
