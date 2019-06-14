package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Armour extends Item {
    public Armour(String name, String type, int value, boolean stackable) {
        super(name, type, value, stackable);
    }

    @Override
    public void use(Player player) {
        player.stats.put("Defense", (Integer) Main.player.stats.get("Defense") + value);
    }

    @Override
    public Sprite getImg() {
        return super.getImg();
    }
}
