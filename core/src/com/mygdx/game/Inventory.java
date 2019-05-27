package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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
    HashMap inventoryBlocks = new HashMap();
    BitmapFont font = new BitmapFont();

    public Inventory() {
        inventory.setPosition(0, 0);
    }

    public void render(SpriteBatch batch) {
        inventory.draw(batch);
    }

    public void update(SpriteBatch batch) {
        render(batch);
    }

    public void open(SpriteBatch batch) {
        for (int i = 0; i < items.length; i++) {
            for (int n = 0; n < items[i].length; n++) {
                if (items[i][n] != null) {
                    HashMap t = (HashMap) inventoryBlocks.get(items[i][n].name);
                    Sprite tmp = items[i][n].getImg();

                    float x = Gdx.input.getX() - items[i][n].getImg().getWidth() / 2;
                    float y = Main.HEIGHT - Gdx.input.getY() - items[i][n].getImg().getHeight() / 2;

                    if (!items[i][n].dragged) {
                        x = (30 + n * tmp.getWidth() + 4 * n) + Main.inventory.getSprite().getX();
                        y = (190 - i * tmp.getWidth() - 4 * i) + Main.inventory.getSprite().getY();
                    }
                    items[i][n].getImg().setPosition(x, y);
                    items[i][n].getImg().draw(batch);
                    font.draw(batch, t.get("Quantity") + "", x + 50, y + 10);
                }
            }
        }
    }

    public void addItem(Item item) { // will add item to the first empty spot found
        boolean itemAdded = false;
        allItems.add(item); // adds to the arraylist which makes it easier to use items for testing TEST
        HashMap tmp = new HashMap();

        if (inventoryBlocks.get(item.name) != null) {
            HashMap t = (HashMap) inventoryBlocks.get(item.name);
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
        HashMap t = (HashMap) inventoryBlocks.get(item.name);
        if ((Integer) t.get("Quantity") > 1) {
            t.put("Quantity", (Integer) t.get("Quantity") - 1);
            inventoryBlocks.put(item.name, t);
        } else if ((Integer) t.get("Quantity") == 1) {
            items[(Integer) t.get("Y")][(Integer) t.get("X")] = null;
            inventoryBlocks.remove(item.name);
//            System.out.println(t);
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
