package com.github.atlantabukkit.mcze.profiles;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.core.constants.Achievements;
import com.github.atlantabukkit.mcze.core.constants.KitType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileLoader extends BukkitRunnable {

    private Profile profile;
    private ZombieEscape plugin;

    private static final String INSERT = "INSERT INTO data VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?";
    private static final String SELECT = "SELECT zombie_kills,human_kills,points,wins,achievements,human_kit,zombie_kit FROM data WHERE uuid=?";

    public ProfileLoader(Profile profile, ZombieEscape plugin) {
        this.profile = profile;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Connection connection = null;

        try {
            connection = plugin.getHikari().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, profile.getUuid().toString());
            preparedStatement.setString(2, profile.getName());
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 0);
            preparedStatement.setInt(6, 0);
            preparedStatement.setString(7, StringUtils.repeat("f", Achievements.values().length));
            preparedStatement.setString(8, "TANK");
            preparedStatement.setString(9, "LEAPER");
            preparedStatement.setString(10, profile.getName());

            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, profile.getUuid().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                profile.setHumanKills(resultSet.getInt("human_kills"));
                profile.setZombieKills(resultSet.getInt("zombie_kills"));
                profile.setPoints(resultSet.getInt("points"));
                profile.setWins(resultSet.getInt("wins"));
                profile.setAchievements(getAchievements(resultSet));
                profile.setHumanKit(KitType.valueOf(resultSet.getString("human_kit")));
                profile.setZombieKit(KitType.valueOf(resultSet.getString("zombie_kit")));
                profile.setLoaded(true);
            }

            preparedStatement.close();
            resultSet.close();
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

    private char[] getAchievements(ResultSet result) throws SQLException {
        char[] achieved = result.getString("achievements").toCharArray();

        if (achieved.length == Achievements.values().length) {
            return achieved;
        }

        char[] adjusted = StringUtils.repeat("f", Achievements.values().length).toCharArray();
        System.arraycopy(achieved, 0, adjusted, 0, adjusted.length);
        return adjusted;
    }
}
