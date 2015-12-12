package com.github.atlantabukkit.mcze.profiles;

import com.github.atlantabukkit.mcze.ZombieEscape;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.SQLException;

public class ProfileSaver extends BukkitRunnable {

    private Profile profile;
    private ZombieEscape plugin;

    public ProfileSaver(Profile profile, ZombieEscape plugin) {
        this.profile = profile;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Connection connection = null;

        try {
            connection = plugin.getHikari().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
