package com.github.atlantabukkit.mcze.core;

import com.github.atlantabukkit.mcze.ZombieEscape;
import com.github.atlantabukkit.mcze.core.constants.GameState;
import com.github.atlantabukkit.mcze.core.constants.KitType;
import com.github.atlantabukkit.mcze.core.constants.Messages;
import com.github.atlantabukkit.mcze.events.GameOverEvent;
import com.github.atlantabukkit.mcze.events.GameStartEvent;
import com.github.atlantabukkit.mcze.profiles.Profile;
import com.github.atlantabukkit.mcze.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    public int getStartingZombies() {
        return (int) (0.25 * Bukkit.getOnlinePlayers().size() + 1);
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

    public void purgePlayer(Player player) {
        zombies.remove(player.getUniqueId());
        humans.remove(player.getUniqueId());
    }

    public void addHuman(Player player) {
        zombies.remove(player.getUniqueId());
        humans.add(player.getUniqueId());
    }

    public void addZombie(Player player) {
        humans.remove(player.getUniqueId());
        zombies.add(player.getUniqueId());
    }

    public void giveKit(Player player) {
        Profile profile = PLUGIN.getGameManager().getProfile(player);
        KitType kitType = isHuman(player) ? profile.getHumanKit() : profile.getZombieKit();
        kitType.getKitAction().giveKit(player);
    }

    public void startCountdown() {
        gameState = GameState.STARTING;

        new BukkitRunnable() {
            int countdown = 30;

            @Override
            public void run() {
                if (countdown != 0) {
                    countdown--;
                    Bukkit.broadcastMessage(ChatColor.GOLD + "Countdown: " + countdown);
                } else {
                    cancel();

                    if (gameState == GameState.STARTING && isMinimumMet()) {
                        startGame();
                    } else {
                        gameState = GameState.WAITING;
                    }
                }
            }
        }.runTaskTimer(PLUGIN, 0, 20);
    }

    public void startGame() {
        gameState = GameState.RUNNING;
        Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent());

        final String YOU_ARE_ZOMBIE = Utils.color("&aYou are a zombie!");
        final String YOU_ARE_HUMAN = Utils.color("&eYou are a human!");

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
                player.sendMessage(YOU_ARE_ZOMBIE);
                PLUGIN.getMenuManager().getMenu("zkits").show(player);
                continue;
            }

            player.sendMessage(YOU_ARE_HUMAN);
            PLUGIN.getMenuManager().getMenu("hkits").show(player);
            addHuman(player);
        }

        Messages.GAME_STARTED.broadcast();
    }

    public void endGame() {
        gameState = GameState.RESTRICTED;
        Bukkit.getServer().getPluginManager().callEvent(new GameOverEvent());

        Messages.GAME_ENDED.broadcast();

        if (getHumansSize() == 0) {

        } else if (getZombieSize() == 0) {

        }
    }
}
