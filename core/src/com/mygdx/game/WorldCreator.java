package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;


public class WorldCreator {
    Body body;
    private ArrayList<Body> walls = new ArrayList<Body>();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Chest> chests = new ArrayList<Chest>();
    private ArrayList<NPC> npcs = new ArrayList<NPC>();
    private ArrayList<Portal> portals = new ArrayList<Portal>();
    private ArrayList<Quest_NPC> quest_npcs = new ArrayList<Quest_NPC>();

    private ArrayList<Body> toBeDestroyed = new ArrayList<Body>();

    public WorldCreator(World world, TiledMap map) {
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            for (MapObject obj : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                if (obj.getName().equals("wall")) {
                    BodyDef bdef = new BodyDef();
                    FixtureDef def = new FixtureDef();
                    PolygonShape shape = new PolygonShape();

                    bdef.type = BodyDef.BodyType.StaticBody;

                    bdef.position.set(rect.getX() * Main.PPM + rect.getWidth() / 2 * Main.PPM, rect.getY() * Main.PPM + rect.getHeight() / 2 * Main.PPM);

                    body = world.createBody(bdef);

                    shape.setAsBox(rect.getWidth() / 2 * Main.PPM, rect.getHeight() / 2 * Main.PPM);

                    def.shape = shape;

                    this.body.createFixture(def);
                }

                String name = obj.getName();

                if (name.equals("wall")) {
                    walls.add(body);
                } else if (name.equals("enemy")) {
                    enemies.add(new Enemy(rect));
                } else if (name.equals("chest")) {
                    chests.add(new Chest(rect, (String) obj.getProperties().get("chestName"), (String) obj.getProperties().get("Item")));
                } else if (name.equals("NPC")) {
                    npcs.add(new NPC(rect, (String) obj.getProperties().get("Name"), (String) obj.getProperties().get("Dialogue"), (String) obj.getProperties().get("item")));
                } else if (name.equals("Portal")) {
                    portals.add(new Portal(rect, (String) obj.getProperties().get("type"), (Integer) obj.getProperties().get("xd"), (Integer) obj.getProperties().get("yd")));
                } else if (name.equals("Quest_NPC")){
                    quest_npcs.add(new Quest_NPC(rect, (String) obj.getProperties().get("Name"), (String) obj.getProperties().get("Dialogue"), (String) obj.getProperties().get("DialogueQuest"), (String) obj.getProperties().get("DialogueFinish"), (String) obj.getProperties().get("Goal"), (String) obj.getProperties().get("item")));
                }

                for (Fixture f : body.getFixtureList()) {
                    f.setUserData(1);
                }
//                }
            }
        }
    }

    public ArrayList<Body> getWalls() {
        return walls;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Chest> getChests() {
        return chests;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public ArrayList<Quest_NPC> getQuest_npcs(){
        return quest_npcs;
    }

    public ArrayList<Portal> getPortals() {
        return portals;
    }

    public ArrayList<Body> getToBeDestroyed() {
        toBeDestroyed = new ArrayList<Body>();
        for (Body i : walls) toBeDestroyed.add(i);
        for (Enemy i : enemies) toBeDestroyed.add(i.getBody());
        for (Chest i : chests) toBeDestroyed.add(i.getBody());
        for (NPC i : npcs) toBeDestroyed.add(i.getBody());
        for (Portal i : portals) toBeDestroyed.add(i.getBody());


        return toBeDestroyed;
    }
}
