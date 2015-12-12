package com.github.atlantabukkit.mcze.core.constants;

import com.github.atlantabukkit.mcze.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Messages {

    GAME_STARTED("&3The game has started!");

    private String message;

    Messages(String message) {
        this.message = Utils.color(message);
    }

    public void send(Player player) {
        player.sendMessage(message);
    }

    public void send(Player player, String replacement) {
        player.sendMessage(message.replace("%s", replacement));
    }

    public void broadcast() {
        Bukkit.broadcastMessage(message);
    }
}
