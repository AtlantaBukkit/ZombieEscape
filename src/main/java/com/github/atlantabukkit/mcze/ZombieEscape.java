package com.github.atlantabukkit.mcze;

import com.github.atlantabukkit.mcze.core.GameArena;
import com.github.atlantabukkit.mcze.core.GameManager;
import com.github.atlantabukkit.mcze.guis.HumanKitMenu;
import com.github.atlantabukkit.mcze.guis.ZombieKitMenu;
import com.github.atlantabukkit.mcze.listeners.*;
import com.github.atlantabukkit.mcze.utils.Configuration;
import com.github.atlantabukkit.mcze.utils.menus.MenuManager;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieEscape extends JavaPlugin {

    private GameArena gameArena;
    private HikariDataSource hikari;

    private GameManager gameManager;
    private MenuManager menuManager;

    private Configuration configuration;

    @Override
    public void onEnable() {
        // setupHikari();

        gameArena = new GameArena(this);
        gameManager = new GameManager();
        menuManager = new MenuManager(this);
        configuration = new Configuration(this);

        menuManager.addMenu("hkits", new HumanKitMenu("Human Kit Menu", 9));
        menuManager.addMenu("zkits", new ZombieKitMenu("Zombie Kit Menu", 9));

        registerListeners();
    }

    @Override
    public void onDisable() {
        if (hikari != null) {
            hikari.close();
        }
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new EntityDamageByEntity(this), this);
        pm.registerEvents(new FoodLevelChange(), this);
        pm.registerEvents(new PlayerDeath(this), this);
        pm.registerEvents(new PlayerDropItem(), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerPickupItem(), this);
        pm.registerEvents(new PlayerQuit(this), this);
    }

    private void setupHikari() {
        FileConfiguration config = configuration.getSettingsConfig();

        String address = config.getString("database.address");

        hikari = new HikariDataSource();

        hikari.setMaximumPoolSize(10);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", address.split(":")[0]);
        hikari.addDataSourceProperty("port", address.split(":")[1]);
        hikari.addDataSourceProperty("databaseName", config.getString("database.name"));
        hikari.addDataSourceProperty("user", config.getString("database.username"));
        hikari.addDataSourceProperty("password", config.getString("database.password"));
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public HikariDataSource getHikari() {
        return hikari;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }
}
