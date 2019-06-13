package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class  Weapon extends Item {
    public Weapon(String name, String type, int value, boolean stackable) {
        super(name, type, value, stackable);
    }

    @Override
    public void use(Player player) {
        System.out.println("THIS IS A " + name.toUpperCase());
        player.stats.put("attack", (Integer) Main.player.stats.get("attack") + value);
    }

    @Override
    public Sprite getImg() {
        return super.getImg();
    }
}