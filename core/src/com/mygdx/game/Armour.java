package com.mygdx.game;

public class Armour extends Item{
    public Armour(String name, String type, int value) {
        super(name, type, value);
    }

    @Override
    public void use() {
        System.out.println("THIS IS A " + name.toUpperCase());
    }
}
