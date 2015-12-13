package com.github.atlantabukkit.mcze.core.constants;

public enum Achievements {

    FIRST_GAME_PLAYED("First Game", "Play 1 Game"),
    LONG_TIME_PLAYER("Long Time Player", "Play 100 Games");

    private final String NAME;
    private final String DESCRIPTION;

    Achievements(String name, String description) {
        this.NAME = name;
        this.DESCRIPTION = description;
    }

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }
}
