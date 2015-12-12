package com.github.atlantabukkit.mcze.guis;

import com.github.atlantabukkit.mcze.utils.ItemStackBuilder;
import com.github.atlantabukkit.mcze.utils.menus.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ZombieKitMenu extends Menu {

    public ZombieKitMenu(String title, int size) {
        super(title, size);

        getInventory().addItem(new ItemStackBuilder(Material.SKULL_ITEM).withData(2).withName("&6&lExample Kit").build());
    }

    @Override
    public void click(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if (itemName == null) {
            return;
        }

        switch (itemName) {
            case "Example Kit":

        }
    }
}
