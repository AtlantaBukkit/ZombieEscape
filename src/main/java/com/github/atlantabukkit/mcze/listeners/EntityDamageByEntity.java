package com.github.atlantabukkit.mcze.listeners;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.core.GameArena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {

    private final ZombieEscape PLUGIN;

    public EntityDamageByEntity(ZombieEscape plugin) {
        this.PLUGIN = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        final Player DAMAGED = (Player) event.getEntity();
        final Player DAMAGER = (Player) event.getDamager();

        GameArena gameArena = PLUGIN.getGameArena();

        if (gameArena.isGameRunning()) {
            if (gameArena.isSameTeam(DAMAGED, DAMAGER)) {
                event.setCancelled(true);
            } else if (gameArena.isHuman(DAMAGED) && gameArena.isZombie(DAMAGER)) {
                gameArena.addZombie(DAMAGED);

                if (gameArena.shouldEnd()) {
                    gameArena.endGame();
                }
            }
        }
    }
}
