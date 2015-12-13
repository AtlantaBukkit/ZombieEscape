package com.github.atlantabukkit.mcze.core.constants;

import com.github.atlantabukkit.mcze.core.kitcallbacks.Leaper;
import com.github.atlantabukkit.mcze.core.kitcallbacks.Tank;

public enum KitType {

    LEAPER("Leaper", new Leaper()),
    TANK("Tank", new Tank());

    private String name;
    private KitAction kitAction;

    KitType(String name, KitAction kitAction) {
        this.name = name;
        this.kitAction = kitAction;
    }

    public String getName() {
        return name;
    }

    public KitAction getKitAction() {
        return kitAction;
    }
}
