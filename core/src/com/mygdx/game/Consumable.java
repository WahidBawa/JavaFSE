package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Consumable extends Item {
    public Consumable(String name, String type, int value, boolean stackable) {
        super(name, type, value, stackable);
    }

    @Override
    public void use(Player player) {
        System.out.println("THIS IS A " + name.toUpperCase());
        player.stats.put((type.equals("health") ? "Health" : "Mana"), (Integer) Main.player.stats.get((type.equals("health") ? "Health" : "Mana")) + value);
    }

    @Override
    public Sprite getImg() {
        return super.getImg();
    }
}
