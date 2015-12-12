package com.github.atlantabukkit.mcze.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {

    private final ItemStack ITEM;
    private final ItemMeta META;

    public ItemStackBuilder(final ItemStack ITEM) {
        this.ITEM = ITEM;
        this.META = ITEM.getItemMeta();
    }

    public ItemStackBuilder(final Material MATERIAL) {
        ITEM = new ItemStack(MATERIAL);
        this.META = ITEM.getItemMeta();
    }

    public ItemStackBuilder withAmount(int amount) {
        ITEM.setAmount(amount);
        return this;
    }

    public ItemStackBuilder withName(String name) {
        META.setDisplayName(Utils.color(name));
        ITEM.setItemMeta(META);
        return this;
    }

    public ItemStackBuilder withLore(String name) {
        List<String> lore = META.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(Utils.color(name));
        META.setLore(lore);
        ITEM.setItemMeta(META);
        return this;
    }

    public ItemStackBuilder withDurability(int durability) {
        ITEM.setDurability((short) durability);
        return this;
    }

    public ItemStackBuilder withData(int data) {
        ITEM.setData(new MaterialData(ITEM.getType(), (byte) data));
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment, int level) {
        ITEM.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment) {
        ITEM.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder withType(Material material) {
        ITEM.setType(material);
        return this;
    }

    public ItemStackBuilder clearLore() {
        META.setLore(new ArrayList<String>());
        ITEM.setItemMeta(META);
        return this;
    }

    public ItemStackBuilder clearEnchantments() {
        ITEM.getEnchantments().keySet().forEach(ITEM::removeEnchantment);
        return this;
    }

    public ItemStackBuilder color(Color color) {
        Material type = ITEM.getType();
        if (type == Material.LEATHER_BOOTS || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET
                || type == Material.LEATHER_LEGGINGS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) META;
            meta.setColor(color);
            ITEM.setItemMeta(meta);
            return this;
        } else {
            throw new IllegalArgumentException("withColor is only applicable for leather armor!");
        }
    }

    public ItemStack build() {
        return ITEM;
    }
}
