package com.github.atlantabukkit.mcze.utils;

import org.bukkit.ChatColor;

public class Utils {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
