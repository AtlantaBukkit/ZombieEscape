package com.github.atlantabukkit.mcze.core.constants;

public enum Achievements {

    FIRST_GAME_PLAYED(0, "First Game", "Play 1 Game"),
    LONG_TIME_PLAYER(1, "Long Time Player", "Play 100 Games");

    private int id;
    private String name;
    private String description;

    Achievements(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
