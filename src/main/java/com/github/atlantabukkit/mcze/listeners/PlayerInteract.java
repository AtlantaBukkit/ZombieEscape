package com.github.atlantabukkit.mcze.listeners;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.utils.Utils;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInteract implements Listener {

    private ZombieEscape plugin;

    public PlayerInteract(ZombieEscape plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() instanceof Sign) {
                final Sign sign = (Sign) event.getClickedBlock();

                if (sign.hasMetadata("clicked")) {
                    return;
                }

                sign.setMetadata("clicked", new FixedMetadataValue(plugin, true));
                player.sendMessage(Utils.color("&aYou have activated this door!"));

                new BukkitRunnable() {
                    String[] lines = sign.getLine(1).replace("s", "").split(" ");
                    int time = Integer.parseInt(lines[1]);

                    @Override
                    public void run() {
                        if (time != 1) {
                            time--;
                            sign.setLine(1, lines[0] + " " + time + "s");
                            sign.update();
                        } else {

                        }
                    }
                }.runTaskTimerAsynchronously(plugin, 0, 20);
            }
        }
    }
}
