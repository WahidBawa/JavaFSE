package com.mygdx.game;

public class Weapon extends Item {
    public Weapon(String name, String type, int value) {
        super(name, type, value);
    }

    @Override
    public void use() {
        System.out.println("THIS IS A " + name.toUpperCase());
        Main.player.stats.put("attack", (Integer) Main.player.stats.get("attack") + value);
    }
}