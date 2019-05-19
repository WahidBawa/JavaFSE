package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    Sprite inventory = new Sprite(new Texture("ASSETS/INVENTORY/Inventory.png"));
    Item[][] items = new Item[3][7];
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

    public void open(){
//        int pos = 0;
//        for (int i = 0; i < Main.player.getInventory().size(); i++){
//            items[pos][i - pos * 7] = Main.player.getInventory().get(i);
//            if (i % 7 == 0){
//                pos++;
//            }
//        }
    }

    public void addItem(Item item){ // will add item to the first empty spot found
        boolean itemAdded = false;
        HashMap tmp = new HashMap();

        if (inventoryBlocks.get(item.name) != null){
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

                        System.out.println("ITEM ADDED");

                        break;
                    }
                    if (itemAdded) break;
                }
            }
        }
        System.out.println(inventoryBlocks);
    }

    public Item[][] getItems(){
        return items;
    }

    public Sprite getSprite() {
        return inventory;
    }
}
