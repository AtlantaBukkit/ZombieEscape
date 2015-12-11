package com.github.atlantabukkit.mcze.utils;

import com.github.atlantabukkit.mcze.ZombieEscape;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Map.Entry;
import java.util.Set;

public class ArenaConfiguration {

    private final ZombieEscape PLUGIN;
    private final String NAME;

    private File configFile;
    private FileConfiguration config;

    public ArenaConfiguration(ZombieEscape plugin, String name) {
        this.PLUGIN = plugin;
        this.NAME = name;
        this.configFile = new File(plugin.getDataFolder(), name + ".yml");

        reloadConfig();
    }

    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(this.configFile);

        Reader defaultConfigStream = null;
        try {
            defaultConfigStream = new InputStreamReader(this.PLUGIN.getResource(this.NAME + ".yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defaultConfigStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
            this.config.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.config == null) {
            reloadConfig();
        }

        return this.config;
    }

    public boolean saveConfig() {
        if (this.config == null || this.configFile == null) {
            return false;
        }

        try {
            this.config.save(this.configFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.PLUGIN.getDataFolder(), this.NAME + ".yml");
        }

        if (!this.configFile.exists()) {
            PLUGIN.saveResource(this.NAME + ",yml", false);
        }
    }

    public Location loadArena(String name) {
        ConfigurationSection mainData = getConfig().getConfigurationSection("arenas");
        ConfigurationSection arenaData = mainData.getConfigurationSection(name);
        ConfigurationSection spawnData = arenaData.getConfigurationSection("spawn");

        return Location.deserialize(spawnData.getValues(false));
    }

    public void saveArena(String name, Location spawn) {
        ConfigurationSection mainData = getConfig().getConfigurationSection("arenas");
        ConfigurationSection section = mainData.createSection(name).createSection("spawn");

        for (Entry<String, Object> entry : spawn.serialize().entrySet()) {
            section.set(entry.getKey(), entry.getValue());
        }
    }

    public Set<String> getArenaNames() {
        ConfigurationSection arenas = getConfig().getConfigurationSection("arenas");

        return arenas.getKeys(false);
    }

    public boolean doesArenaExist(String name) {
        ConfigurationSection mainData = getConfig().getConfigurationSection("arenas");

        for (String keys : mainData.getKeys(false)) {
            if (keys.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
