package com.github.atlantabukkit.mcze.core.kitcallbacks;

import com.github.atlantabukkit.mcze.core.constants.KitAction;
import com.github.atlantabukkit.mcze.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Leaper implements KitAction {

    @Override
    public void giveKit(Player player) {
        player.getInventory().addItem(new ItemStackBuilder(Material.CARROT_ITEM).withName("&bKarrot").build());
    }

    @Override
    public void interact(PlayerInteractEvent event, ItemStack itemStack) {

    }
}
