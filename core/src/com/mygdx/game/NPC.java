package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Arrays;

public class NPC {
    Body body;
    Sprite npc = new Sprite(new Texture("ASSETS/SPRITES/NPC/0/DOWN.png"));
    String name, dialogue, item;
    Sprite textBox = new Sprite(new Texture("ASSETS/UI/DIALOGUE_BOX/box2.png"));
    BitmapFont font = new BitmapFont(Gdx.files.internal("ASSETS/FONTS/myFont.fnt"), false);
    ArrayList<ArrayList<String>> allText = new ArrayList<ArrayList<String>>();
    int dialoguePage, pageLine;

    boolean textFinished = false;

    public NPC(Rectangle rect, String name, String dialogue, String item) {
        this.name = name;
        this.dialogue = dialogue;
        this.item = item;

        String[] pages = dialogue.split("#");
        for (String i : pages) {
            ArrayList<String> lines = new ArrayList<String>();
            lines.addAll(Arrays.asList(i.split("//")));
            allText.add(lines);
        }

        dialoguePage = 0;
        pageLine = -1;

        System.out.println(allText);

        textBox.setPosition(0, 0);
        textBox.setSize(Main.WIDTH, textBox.getHeight());
        npc.setPosition(rect.getX(), rect.getY());
        npc.setSize(npc.getWidth() * 2, npc.getHeight() * 2);
        createBody();
    }

    public void render(SpriteBatch batch) {
        batch.draw(npc, body.getPosition().x - npc.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - npc.getHeight() * (float) Math.pow(Main.PPM, 2), npc.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, npc.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);

    }

    public void update(SpriteBatch batch) {

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

    public void talk(SpriteBatch batch) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (dialoguePage == allText.size() - 1 && pageLine == allText.get(dialoguePage).size() - 1){
                textFinished = true;
            }
            if (!textFinished){
                pageLine++;
                if (pageLine == allText.get(dialoguePage).size()) {
                    pageLine = 0;
                    dialoguePage++;
                }
                System.out.println(pageLine);
            }
        }
//        String currLine = allText.get(dialoguePage).get(pageLine);

        textBox.draw(batch);
        font.setColor(Color.RED);
        font.draw(batch, name, 10, 180 + font.getCapHeight());
        font.setColor(Color.WHITE);
        for (int i = 0; i < pageLine + 1; i++) {
            font.draw(batch, allText.get(dialoguePage).get(i), 112, (140 - font.getCapHeight() * i) + font.getCapHeight());
        }
//        System.out.println("X: " + Gdx.input.getX() + " Y: " + (Main.HEIGHT - Gdx.input.getY()));
    }

    public void resetTalk(){
        dialoguePage = 0;
        pageLine = -1;
        textFinished = false;
    }
}
