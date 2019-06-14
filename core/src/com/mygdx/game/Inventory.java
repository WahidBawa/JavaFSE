/*
    Author: Wahid Bawa & Andi Morarescu
    Purpose: Stores all the items that the player has received and makes them usable
 */
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
    Sprite inventory = new Sprite(new Texture("ASSETS/UI/INVENTORY/Inventory.png")); // this is the image of the inventory
    Item[][] items = new Item[3][7]; // this stores the place of the items in the grid
    ArrayList<Item> allItems = new ArrayList<Item>(); // this stores all of the items
    HashMap<String, HashMap<String, Integer>> inventoryBlocks = new HashMap<String, HashMap<String, Integer>>(); // this is the hashtable that stores the items received along with their x and y and quantity
    BitmapFont font = new BitmapFont(); // this is used to render font on the screen
    Sprite selected = new Sprite(new Texture("ASSETS/UI/INVENTORY/Selected.png")); // this is the selected sprite
    int selected_x, selected_y; // this is the x and y of the selected sprite
    Sprite hover = new Sprite(new Texture("ASSETS/UI/INVENTORY/Hover.png")); // this is the hover sprite
    int hover_x, hover_y; // this is the x and y of the hover sprite
    boolean selectedItem = false; // this check if the item is selected or not
    boolean changed = false; // this is checking if the item has been changed

    public Inventory() {
        // sets the hover and selected x and y to 0
        hover_x = hover_y = 0;
        selected_x = selected_y = 0;

        inventory.setPosition(Main.WIDTH / 2 - inventory.getWidth() / 2, Main.HEIGHT / 2 - inventory.getHeight() / 2); // sets the position of the inventory sprite in the middle of the screen
    }

    public void render(SpriteBatch batch) {
        inventory.draw(batch); // draws the inventory on to the screen
    }

    public void update(SpriteBatch batch) {

        // directional controls for the hover and the selected on the grid
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){ // if space pressed
            if (selectedItem){ // if an item is selected
                if (items[hover_y][hover_x] == null){ // if the space is empty
                    selectedItem = false; // item is no longer selected
                    // switches the spot of the item on the grid
                    inventoryBlocks.get(items[selected_y][selected_x].name).put("X", hover_x);
                    inventoryBlocks.get(items[selected_y][selected_x].name).put("Y", hover_y);
                    items[hover_y][hover_x] = items[selected_y][selected_x];
                    items[selected_y][selected_x] = null;
                } else if (items[hover_y][hover_x] != null){ // if the item is meant to replace another item on the grid

                    selectedItem = false; // selected item is made false

                    //exchanges their position on the grid
                    Item selectedItem = items[selected_y][selected_x];
                    Item hoverItem = items[hover_y][hover_x];

                    inventoryBlocks.get(selectedItem.name).put("X", hover_x);
                    inventoryBlocks.get(selectedItem.name).put("Y", hover_y);

                    inventoryBlocks.get(hoverItem.name).put("X", selected_x);
                    inventoryBlocks.get(hoverItem.name).put("Y", selected_y);

                    items[hover_y][hover_x] = selectedItem;
                    items[selected_y][selected_x] = hoverItem;
                }
                changed = true; // changed is true
            }
            if (items[hover_y][hover_x] != null && !selectedItem && !changed){ // will set the position of the selected sprite
                selectedItem = true;
                selected_x = hover_x;
                selected_y = hover_y;
            }
            changed = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X) && items[hover_y][hover_x] != null){ // uses the item
            Main.player.use(items[hover_y][hover_x]);
        }
        render(batch);
    }

    public void open(SpriteBatch batch) { // renders all of the items on the grid as well as their quantity
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

        drawStats(batch); // draws the stats on the screen
        // draws hover or selected sprites
        batch.draw(hover, 30 + (hover_x * 60 + inventory.getX() + 4 * hover_x), 187 + (hover_y * -60 + inventory.getY() - 4 * hover_y), 60, 60);
        if (selectedItem) batch.draw(selected, 30 + (selected_x * 60 + inventory.getX() + 4 * selected_x), 187 + (selected_y * -60 + inventory.getY() - 4 * selected_y), 60, 60);
    }

    public void drawStats(SpriteBatch batch){ // iterates through stats and draws them on screen
        int i = 850;
        for (Object data : Main.player.getStats().keySet()) {

            font.draw(batch, "" + data + ": "+ Main.player.getStats().get(data)  , 950, i);
            i-=50;
        }
    }

    public void addItem(Item item) { // will add item to the first empty spot found
        boolean itemAdded = false;
        allItems.add(item);
        HashMap tmp = new HashMap();

        if (inventoryBlocks.get(item.name) != null) { // if the item already exists it will add to the quantity
            HashMap t = inventoryBlocks.get(item.name);
            tmp.put("X", t.get("X"));
            tmp.put("Y", t.get("Y"));
            tmp.put("Quantity", (Integer) t.get("Quantity") + 1);
            inventoryBlocks.put(item.name, tmp);
        } else {
            for (int i = 0; i < items.length; i++) {
                for (int n = 0; n < items[i].length; n++) {
                    if (items[i][n] == null && !itemAdded) { // will put the item in the first empty spot found
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

    public void removeItem(Item item) { // removes the item
        HashMap t = inventoryBlocks.get(item.name); // hashmap of the items properties
        if ((Integer) t.get("Quantity") > 1) { // if the quantity is more than 1
            t.put("Quantity", (Integer) t.get("Quantity") - 1); // will decrease the quantity by one
            inventoryBlocks.put(item.name, t);
        } else if ((Integer) t.get("Quantity") == 1) { // if the quantity is one
            items[(Integer) t.get("Y")][(Integer) t.get("X")] = null; // will erase the item from that spot by making it null
            inventoryBlocks.remove(item.name); // removes the item
        }
        allItems.remove(item); // removes item
    }
}
