package com.github.atlantabukkit.mcze.core;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.core.constants.GameState;
import com.github.atlantabukkit.mcze.core.constants.Messages;
import com.github.atlantabukkit.mcze.events.GameOverEvent;
import com.github.atlantabukkit.mcze.events.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameArena {

    private final ZombieEscape PLUGIN;
    private GameState gameState;
    private final VoteManager VOTE_MANAGER;

    private final int MINIMUM_PLAYERS = 2;

    private HashSet<UUID> humans = new HashSet<>();
    private HashSet<UUID> zombies = new HashSet<>();

    public GameArena(ZombieEscape plugin) {
        this.PLUGIN = plugin;
        gameState = GameState.WAITING;
        VOTE_MANAGER = new VoteManager();
    }

    public boolean isMinimumMet() {
        return Bukkit.getOnlinePlayers().size() >= MINIMUM_PLAYERS;
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public boolean shouldStart() {
        return gameState == GameState.WAITING && isMinimumMet();
    }

    public boolean shouldEnd() {
        return zombies.size() == 0 || humans.size() == 0;
    }

    public int getZombieSize() {
        return zombies.size();
    }

    public int getHumansSize() {
        return humans.size();
    }

    public boolean isHuman(Player player) {
        return humans.contains(player.getUniqueId());
    }

    public boolean isZombie(Player player) {
        return zombies.contains(player.getUniqueId());
    }

    public boolean isSameTeam(Player x, Player y) {
        return isHuman(x) && isHuman(y) || isZombie(x) && isZombie(y);
    }

    public void addHuman(Player player) {
        humans.add(player.getUniqueId());
    }

    public void addZombie(Player player) {
        zombies.add(player.getUniqueId());
    }

    public int getStartingZombies() {
        return (int) (0.25 * Bukkit.getOnlinePlayers().size()) + 1;
    }

    public void startCountdowm() {
        gameState = GameState.STARTING;

        new BukkitRunnable() {
            int countdown = 30;

            @Override
            public void run() {
                if (countdown != 0) {
                    countdown--;
                } else {
                    cancel();

                    if (gameState == GameState.STARTING && isMinimumMet()) {
                        startGame();
                    } else {

                    }
                }
            }
        }.runTaskTimer(PLUGIN, 0, 20);
    }

    public void startGame() {
        gameState = GameState.RUNNING;
        Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent());

        zombies.clear();
        humans.clear();

        List<UUID> random = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            random.add(player.getUniqueId());
        }

        Collections.shuffle(random);

        for (int i = 0; i < getStartingZombies(); i++) {
            zombies.add(random.get(i));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isZombie(player)) {
                continue;
            }

            addHuman(player);
        }

        Messages.GAME_STARTED.broadcast();
    }

    public void endGame() {
        gameState = GameState.RESTRICTED;
        Bukkit.getServer().getPluginManager().callEvent(new GameOverEvent());

        if (getHumansSize() == 0) {

        } else if (getZombieSize() == 0) {

        }
    }
}
