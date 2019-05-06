package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;


public class WorldCreator {
    Body body;
    ArrayList<Body> walls = new ArrayList<Body>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<Chest> chests = new ArrayList<Chest>();

    public WorldCreator(World world, TiledMap map) {
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            for (MapObject obj : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                if (obj.getName().equals("wall")) {
                    BodyDef bdef = new BodyDef();
                    FixtureDef def = new FixtureDef();
                    PolygonShape shape = new PolygonShape();

                    bdef.type = (obj.getName().equals("enemy") ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody);

                    bdef.position.set(rect.getX() * Main.PPM + rect.getWidth() / 2 * Main.PPM, rect.getY() * Main.PPM + rect.getHeight() / 2 * Main.PPM);

                    body = world.createBody(bdef);

                    shape.setAsBox(rect.getWidth() / 2 * Main.PPM, rect.getHeight() / 2 * Main.PPM);

                    def.shape = shape;

                    this.body.createFixture(def);
                }

                String name = obj.getName();

                if (name.equals("wall")) walls.add(body);
                else if (name.equals("enemy")) enemies.add(new Enemy(rect));
                else if (name.equals("chest"))
                    chests.add(new Chest(rect, (String) obj.getProperties().get("chestName")));

                for (Fixture f : body.getFixtureList()) {
                    f.setUserData(1);
                }
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
}
