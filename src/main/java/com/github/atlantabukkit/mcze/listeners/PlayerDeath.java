package com.github.atlantabukkit.mcze.listeners;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.core.GameArena;
import com.github.atlantabukkit.mcze.profiles.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private ZombieEscape PLUGIN;

    public PlayerDeath(ZombieEscape plugin) {
        this.PLUGIN = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        GameArena gameArena = PLUGIN.getGameArena();

        if (!gameArena.isGameRunning()) {
            return;
        }

        if (gameArena.isHuman(player)) {
            gameArena.addZombie(player);

            if (player.getKiller() != null) {
                Profile zombieProfile = PLUGIN.getGameManager().getProfile(player.getKiller());
                zombieProfile.setHumanKills(zombieProfile.getHumanKills() + 1);
            }
        } else if (gameArena.isZombie(player)) {

        }

        if (gameArena.isGameRunning() && gameArena.shouldEnd()) {
            gameArena.endGame();
        }
    }
}
