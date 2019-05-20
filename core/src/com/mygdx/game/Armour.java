package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Armour extends Item {
    public Armour(String name, String type, int value) {
        super(name, type, value);
    }

    @Override
    public void use(Player player) {
        player.stats.put("defense", (Integer) Main.player.stats.get("defense") + value);
    }

    @Override
    public Sprite getImg() {
        return super.getImg();
    }
}
