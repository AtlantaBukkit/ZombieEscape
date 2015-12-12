package com.github.atlantabukkit.mcze.profiles;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Profile {

    private UUID uuid;
    private String name;

    private int zombieKills;
    private int humanKills;
    private int points;
    private int wins;
    private int gamesPlayed;

    private boolean loaded;

    private char[] achievements;

    public Profile(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZombieKills() {
        return zombieKills;
    }

    public void setZombieKills(int zombieKills) {
        this.zombieKills = zombieKills;
    }

    public int getHumanKills() {
        return humanKills;
    }

    public void setHumanKills(int humanKills) {
        this.humanKills = humanKills;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public char[] getAchievements() {
        return achievements;
    }

    public void setAchievements(char[] achievements) {
        this.achievements = achievements;
    }
}
