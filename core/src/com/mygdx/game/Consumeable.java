package com.mygdx.game;

public class Consumeable extends Item {
    public Consumeable(String name, String type, int value) {
        super(name, type, value);
    }

    @Override
    public void use(Player player) {
        System.out.println("THIS IS A " + name.toUpperCase());
        player.stats.put((type.equals("HEALTH") ? "health" : "mana"), (Integer) Main.player.stats.get((type.equals("HEALTH") ? "health" : "mana")) + value);
    }
}
