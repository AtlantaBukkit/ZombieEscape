package com.github.atlantabukkit.mcze;

import com.github.atlantabukkit.mcze.core.GameArena;
import com.github.atlantabukkit.mcze.core.GameManager;
import com.github.atlantabukkit.mcze.listeners.*;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieEscape extends JavaPlugin {

    private GameArena gameArena;
    private HikariDataSource hikari;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        setupHikari();

        gameArena = new GameArena(this);
        gameManager = new GameManager();

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
        FileConfiguration config = getConfig();
        hikari = new HikariDataSource();

        hikari.setMaximumPoolSize(10);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", config.getString("database.address"));
        hikari.addDataSourceProperty("port", "3306");
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
}
