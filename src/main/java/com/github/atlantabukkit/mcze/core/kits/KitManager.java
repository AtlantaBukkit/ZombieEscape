package com.github.atlantabukkit.mcze.core.kits;

import com.github.atlantabukkit.mcze.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Set;

public class KitManager {

    private Set<Kit> kits = new HashSet<>();
    private static KitManager instance;

    public static KitManager getInstance(){
        if(instance == null) instance = new KitManager();
        return instance;
    }

    public Kit getKit(String name) {
        for (Kit k : getKits()) {
            if (k.getName().equalsIgnoreCase(name)) return k;
        }
        return null;
    }

    public void loadKit(Kit kit){
        kits.add(kit);
    }

    public void openGUIForPlayer(Player p) {
        Inventory inv = Bukkit.createInventory(p, roundNumber(getKits().size()),"Kit Menu");

        for (Kit k : getKits()) {
            ItemStack stack = k.getIcon();
            ItemMeta meta = stack.getItemMeta();

            if (p.hasPermission(k.getPerm())) {
                meta.setDisplayName(Utils.color("&a" + k.getName()));
            } else {
                meta.setDisplayName(Utils.color("&c" + k.getName()));
            }

            stack.setItemMeta(meta);
            inv.addItem(stack);
        }
        p.openInventory(inv);
    }

    public Set<Kit> getKits() {
        return kits;
    }

    public int roundNumber(int number){
        if(number > 0 && number <= 9) {
            return 9;
        } else if(number > 9 && number <= 18)  {
            return 18;
        } else if(number > 18 && number <= 27) {
            return 27;
        } else if(number > 27 && number <= 36) {
            return 36;
        } else if(number > 36 && number <= 45) {
            return 45;
        } else if(number > 45 && number <= 54) {
            return 54;
        }
        return 54;
    }
}
