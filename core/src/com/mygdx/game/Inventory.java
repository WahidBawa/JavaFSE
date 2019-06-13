package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    Sprite inventory = new Sprite(new Texture("ASSETS/UI/INVENTORY/Inventory.png"));
    Item[][] items = new Item[3][7];
    ArrayList<Item> allItems = new ArrayList<Item>();
    HashMap<String, HashMap<String, Integer>> inventoryBlocks = new HashMap<String, HashMap<String, Integer>>();
    BitmapFont font = new BitmapFont();
    Sprite selected = new Sprite(new Texture("ASSETS/UI/INVENTORY/Selected.png"));
    int selected_x, selected_y;
    Sprite hover = new Sprite(new Texture("ASSETS/UI/INVENTORY/Hover.png"));
    int hover_x, hover_y;
    boolean selectedItem = false;

    public Inventory() {
        hover_x = hover_y = 0;
        selected_x = selected_y = 0;

        inventory.setPosition(Main.WIDTH / 2 - inventory.getWidth() / 2, Main.HEIGHT / 2 - inventory.getHeight() / 2);
    }

    public void render(SpriteBatch batch) {
        inventory.draw(batch);
    }

    public void update(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            hover_x += 1;
            hover_x = hover_x % 7;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            hover_x -= 1;
            if (hover_x < 0) {
                hover_x = 6;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            hover_y += 1;
            hover_y = hover_y % 3;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            hover_y -= 1;
            if (hover_y < 0) {
                hover_y = 2;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            //do stuff
            if (items[hover_y][hover_x] != null && !selectedItem){
                selectedItem = true;
                selected_x = hover_x;
                selected_y = hover_y;
            }
            if (items[hover_y][hover_x] == null && selectedItem){
                selectedItem = false;
                inventoryBlocks.get(items[selected_y][selected_x].name).put("X", hover_x);
                inventoryBlocks.get(items[selected_y][selected_x].name).put("Y", hover_y);
                items[hover_y][hover_x] = items[selected_y][selected_x];
                items[selected_y][selected_x] = null;
            }
        }
        render(batch);
    }

    public void open(SpriteBatch batch) {
        for (int i = 0; i < items.length; i++) {
            for (int n = 0; n < items[i].length; n++) {
                if (items[i][n] != null) {
                    HashMap t = inventoryBlocks.get(items[i][n].name);
                    Sprite tmp = items[i][n].getImg();

                    int posX = (Integer) t.get("X");
                    int posY = (Integer) t.get("Y");

                    float x = (30 + posX * tmp.getWidth() + 4 * posX) + inventory.getX();
                    float y = (190 - posY * tmp.getWidth() - 4 * posY) + inventory.getY();

                    items[i][n].getImg().setPosition(x, y);
                    items[i][n].getImg().draw(batch);
                    font.draw(batch, t.get("Quantity") + "", x + 50, y + 10);
                }
            }
        }
        batch.draw(hover, 30 + (hover_x * 60 + inventory.getX() + 4 * hover_x), 187 + (hover_y * -60 + inventory.getY() - 4 * hover_y), 60, 60);
        if (selectedItem) batch.draw(selected, 30 + (selected_x * 60 + inventory.getX() + 4 * selected_x), 187 + (selected_y * -60 + inventory.getY() - 4 * selected_y), 60, 60);
    }

    public void addItem(Item item) { // will add item to the first empty spot found
        boolean itemAdded = false;
        allItems.add(item); // adds to the arraylist which makes it easier to use items for testing TEST
        HashMap tmp = new HashMap();

        if (inventoryBlocks.get(item.name) != null) {
            HashMap t = inventoryBlocks.get(item.name);
            tmp.put("X", t.get("X"));
            tmp.put("Y", t.get("Y"));
            tmp.put("Quantity", (Integer) t.get("Quantity") + 1);
            inventoryBlocks.put(item.name, tmp);
        } else {
            for (int i = 0; i < items.length; i++) {
                for (int n = 0; n < items[i].length; n++) {
                    if (items[i][n] == null && !itemAdded) {
                        items[i][n] = item;

                        tmp.put("X", n);
                        tmp.put("Y", i);
                        tmp.put("Quantity", 1);

                        inventoryBlocks.put(items[i][n].name, tmp);

                        itemAdded = true;

                        break;
                    }
                    if (itemAdded) break;
                }
            }
        }
    }

    public void removeItem(Item item) {
        HashMap t = inventoryBlocks.get(item.name);
        if ((Integer) t.get("Quantity") > 1) {
            t.put("Quantity", (Integer) t.get("Quantity") - 1);
            inventoryBlocks.put(item.name, t);
        } else if ((Integer) t.get("Quantity") == 1) {
            items[(Integer) t.get("Y")][(Integer) t.get("X")] = null;
            inventoryBlocks.remove(item.name);
        }
        allItems.remove(item);
    }

    public ArrayList<Item> getItems() {
        return allItems;
    }

    public Item[][] getItemArray() {
        return items;
    }

    public HashMap getInventoryBlocks() {
        return inventoryBlocks;
    }

    public Sprite getSprite() {
        return inventory;
    }
}
