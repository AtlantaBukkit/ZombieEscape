package com.github.atlantabukkit.mcze.core.constants;

import com.github.atlantabukkit.mcze.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Messages {

    GAME_STARTED("&a&lTHE GAME STARTED!"),
    GAME_ENDED("&c&lTHE GAME ENDED!");

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
