package com.github.atlantabukkit.mcze.utils.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MenuManager implements Listener {

    private Map<Object, Menu> menus = new HashMap<>();

    public MenuManager(Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void addMenu(Object key, Menu menu) {
        menus.put(key, menu);
    }

    public Collection<Menu> getMenus() {
        return menus.values();
    }

    public Menu getMenu(Object key) {
        return menus.get(key);
    }

    @EventHandler
    public void onClick(InventoryDragEvent event) {
        Inventory inv = event.getWhoClicked().getOpenInventory().getTopInventory();
        menus.values().stream().filter(menu -> menu.getInventory().getName().equals(inv.getName())).forEach(menu -> {
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = player.getOpenInventory().getTopInventory();
        menus.values().stream().filter(menu -> menu.getInventory().getName().equals(inv.getName())).forEach(menu -> {
            event.setCancelled(true);
            menu.click(player, event.getCurrentItem());
        });
    }
}
