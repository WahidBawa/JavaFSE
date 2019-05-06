package com.mygdx.game;

public class Armour extends Item {
    public Armour(String name, String type, int value) {
        super(name, type, value);
    }

    @Override
    public void use() {
        Main.player.stats.put("defense", (Integer) Main.player.stats.get("defense") + value);
    }
}
