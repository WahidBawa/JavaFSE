/*
Author: Wahid Bawa & Andi Morarescu
        Purpose: This is an npc which talks to you and can give you items
 */
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

public class NPC {
    // loads default sprites and fonts
    Body body;
    Sprite npc = new Sprite(new Texture("ASSETS/SPRITES/NPC/17.png"));
    String name, dialogue, item;
    Sprite textBox = new Sprite(new Texture("ASSETS/UI/DIALOGUE_BOX/box2.png"));
    BitmapFont font = new BitmapFont(Gdx.files.internal("ASSETS/FONTS/myFont.fnt"), false);
    ArrayList<ArrayList<String>> allText = new ArrayList<ArrayList<String>>(); // stores all the text
    int dialoguePage, pageLine; // used to keep track of what should be displayed on the screen

    boolean textFinished = false; // checks if the text is finished

    String type;

    public NPC(Rectangle rect, String name, String dialogue, String item, String type) {
        this.name = name;
        this.dialogue = dialogue;
        this.item = item;
        this.type = type;
        String[] pages = dialogue.split("#"); // splits the dialog based on pages
        for (String i : pages) { // iterates through the pages of text and splits them into lines
            ArrayList<String> lines = new ArrayList<String>();
            lines.addAll(Arrays.asList(i.split("//")));
            allText.add(lines);
        }

        // starting vals for where the text should start
        dialoguePage = 0;
        pageLine = -1;

        //renders text box
        textBox.setPosition(0, 0);
        textBox.setSize(Main.WIDTH, textBox.getHeight()); // sets size
        npc.setPosition(rect.getX(), rect.getY()); // sets pos
        npc.setSize(npc.getWidth() * 2, npc.getHeight() * 2); // sets size
        createBody(rect); // creates body
    }

    public void render(SpriteBatch batch) {
        // if the body of the npc isn't a sprite it will use the scaled version of the rect object from the tmx map
        if (!type.equals("00")) batch.draw(npc, body.getPosition().x - npc.getWidth() * (float) Math.pow(Main.PPM, 2), body.getPosition().y - npc.getHeight() * (float) Math.pow(Main.PPM, 2), npc.getWidth() * (float) Math.pow(Main.PPM, 2) * 2, npc.getHeight() * (float) Math.pow(Main.PPM, 2) * 2);

    }

    public void update(SpriteBatch batch) {

        render(batch);
    }

    public void createBody(Rectangle rect) { // same as normal except uses scaled rect from map in cases of not using sprite for npc
        BodyDef bdef = new BodyDef();
        FixtureDef def = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;

        bdef.position.set((type.equals("00") ? rect.getX() : npc.getX()) * Main.PPM + (type.equals("00") ? rect.getWidth() : npc.getWidth()) / 2 * Main.PPM, (type.equals("00") ? rect.getY() : npc.getY()) * Main.PPM + (type.equals("00") ? rect.getHeight() : npc.getHeight()) / 2 * Main.PPM);

        body = Main.world.createBody(bdef);

        shape.setAsBox((type.equals("00") ? rect.getWidth() : npc.getWidth()) / 3 * Main.PPM, (type.equals("00") ? rect.getHeight() : npc.getHeight()) / 3 * Main.PPM);

        def.shape = shape;

        this.body.createFixture(def);

        this.body.getFixtureList().get(0).setUserData(this);

    }

    public void talk(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (dialoguePage == allText.size() - 1 && pageLine == allText.get(dialoguePage).size() - 1) { // if the dialogue is done
                // gives an item to the user
                HashMap tmp = (Main.weapons.get(item) != null ? Main.weapons.get(item) : Main.consumables.get(item));
                if (Main.consumables.get(item) != null) {
                    Main.player.receiveItem(new Consumable(item, (Integer) tmp.get("stat") == 1 ? "health" : "mana", (Integer) tmp.get("replenishAmount"), true));
                } else if (Main.weapons.get(item) != null) {
                    Main.player.receiveItem(new Weapon(item, null, (Integer) tmp.get("damage"), false));
                }

                textFinished = true;

                for (int i = 0; i < Main.questRelatedNPCs.size(); i++){ // checks if this npc is to be used to complete a quest
                    if (Main.questRelatedNPCs.get(i).getGoal().equals(name)){ // will advance the quest after being talked to
                        Main.questRelatedNPCs.get(i).advanceQuest();
                    }
                }
            }
            if (!textFinished) { // will continue to display the proper lines on a page
                pageLine++;
                if (pageLine == allText.get(dialoguePage).size()) {
                    pageLine = 0;
                    dialoguePage++;
                }
            }
        }
        // draws the text box and the text on screen
        textBox.draw(batch);
        font.setColor(Color.RED);
        font.draw(batch, name, 10, 180 + font.getCapHeight());
        font.setColor(Color.WHITE);
        for (int i = 0; i < pageLine + 1; i++) {
            font.draw(batch, allText.get(dialoguePage).get(i), 106 + 26 * i, (140 - font.getCapHeight() * i) + font.getCapHeight() - 8 * i);
        }
    }

    public void resetTalk() { // resets the variables that keep track of pages
        dialoguePage = 0;
        pageLine = -1;
        textFinished = false;
    }

    public Body getBody() { // returns the body
        return body;
    }
}
