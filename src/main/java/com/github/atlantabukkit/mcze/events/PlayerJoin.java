package com.github.atlantabukkit.mcze.events;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.core.GameArena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private ZombieEscape plugin;

    public PlayerJoin(ZombieEscape plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        GameArena gameArena = plugin.getGameArena();

        if (gameArena.isGameRunning()) {

        } else if (gameArena.shouldStart()) {
            gameArena.startCountdowm();
        }
    }
}
