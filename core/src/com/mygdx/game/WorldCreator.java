package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;


public class WorldCreator {
    Body body;
    ArrayList<Body> blocks = new ArrayList<Body>();
    public WorldCreator(World world, TiledMap map){
        for(int i = 0; i < map.getLayers().getCount(); i++){
            for (MapObject obj : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect  = ((RectangleMapObject) obj).getRectangle();
                BodyDef bdef  = new BodyDef();
                FixtureDef def = new FixtureDef();
                PolygonShape shape = new PolygonShape();

                bdef.type = BodyDef.BodyType.StaticBody;

                bdef.position.set(rect.getX() * Main.PPM + rect.getWidth() / 2 * Main.PPM, rect.getY() * Main.PPM + rect.getHeight() / 2 * Main.PPM);

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2 * Main.PPM, rect.getHeight() / 2 * Main.PPM);

                def.shape = shape;

                this.body.createFixture(def);

//                for (Fixture f : body.getFixtureList()){
//                    f.setUserData(1);
//                }

                blocks.add(body);

            }
        }
    }

    public ArrayList<Body> getAsList(){
        return blocks;
    }
}
