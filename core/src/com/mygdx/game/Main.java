package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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

    public static final float PPM = 0.3f;

    OrthogonalTiledMapRenderer renderer;

    public static OrthographicCamera camera;
    public static OrthographicCamera staticCam;

    public static boolean moving = false;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static int dir = DOWN;

    public static boolean chestCollide = false;
    FPSLogger fl = new FPSLogger();

    public static ArrayList<Fixture> objs = new ArrayList<Fixture>();

    Box2DDebugRenderer dbr;

    HUD hud;

    static Inventory inventory;

    boolean showInventory = false;

    public static HashMap<String, HashMap<String, Integer>> weapons;
    public static HashMap<String, HashMap<String, Integer>> consumables;

    @Override
    public void create() {
        graphics.setWindowedMode(WIDTH, HEIGHT);
        world = new World(new Vector2(0, 0), true);
        player = new Player();


        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("ASSETS/MAPS/OLD_MAPS/grasslands.tmx");

        MAP_WIDTH = (Integer) map.getProperties().get("width") * TILESIZE;
        MAP_HEIGHT = (Integer) map.getProperties().get("height") * TILESIZE;

        camera = new OrthographicCamera(800f, 600f);

        staticCam = new OrthographicCamera(800, 600);

        staticCam.translate(staticCam.viewportWidth / 2, staticCam.viewportHeight / 2);

        renderer = new OrthogonalTiledMapRenderer(map, PPM);

        batch = new SpriteBatch();

        hud_batch = new SpriteBatch();

        wc = new WorldCreator(world, map);

        dbr = new Box2DDebugRenderer();

        world.setContactListener(new CollisionListener());

        hud = new HUD();

        inventory = new Inventory();

        try {
            weapons = loadData("DATA/weapons.dat", true, false, false);
            consumables = loadData("DATA/consumables.dat", false, false, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        camera.zoom = PPM;

        world.step(1 / 60f, 6, 2);

        Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        renderer.setView(camera);
        renderer.render(new int[]{0, 1, 2, 3});

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        updateWorldObjects();

//        if (inventory.getItems().size() > 0){
//            inventory.open(batch);
//        }

        batch.end();

        movePlayer();
        for(Enemy enemy : wc.getEnemies()) {
            enemy.move(player);
        }

        renderer.render(new int[]{4});

        staticCam.update();

        if (showInventory){
            hud_batch.begin();
//        hud.update(batch);
            inventory.update(hud_batch);
            inventory.open(hud_batch);
            hud_batch.end();

            if (Gdx.input.isButtonPressed(0)) {
                clickedOn(inventory);
            }
        }

//        dbr.render(world, camera.combined);

//        fl.log();
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }

    public void movePlayer() {
        player.getBody().setLinearVelocity(0,0);

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

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && chestCollide) {
            for (Fixture i : objs) {
                Chest c = (Chest) i.getUserData();
                c.open();
            }
        }


        // how to implement using items in the future
        if (Gdx.input.isKeyJustPressed(Input.Keys.X) && inventory.getItems().size() > 0) {
            player.use(inventory.getItems().get(0));
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

        player.setX(player.body.getPosition().x);
        player.setY(player.body.getPosition().y);

        camera.position.x = player.getX();
        camera.position.y = player.getY();
    }

    public void updateWorldObjects() {
        player.update(batch);

        for (Enemy i : wc.getEnemies()) i.update(batch);

        for (Chest i : wc.getChests()) i.update(batch);
    }

    public void clickedOn(Inventory inv){
        float minx = inv.getSprite().getX();
        float miny = inv.getSprite().getY();
        float maxx = minx + inv.getSprite().getWidth();
        float maxy = miny + inv.getSprite().getHeight();

        int mousex = Gdx.input.getX();
        int mousey = HEIGHT - Gdx.input.getY();
        System.out.println("X: " + mousex +" Y: " + mousey);

        if ((mousex >= minx && mousex <= maxx) && (mousey >= miny && mousey <= maxy)) {
            System.out.println("Clicked on the sprite.");
            // Some reset code.
        }
    }


    public HashMap<String, HashMap<String, Integer>> loadData(String filePath, boolean weapon, boolean armour, boolean consumable) throws FileNotFoundException {
        String[] str;
        HashMap<String, HashMap<String, Integer>> data = new HashMap<String, HashMap<String, Integer>>();
        File file = new File(filePath);
        Scanner text = new Scanner(file);
        text.nextLine();

        while (text.hasNextLine()){
            str = text.nextLine().split(",");
            HashMap<String, Integer> tmp = new HashMap<String, Integer>();
            if (consumable){
                tmp.put("stat", Integer.parseInt(str[1]));
                tmp.put("replenishAmount", Integer.parseInt(str[2]));
                tmp.put("stackable", Integer.parseInt(str[3]));
            }else if (weapon){
                tmp.put("damage", Integer.parseInt(str[1]));
                tmp.put("stackable", Integer.parseInt(str[2]));
            }

            data.put(str[0], tmp);
        }
        return data;
    }

}
