package com.mygdx.game;

public class Weapon extends Item {
    public Weapon(String name, String type, int value) {
        super(name, type, value);
    }

    @Override
    public void use(Player player) {
        System.out.println("THIS IS A " + name.toUpperCase());
        player.stats.put("attack", (Integer) Main.player.stats.get("attack") + value);
    }
}