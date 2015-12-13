package com.github.atlantabukkit.mcze.core.kitcallbacks;

import com.github.atlantabukkit.mcze.core.constants.KitAction;
import com.github.atlantabukkit.mcze.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Tank implements KitAction {

    @Override
    public void giveKit(Player player) {
        player.getInventory().setChestplate(new ItemStackBuilder(Material.DIAMOND_CHESTPLATE).build());
    }

    @Override
    public void interact(PlayerInteractEvent event, ItemStack itemStack) {

    }
}
