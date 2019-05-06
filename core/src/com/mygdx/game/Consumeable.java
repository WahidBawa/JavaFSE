package com.mygdx.game;

public class Consumeable extends Item {
    public Consumeable(String name, String type, int value) {
        super(name, type, value);
    }

    @Override
    public void use() {
        System.out.println("THIS IS A " + name.toUpperCase());
        Main.player.stats.put((type.equals("HEALTH") ? "health" : "mana"), (Integer) Main.player.stats.get((type.equals("HEALTH") ? "health" : "mana")) + value);
    }
}
