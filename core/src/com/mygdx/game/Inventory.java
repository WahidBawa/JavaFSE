package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    Sprite inventory = new Sprite(new Texture("ASSETS/INVENTORY/Inventory.png"));
    Item[][] items = new Item[3][7];
    ArrayList<Item> owo = new ArrayList<Item>();
    HashMap inventoryBlocks = new HashMap();

    public Inventory(){
//        inventory.setSize(inventory.getWidth() * Main.PPM, inventory.getHeight() * Main.PPM);

    }

    public void render(SpriteBatch batch){
//        batch.draw(inventory, 0, 0, inventory.getWidth() * 0.2f, inventory.getHeight() * 0.2f);
        inventory.setPosition(0, 0);
        inventory.draw(batch);
    }

    public void update(SpriteBatch batch){

        render(batch);
    }

    public void open(SpriteBatch batch){
        for (int i = 0; i < items.length; i++){
            for (int n = 0; n < items[i].length; n++){
                if (items[i][n] != null){
                    Sprite tmp = items[i][n].getImg();
                    tmp.setPosition(n * tmp.getWidth(), i * tmp.getHeight());
                    tmp.draw(batch);
                }
            }
        }
    }

    public void addItem(Item item){ // will add item to the first empty spot found
        boolean itemAdded = false;
        owo.add(item); // adds to the arraylist which makes it easier to use items for testing TEST
        HashMap tmp = new HashMap();

        if (inventoryBlocks.get(item.name) != null && item.stackable){
            HashMap t = (HashMap) inventoryBlocks.get(item.name);
            tmp.put("X", t.get("X"));
            tmp.put("Y", t.get("Y"));
            tmp.put("Quantity", (Integer) t.get("Quantity") + 1);
            inventoryBlocks.put(item.name, tmp);
        }else{
            for (int i = 0; i < items.length; i++){
                for (int n = 0; n < items[i].length; n++){
                    if (items[i][n] == null && !itemAdded){
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
        System.out.println(inventoryBlocks);
    }

    public void removeItem(Item item){
        HashMap t = (HashMap) inventoryBlocks.get(item.name);
        owo.remove(item);
        if ((Integer) t.get("Quantity") > 1){
            t.put("Quantity", (Integer) t.get("Quantity") - 1);
            inventoryBlocks.put(item.name, t);
        }else if ((Integer) t.get("Quantity") == 1){
            items[(Integer) t.get("Y")][(Integer) t.get("X")] = null;
            inventoryBlocks.remove(item.name);
//            System.out.println(t);
        }

        System.out.println(inventoryBlocks);
    }

    public ArrayList<Item> getItems(){
        return owo;
    }

    public Sprite getSprite() {
        return inventory;
    }
}
