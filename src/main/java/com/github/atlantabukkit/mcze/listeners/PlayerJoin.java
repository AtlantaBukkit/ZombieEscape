package com.github.atlantabukkit.mcze.listeners;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.core.GameArena;
import com.github.atlantabukkit.mcze.profiles.Profile;
import com.github.atlantabukkit.mcze.profiles.ProfileLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private ZombieEscape PLUGIN;

    public PlayerJoin(ZombieEscape plugin) {
        this.PLUGIN = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        GameArena gameArena = PLUGIN.getGameArena();

        if (gameArena.isGameRunning()) {

        } else if (gameArena.shouldStart()) {
            gameArena.startCountdowm();
        }

        final Profile PROFILE = new Profile(event.getPlayer());
        PLUGIN.getGameManager().getProfiles().put(event.getPlayer().getUniqueId(), PROFILE);
        new ProfileLoader(PROFILE, PLUGIN).runTaskAsynchronously(PLUGIN);
    }
}
