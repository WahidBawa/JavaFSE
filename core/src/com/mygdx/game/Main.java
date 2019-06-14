package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static com.badlogic.gdx.Gdx.graphics;

public class Main extends ApplicationAdapter {

    SpriteBatch batch;
    SpriteBatch hud_batch;
    public static Player player;

    public static int speed = 9001; // speed is over 9000

    public static int WIDTH = 1366, HEIGHT = 1024;
    public static int MAP_WIDTH, MAP_HEIGHT;
    public final static int TILESIZE = 32;


    public static World world;
    public static WorldCreator wc;

    public static float PPM = 0.3f;


    public static OrthogonalTiledMapRenderer renderer;

    public static OrthographicCamera camera;

    public static boolean moving = false;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static int dir = DOWN;


    public static boolean chestCollide = false;
    public static boolean npcCollide = false;
    public static boolean npcQuestCollide = false;
    public static boolean interactable = true;

    public static ArrayList<Fixture> objs = new ArrayList<Fixture>();

    Box2DDebugRenderer dbr;
    FPSLogger fl = new FPSLogger();

    HUD hud; // this will be used in the future for putting stats like health bar on the screen

    public static Inventory inventory;
    private boolean showInventory = false;

    public static boolean displayText = false;
    public String type = null;

    public static HashMap<String, HashMap<String, Integer>> weapons = new HashMap<String, HashMap<String, Integer>>();
    public static HashMap<String, HashMap<String, Integer>> consumables = new HashMap<String, HashMap<String, Integer>>();
    public static HashMap<String, String> maps = new HashMap<String, String>();

    private NPC currNpc;
    private Quest_NPC currQuest_NPC;
    private Chest currChest;

    private ArrayList<Body> bodiesToDestroy = new ArrayList<Body>();

    boolean destroyed = true;

    Click click = new Click();

    public static ArrayList<Quest_NPC> questRelatedNPCs = new ArrayList<Quest_NPC>();

    @Override
    public void create() {
        Gdx.input.setInputProcessor(click);
        graphics.setWindowedMode(WIDTH, HEIGHT);

        try {
            loadData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        world = new World(new Vector2(0, 0), true);

        player = new Player();

        createWorld("1");

        batch = new SpriteBatch();

        hud_batch = new SpriteBatch();

        camera = new OrthographicCamera(800f, 600f);

        dbr = new Box2DDebugRenderer();

        world.setContactListener(new CollisionListener());

        hud = new HUD();

        inventory = new Inventory();
    }

    @Override
    public void render() {
        System.out.println(player.getBody().getPosition().x + " " + player.getBody().getPosition().y);
        camera.zoom = PPM;

        if (!destroyed) {
            for (Body i : bodiesToDestroy) {
                world.destroyBody(i);
            }
            destroyed = true;
        } else {
            world.step(1 / 60f, 6, 2);
        }


        Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!displayText) {
            movePlayer();
        }

        renderer.setView(camera);
//        renderer.render(new int[]{0, 1, 2, 3});
        renderer.render();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        updateWorldObjects();

        batch.end();
        /*
        if (!displayText && !player.isFrozen()) {
            for (Enemy enemy : wc.getEnemies()) {
                //enemy.encounter(player);
                System.out.println(enemy.getBody().getFixtureList().get(0).getUserData());
            }
        }*/


//        renderer.render(new int[]{4});

        if (showInventory && !displayText) {
            hud_batch.begin();
            inventory.update(hud_batch);
            inventory.open(hud_batch);
            hud_batch.end();
        }

        if (displayText) {
            hud_batch.begin();
            if (type.equals("npc")) {
                currNpc.talk(hud_batch);
            } else if (type.equals("chest")) {
                currChest.open(hud_batch);
            }else if (type.equals("quest_npc")){
                currQuest_NPC.talk(hud_batch);
            }
            hud_batch.end();
//            if (currQuest_NPC != null) System.out.println(currQuest_NPC.textFinished);
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

        //DEBUGGER AND FPS
//        dbr.render(world, camera.combined);
//        fl.log();
//        ShapeRenderer debugRenderer = new ShapeRenderer();
//        Gdx.gl.glLineWidth(4);
//        debugRenderer.setProjectionMatrix(camera.combined);
//        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
//        debugRenderer.setColor(new Color(0, 0, 0, 1));
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }


    public void movePlayer() {
        Vector2 adder = new Vector2(0, 0);
        if (!player.isFrozen()){
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

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !moving) {
                for (Fixture i : objs) {
                    if (i.getUserData().getClass() == Chest.class && chestCollide) {
                        currChest = (Chest) i.getUserData();
                        if (!currChest.chestOpened) {
                            type = "chest";
                            displayText = true;
                        }
                    }
                    if (i.getUserData().getClass() == NPC.class && interactable && npcCollide) {
                        currNpc = (NPC) i.getUserData();
//                        objs.remove(objs.indexOf(i));
                        type = "npc";
                        currNpc.resetTalk();
                        displayText = true;
                        interactable = false;
                    }
                    if (i.getUserData().getClass() == Quest_NPC.class && interactable && npcQuestCollide){
                        currQuest_NPC = (Quest_NPC) i.getUserData();
//                        objs.remove(i);
                        type = "quest_npc";
                        currQuest_NPC.resetTalk();
                        displayText = true;
                        interactable = false;
                    }
                }
            }

            for (Fixture i : objs) {
                if (i.getUserData().getClass() == Portal.class) {
                    Portal p = (Portal) i.getUserData();
                    createWorld(p.getType(), p.getNewX(), p.getNewY());
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                System.out.println();
                System.out.println("HEALTH: " + player.stats.get("Health"));
                System.out.println("MANA: " + player.stats.get("Mana"));
                System.out.println("ATTACK: " + player.stats.get("Attack"));
                System.out.println("DEFENSE: " + player.stats.get("Defense"));
                System.out.println();
            }


            player.setX(player.body.getPosition().x);
            player.setY(player.body.getPosition().y);

            camera.position.x = player.getX();
            camera.position.y = player.getY();
            camera.update();

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            showInventory = !showInventory;
            if (showInventory) {
                player.lock();
            }
            else {
                player.unLock();
            }
        }
    }

    public void updateWorldObjects() {
        player.update(batch);

        for (NPC i : wc.getNpcs()) i.update(batch);

        for (Quest_NPC i : wc.getQuest_npcs()) i.update(batch);

        for (Enemy i : wc.getEnemies()) i.update(batch);

        for (Chest i : wc.getChests()) i.update(batch);
    }

    public void loadData() throws FileNotFoundException {
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

        // this is for other stuff now
        Scanner mapsData = new Scanner(new File("DATA/maps.dat"));
        String[] split;
        String line;
        mapsData.nextLine();
        while (mapsData.hasNextLine()) {
            line = mapsData.nextLine();
            split = line.split(",");
            maps.put(split[0], split[1]);
        }
    }

    public void createWorld(String type, float x, float y) {
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

        player.getBody().setTransform(x, y, 0);
        player.setX(player.body.getPosition().x);
        player.setY(player.body.getPosition().y);

        camera.position.x = player.getX();
        camera.position.y = player.getY();

    }

    public void createWorld(String type) {
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
