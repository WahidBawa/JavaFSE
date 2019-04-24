package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;


public class WorldCreator {
    Body body;
    public WorldCreator(World world, TiledMap map){
        for(int i = 0; i < map.getLayers().getCount(); i++){
            for (MapObject obj : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect  = ((RectangleMapObject) obj).getRectangle();
                BodyDef bdef  = new BodyDef();
                FixtureDef def = new FixtureDef();
                PolygonShape shape = new PolygonShape();

                bdef.type = BodyDef.BodyType.StaticBody;

                bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);

                def.shape = shape;

                this.body.createFixture(def);

//                for (Fixture f : body.getFixtureList()){
//                    f.setUserData(1);
//                }
            }
        }
    }
}
