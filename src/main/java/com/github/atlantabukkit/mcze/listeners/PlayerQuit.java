package com.github.atlantabukkit.mcze.listeners;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.core.GameArena;
import com.github.atlantabukkit.mcze.profiles.Profile;
import com.github.atlantabukkit.mcze.profiles.ProfileSaver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final ZombieEscape PLUGIN;

    public PlayerQuit(ZombieEscape plugin) {
        this.PLUGIN = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        GameArena gameArena = PLUGIN.getGameArena();

        gameArena.purgePlayer(event.getPlayer());

        if (gameArena.isGameRunning()) {
            if (gameArena.shouldEnd()) {
                gameArena.endGame();
            }
        }

        // final Profile PROFILE = PLUGIN.getGameManager().getRemovedProfile(event.getPlayer());
        // new ProfileSaver(PROFILE, PLUGIN).runTaskAsynchronously(PLUGIN);
    }
}
