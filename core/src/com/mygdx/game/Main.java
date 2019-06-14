/*
    By: Wahid Bawa & Andy Morarescu
    Purpose: The main class in charge of the main functions of the game, everything runs some way or another through this class
        */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static com.badlogic.gdx.Gdx.graphics;

public class Main extends ApplicationAdapter {

    SpriteBatch batch; // batch for all the world objects
    SpriteBatch hud_batch; // batch for all the screen objects
    public static Player player; // main player object

    public static int speed = 9001; // speed is over 9000

    public static int WIDTH = 1366, HEIGHT = 1024; // size of the screen
    public static int MAP_WIDTH, MAP_HEIGHT; // this is the map width and map height
    public final static int TILESIZE = 32; // this is the tile size fo all of our maps


    public static World world; // this is the world which will hold all of the bodies and the tiled map
    public static WorldCreator wc; // the worldcreator will create all of the necessary objects using the tiled map

    public static float PPM = 0.3f; // this is pixels per meter


    public static OrthogonalTiledMapRenderer renderer; // this is the renderer for our maps

    public static OrthographicCamera camera; // this is the camera that will pan around the world

    public static boolean moving = false; // this will check if the player is moving or not

    // these will be used to determine in which direction the player is moving
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static int dir = DOWN;

    // checks if objects are collidable / interactable
    public static boolean chestCollide = false;
    public static boolean npcCollide = false;
    public static boolean npcQuestCollide = false;
    public static boolean interactable = true;
    public String type = null; // this will be the type of object being collided with

    public static ArrayList<Fixture> objs = new ArrayList<Fixture>(); // these are all the objects that the player is colliding with

//    Box2DDebugRenderer dbr; // this is the debug renderer used for trouble shooting

    public static Inventory inventory; // this will be the players inventory
    private boolean showInventory = false; // this will check if the inventory should be being displayed on the screenq

    public static boolean displayText = false; // this will check if text should be displaying on the screen

    public static HashMap<String, HashMap<String, Integer>> weapons = new HashMap<String, HashMap<String, Integer>>(); // this is the hashtable that stores all of our weapos
    public static HashMap<String, HashMap<String, Integer>> consumables = new HashMap<String, HashMap<String, Integer>>(); // stores all consumables
    public static HashMap<String, String> maps = new HashMap<String, String>(); // stores all maps

    private NPC currNpc; // the current npc that is being talked to
    private Quest_NPC currQuest_NPC; // the current quest npc that is being talked to
    private Chest currChest; // the current chest being interacted with

    private ArrayList<Body> bodiesToDestroy = new ArrayList<Body>(); // bodies to be destroyed after loading in a new map

    boolean destroyed = true; // checks if the bodies that need to be destroyed are destroyed

    Click click = new Click(); // InputProcessor

    public static ArrayList<Quest_NPC> questRelatedNPCs = new ArrayList<Quest_NPC>(); // npcs that you have a quest for at that moment

    @Override
    public void create() {
        Gdx.input.setInputProcessor(click); // sets the input processor
        graphics.setWindowedMode(WIDTH, HEIGHT); // sets the size of the screen

        try {
            loadData(); // loads the weapons and the armour data
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        world = new World(new Vector2(0, 0), true); //initializes the world object

        player = new Player(); // initializes the player object

        createWorld("1"); // creates the world

        batch = new SpriteBatch(); // initializes the batch

        hud_batch = new SpriteBatch(); // initializes the hud_batch

        camera = new OrthographicCamera(800f, 600f); // initializes the camera

//        dbr = new Box2DDebugRenderer();

        world.setContactListener(new CollisionListener()); // sets the collision listener for the world

//        hud = new HUD();

        inventory = new Inventory(); // initializes the inventory
    }

    @Override
    public void render() {
        camera.zoom = PPM; // sets the camera zoom

        if (!destroyed) { // if not destroyed
            for (Body i : bodiesToDestroy) {
                world.destroyBody(i); // destroys the current body
            }
            destroyed = true; // sets destroyed
        } else {
            world.step(1 / 60f, 6, 2); // calculates the physics useing box2D
        }


        Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1); // sets the background colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!displayText) { // if text is not being displayed than the player will update
            movePlayer();
        }

        renderer.setView(camera); // sets the renderer's view
//        renderer.render(new int[]{0, 1, 2, 3});
        renderer.render(); // renders the map

        batch.setProjectionMatrix(camera.combined); // sets projection matrix
        batch.begin();

        updateWorldObjects(); // updates all of the world objects

        batch.end();
        /*
        if (!displayText && !player.isFrozen()) {
            for (Enemy enemy : wc.getEnemies()) {
                //enemy.encounter(player);
                System.out.println(enemy.getBody().getFixtureList().get(0).getUserData());
            }
        }*/


//        renderer.render(new int[]{4});

        if (showInventory && !displayText) { // if the inventory is going to be shown
            hud_batch.begin();
            inventory.update(hud_batch); // updates the inventory
            inventory.open(hud_batch); // opens the inventory
            hud_batch.end();
        }

        if (displayText) { // if text is being displayed
            hud_batch.begin();
            // will decide which object is going to print out text
            if (type.equals("npc")) {
                currNpc.talk(hud_batch);
            } else if (type.equals("chest")) {
                currChest.open(hud_batch);
            }else if (type.equals("quest_npc")){
                currQuest_NPC.talk(hud_batch);
            }
            hud_batch.end();
            // will set displayText to false and then type to null based on when text is finished
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (type.equals("npc") && currNpc.textFinished) {
                    displayText = false;
                    type = null;
                } else if (type.equals("chest") && currChest.textFinished) {
                    displayText = false;
                    type = null;
                } else if (type.equals("quest_npc") && currQuest_NPC.textFinished){
                    displayText = false;
                    type = null;
                }
            }
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        hud_batch.dispose();
        renderer.dispose();
    }


    public void movePlayer() {
        Vector2 adder = new Vector2(0, 0);
        if (!player.isFrozen()){ // if the player is not frozen
            // will make the player move and set the dir and moving to true
            player.getBody().setLinearVelocity(0, 0);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                adder.add(new Vector2(-speed * 2, 0));
                moving = true;
                dir = LEFT;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                adder.add(new Vector2(speed * 2, 0));
                moving = true;
                dir = RIGHT;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                adder.add(new Vector2(0, -speed * 2));
                moving = true;
                dir = DOWN;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                adder.add(new Vector2(0, speed * 2));
                moving = true;
                dir = UP;
            }

            if (adder.equals(new Vector2(0, 0))) {
                moving = false;
            }

            player.getBody().applyLinearImpulse(adder, player.getBody().getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !moving) { // if the player is interacting with an object
                for (Fixture i : objs) { // iterates through colliding objects
                    if (i.getUserData().getClass() == Chest.class && chestCollide) { // will make it so that a chest will open
                        currChest = (Chest) i.getUserData();
                        if (!currChest.chestOpened) {
                            type = "chest";
                            displayText = true;
                        }
                    }
                    if (i.getUserData().getClass() == NPC.class && interactable && npcCollide) { // will make it so that you can talk to a npc
                        currNpc = (NPC) i.getUserData();
//                        objs.remove(objs.indexOf(i));
                        type = "npc";
                        currNpc.resetTalk();
                        displayText = true;
                        interactable = false;
                    }
                    if (i.getUserData().getClass() == Quest_NPC.class && interactable && npcQuestCollide){ // makes it so that you can talk to a quest npc
                        currQuest_NPC = (Quest_NPC) i.getUserData();
//                        objs.remove(i);
                        type = "quest_npc";
                        currQuest_NPC.resetTalk();
                        displayText = true;
                        interactable = false;
                    }
                }
            }

            for (Fixture i : objs) { // iterates through colliding objs
                if (i.getUserData().getClass() == Portal.class) { // will load a new map
                    Portal p = (Portal) i.getUserData();
                    createWorld(p.getType(), p.getNewX(), p.getNewY());
                }
            }

            // sets the player position
            player.setX(player.body.getPosition().x);
            player.setY(player.body.getPosition().y);

            camera.position.x = player.getX();
            camera.position.y = player.getY();
            camera.update(); // updates camera

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) { // opens the inventory
            showInventory = !showInventory; // toggles showing the inventory
            if (showInventory) { // locks the player in place
                player.lock();
            }
            else { // unlocks the player so that it is able to move
                player.unLock();
            }
        }
    }

    public void updateWorldObjects() { // updates all the world objects
        player.update(batch);

        for (NPC i : wc.getNpcs()) i.update(batch);

        for (Quest_NPC i : wc.getQuest_npcs()) i.update(batch);

        for (Enemy i : wc.getEnemies()) i.update(batch);

        for (Chest i : wc.getChests()) i.update(batch);
    }

    public void loadData() throws FileNotFoundException { // loads all the data for weapons and consumeables from .dat files into hashtables
        String[] dats = {"weapons.dat", "consumables.dat"};
        for (int i = 0; i < dats.length; i++) { // this is for loading all the inventory data
            String[] str;
            File file = new File("DATA/" + dats[i]);
            Scanner text = new Scanner(file);
            text.nextLine();
            while (text.hasNextLine()) {
                str = text.nextLine().split(",");
                HashMap<String, Integer> tmp = new HashMap<String, Integer>();
                if (i == 1) {
                    tmp.put("stat", Integer.parseInt(str[1]));
                    tmp.put("replenishAmount", Integer.parseInt(str[2]));
                    tmp.put("stackable", Integer.parseInt(str[3]));
                    consumables.put(str[0], tmp);
                } else if (i == 0) {
                    tmp.put("damage", Integer.parseInt(str[1]));
                    tmp.put("stackable", Integer.parseInt(str[2]));
                    weapons.put(str[0], tmp);
                }
            }
        }

        Scanner mapsData = new Scanner(new File("DATA/maps.dat")); // loads the maps data
        String[] split;
        String line;
        mapsData.nextLine();
        while (mapsData.hasNextLine()) {
            line = mapsData.nextLine();
            split = line.split(",");
            maps.put(split[0], split[1]);
        }
    }

    public void createWorld(String type, float x, float y) { // loads a map and puts player in the specified position
        TmxMapLoader loader = new TmxMapLoader(); // creates a new loader
        TiledMap map = loader.load(maps.get(type)); // creates a new map

        MAP_WIDTH = (Integer) map.getProperties().get("width") * TILESIZE;
        MAP_HEIGHT = (Integer) map.getProperties().get("height") * TILESIZE;

        renderer = new OrthogonalTiledMapRenderer(map, PPM);

        if (wc != null) { // gets bodies to destroy if this is a new map being loaded
            bodiesToDestroy = wc.getToBeDestroyed();
            destroyed = false;
        }

        wc = new WorldCreator(world, map);

        player.getBody().setTransform(x, y, 0); // sets the position of the player's body
        player.setX(player.body.getPosition().x);
        player.setY(player.body.getPosition().y);

        // sets the camera position
        camera.position.x = player.getX();
        camera.position.y = player.getY();

    }

    public void createWorld(String type) { // this loads a map except that it does not place the player in a specified spot
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load(maps.get(type));

        MAP_WIDTH = (Integer) map.getProperties().get("width") * TILESIZE;
        MAP_HEIGHT = (Integer) map.getProperties().get("height") * TILESIZE;

        renderer = new OrthogonalTiledMapRenderer(map, PPM);

        if (wc != null) {
            bodiesToDestroy = wc.getToBeDestroyed();
            destroyed = false;
        }

        wc = new WorldCreator(world, map);

    }

}
