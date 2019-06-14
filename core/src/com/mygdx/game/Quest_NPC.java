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
import java.util.HashMap;

public class Quest_NPC {
    Body body;
    Sprite npc = new Sprite(new Texture("ASSETS/SPRITES/NPC/1.png"));
    String name, item;
    Sprite textBox = new Sprite(new Texture("ASSETS/UI/DIALOGUE_BOX/box2.png"));
    BitmapFont font = new BitmapFont(Gdx.files.internal("ASSETS/FONTS/myFont.fnt"), false);
    ArrayList<ArrayList<String>> allText = new ArrayList<ArrayList<String>>();
    int dialoguePage, pageLine;
    String[] allDialogues = new String[3];
    int questStage = 0;

    boolean textFinished = false;

    public Quest_NPC(Rectangle rect, String name, String dialogue, String midQuestDialogue, String questFinishDialogue, String goalNPC, String item) {
        this.name = name;

        allDialogues[0] = dialogue;
        allDialogues[1] = midQuestDialogue;
        allDialogues[2] = questFinishDialogue;
        this.item = item;

        String[] pages = dialogue.split("#");
        for (String i : pages) {
            ArrayList<String> lines = new ArrayList<String>();
            lines.addAll(Arrays.asList(i.split("//")));
            allText.add(lines);
        }

        dialoguePage = 0;
        pageLine = -1;

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

        shape.setAsBox(npc.getWidth() / 3 * Main.PPM, npc.getHeight() / 3 * Main.PPM);

        def.shape = shape;

        this.body.createFixture(def);

        this.body.getFixtureList().get(0).setUserData(this);

    }

    public void talk(SpriteBatch batch) {
//        String[] pages = allDialogues[questStage].split("#");
//        for (String i : pages) {
//            ArrayList<String> lines = new ArrayList<String>();
//            lines.addAll(Arrays.asList(i.split("//")));
//            allText.add(lines);
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (dialoguePage == allText.size() - 1 && pageLine == allText.get(dialoguePage).size() - 1) {
                HashMap tmp = (Main.weapons.get(item) != null ? Main.weapons.get(item) : Main.consumables.get(item));
                if (Main.consumables.get(item) != null) {
                    Main.player.receiveItem(new Consumable(item, (Integer) tmp.get("stat") == 1 ? "health" : "mana", (Integer) tmp.get("replenishAmount"), true));
                } else if (Main.weapons.get(item) != null) {
                    Main.player.receiveItem(new Weapon(item, null, (Integer) tmp.get("damage"), false));
                }
                System.out.println("large oof");
                textFinished = true;
            }
            if (!textFinished) {
                pageLine++;
                if (pageLine == allText.get(dialoguePage).size()) {
                    pageLine = 0;
                    dialoguePage++;
                }
            }
        }
        textBox.draw(batch);
        font.setColor(Color.RED);
        font.draw(batch, name, 10, 180 + font.getCapHeight());
        font.setColor(Color.WHITE);
        for (int i = 0; i < pageLine + 1; i++) {
            font.draw(batch, allText.get(dialoguePage).get(i), 106 + 26 * i, (140 - font.getCapHeight() * i) + font.getCapHeight() - 8 * i);
        }
    }

    public void resetTalk() {
        dialoguePage = 0;
        pageLine = -1;
        textFinished = false;
    }

    public Body getBody() {
        return body;
    }
}
