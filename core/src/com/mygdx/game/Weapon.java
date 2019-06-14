package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class  Weapon extends Item {
    public Weapon(String name, String type, int value, boolean stackable) {
        super(name, type, value, stackable);
    }

    @Override
    public void use(Player player) {
        System.out.println("THIS IS A " + name.toUpperCase());
        player.stats.put("Attack", (Integer) Main.player.stats.get("Attack") + value);
//        System.out.println(6);
    }

    @Override
    public Sprite getImg() {
        return super.getImg();
    }

    public int getValue() {
        return  value;
    }
}