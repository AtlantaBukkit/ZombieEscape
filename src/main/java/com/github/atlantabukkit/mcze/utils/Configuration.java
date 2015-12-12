package com.github.atlantabukkit.mcze.utils;

import com.github.atlantabukkit.mcze.ZombieEscape;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private final ZombieEscape INSTANCE;
    private final File SETTINGS_FOLDER;
    private final File ARENAS_FOLDER;
    private final File SETTINGS_FILE;
    private final YamlConfiguration SETTINGS_CONFIG;

    public Configuration(ZombieEscape instance) {
        this.INSTANCE = instance;
        this.SETTINGS_FOLDER = new File(INSTANCE.getDataFolder(), "settings");
        this.ARENAS_FOLDER = new File(INSTANCE.getDataFolder(), "arenas");
        this.SETTINGS_FILE = new File(SETTINGS_FOLDER, "settings.yml");
        this.SETTINGS_CONFIG = YamlConfiguration.loadConfiguration(SETTINGS_FILE);

        if (!SETTINGS_FOLDER.exists()) {
            SETTINGS_FOLDER.mkdirs();
        }

        if (!ARENAS_FOLDER.exists()) {
            ARENAS_FOLDER.mkdirs();
        }

        if (!SETTINGS_CONFIG.isConfigurationSection("database")) {
            SETTINGS_CONFIG.set("database.address", "localhost:3306");
            SETTINGS_CONFIG.set("database.schema", "example");
            SETTINGS_CONFIG.set("database.username", "root");
            SETTINGS_CONFIG.set("database.password", "root");

            saveSettingsConfig();
        }
    }

    public File getArenasFolder() {
        return ARENAS_FOLDER;
    }

    public YamlConfiguration getSettingsConfig() {
        return SETTINGS_CONFIG;
    }

    @Deprecated
    public YamlConfiguration getArenaConfig(String name) {
        return null;
    }

    public void saveSettingsConfig() {
        try {
            SETTINGS_CONFIG.save(SETTINGS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveArenaConfig(String name, YamlConfiguration configuration) {
        try {
            configuration.save(new File(ARENAS_FOLDER, name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
