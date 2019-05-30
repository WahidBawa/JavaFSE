package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import javax.sound.sampled.Port;
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

    public static final float PPM = 0.3f;


    OrthogonalTiledMapRenderer renderer;

    public static OrthographicCamera camera;

    public static boolean moving = false;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static int dir = DOWN;


    public static boolean chestCollide = false;
    public static boolean npcCollide = false;
    public static boolean interactable = true;

    public static ArrayList<Fixture> objs = new ArrayList<Fixture>();

    Box2DDebugRenderer dbr;
    FPSLogger fl = new FPSLogger();

    HUD hud; // this will be used in the future for putting stats like health bar on the screen

    public static Inventory inventory;
    private boolean showInventory = false;
    private boolean invDrag = false;
    private int xDragOffset = 0;
    private int yDragOffset = 0;

    public static boolean displayText = false;
    public String type = null;

    public static HashMap<String, HashMap<String, Integer>> weapons = new HashMap<String, HashMap<String, Integer>>();
    public static HashMap<String, HashMap<String, Integer>> consumables = new HashMap<String, HashMap<String, Integer>>();
    public static HashMap<String, String> maps = new HashMap<String, String>();

    private NPC currNpc;
    private Chest currChest;

    private ArrayList<Body> bodiesToDestroy = new ArrayList<Body>();

    boolean destroyed = true;

    @Override
    public void create() {
        graphics.setWindowedMode(WIDTH, HEIGHT);

        try {
            loadData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        world = new World(new Vector2(0, 0), true);

        player = new Player();

        createWorld("3");
//        createWorld("ASSETS/MAPS/OLD_MAPS/grasslands.tmx");
//        createWorld("ASSETS/MAPS/OLD_MAPS/snow_place.tmx");

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
        camera.zoom = PPM;

        if (!destroyed){
            for (Body i : bodiesToDestroy){
                world.destroyBody(i);
                destroyed = true;
//            bodiesToDestroy.remove(i);
            }
        }else{
            world.step(1 / 60f, 6, 2);
        }

        Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        renderer.setView(camera);
//        renderer.render(new int[]{0, 1, 2, 3});
        renderer.render();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        updateWorldObjects();

        batch.end();

        if (!displayText) {
            movePlayer();
            for (Enemy enemy : wc.getEnemies()) {
                enemy.move(player);
            }
        }

//        renderer.render(new int[]{4});

        if (showInventory && !displayText) {
            hud_batch.begin();
            inventory.update(hud_batch);
            inventory.open(hud_batch);
            hud_batch.end();

            clickedOn(inventory);
            if (inventory.getItems().size() > 0) {
                for (Item[] i : inventory.getItemArray()) {
                    for (Item n : i) {
                        if (n != null) {
                            clickedOn(n);
                        }
                    }
                }
            }
        }

        if (displayText) {
            hud_batch.begin();
            if (type.equals("npc")) {
                currNpc.talk(hud_batch);
            } else if (type.equals("chest")) {
                currChest.open(hud_batch);
            }
            hud_batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (type.equals("npc") && currNpc.textFinished) {
                    displayText = false;
                } else if (type.equals("chest") && currChest.textFinished) {
                    displayText = false;
                }
            }
        }

        //DEBUGGER AND FPS
        dbr.render(world, camera.combined);
//        fl.log();
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }

    public void movePlayer() {
        player.getBody().setLinearVelocity(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.getBody().applyLinearImpulse(new Vector2(-speed * 2, 0), player.getBody().getWorldCenter(), true);
            moving = true;
            dir = LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.getBody().applyLinearImpulse(new Vector2(speed * 2, 0), player.getBody().getWorldCenter(), true);
            moving = true;
            dir = RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.getBody().applyLinearImpulse(new Vector2(0, -speed * 2), player.getBody().getWorldCenter(), true);
            moving = true;
            dir = DOWN;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.getBody().applyLinearImpulse(new Vector2(0, speed * 2), player.getBody().getWorldCenter(), true);
            moving = true;
            dir = UP;
        } else {
            player.getBody().applyLinearImpulse(new Vector2(player.getBody().getLinearVelocity().x * -1, player.getBody().getLinearVelocity().y * -1), player.getBody().getWorldCenter(), true);
            moving = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && (chestCollide || npcCollide) && !moving) {
            for (Fixture i : objs) {
                if (i.getUserData().getClass() == Chest.class) {
                    currChest = (Chest) i.getUserData();
                    if (!currChest.chestOpened) {
                        type = "chest";
                        displayText = true;
                    }
                }
                if (i.getUserData().getClass() == NPC.class && interactable) {
                    currNpc = (NPC) i.getUserData();
                    type = "npc";
                    currNpc.resetTalk();
                    displayText = true;
                    interactable = false;
                }
            }
        }

        for (Fixture i : objs){
            if (i.getUserData().getClass() == Portal.class){
                Portal p = (Portal) i.getUserData();
                createWorld(p.getType());
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            System.out.println();
            System.out.println("HEALTH: " + player.stats.get("health"));
            System.out.println("MANA: " + player.stats.get("mana"));
            System.out.println("ATTACK: " + player.stats.get("attack"));
            System.out.println("DEFENSE: " + player.stats.get("defense"));
            System.out.println();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            showInventory = !showInventory;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) createWorld("1");
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) createWorld("2");
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) createWorld("3");

        player.setX(player.body.getPosition().x);
        player.setY(player.body.getPosition().y);

        camera.position.x = player.getX();
        camera.position.y = player.getY();
    }

    public void updateWorldObjects() {
        player.update(batch);

        for (NPC i : wc.getNpcs()) i.update(batch);

        for (Enemy i : wc.getEnemies()) i.update(batch);

        for (Chest i : wc.getChests()) i.update(batch);
    }

    public void clickedOn(Inventory inv) {
        float minx = inv.getSprite().getX();
        float miny = inv.getSprite().getY() + inv.getSprite().getHeight() - 75;
        float maxx = minx + inv.getSprite().getWidth();
        float maxy = miny + inv.getSprite().getHeight();

        int mousex = Gdx.input.getX();
        int mousey = HEIGHT - Gdx.input.getY();
//        System.out.println("X: " + mousex +" Y: " + mousey);
        if (Gdx.input.isButtonPressed(0)) {
            if ((mousex >= minx && mousex <= maxx) && (mousey >= miny && mousey <= maxy)) {
                if (!invDrag) { // YOU NEED TO USE MOUSE UP TO RESET INV DRAG, SO IMPLEMENT THE INPUT PROCESSOR HOE
                    xDragOffset = Math.abs(mousex - (int) minx);
                    yDragOffset = Math.abs(mousey - (int) miny);
                    invDrag = true;
                }
                inventory.getSprite().setPosition(mousex - xDragOffset, mousey - inv.getSprite().getHeight() + 25);
            }
        } else {
            invDrag = false;
        }
    }

    public void clickedOn(Item item) {
        float minx = item.getImg().getX();
        float miny = item.getImg().getY();
        float maxx = minx + item.getImg().getWidth();
        float maxy = miny + item.getImg().getHeight();

        int mousex = Gdx.input.getX();
        int mousey = HEIGHT - Gdx.input.getY();

        if ((mousex >= minx && mousex <= maxx) && (mousey >= miny && mousey <= maxy)) {
            if (Gdx.input.isButtonPressed(0)) {
                HashMap t = (HashMap) inventory.getInventoryBlocks().get(item.name);
                System.out.println("Clicked on " + item.name + " with a quantity of " + t.get("Quantity"));
                boolean otherDrag = false;
                for (Item[] i : inventory.getItemArray()) {
                    for (Item n : i) {
                        if (n != null && n.dragged) {
                            otherDrag = true;
                        }
                    }
                }

                if (!otherDrag) item.dragged = true;

            } else {
                item.dragged = false;
            }

            if (Gdx.input.isButtonPressed(1)) {
                item.used = true;
            } // working on this shit still
            else {
                if (item.used) {
                    player.use(item);
                    System.out.println("item used");
                }
            }

        } else {
            item.used = false;
        }
    }


    public void loadData() throws FileNotFoundException {
        //"maps.dat"
        String[] dats = {"weapons.dat", "consumables.dat"};
        for (int i = 0; i < dats.length; i++){ // this is for loading all the inventory data
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
        while(mapsData.hasNextLine()){
            line = mapsData.nextLine();
            split = line.split(",");
            maps.put(split[0], split[1]);
        }
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
