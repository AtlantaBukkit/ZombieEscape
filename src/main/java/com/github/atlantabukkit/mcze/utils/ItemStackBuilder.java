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

    public ItemStackBuilder amount(int amount) {
        ITEM.setAmount(amount);
        return this;
    }

    public ItemStackBuilder name(String name) {
        META.setDisplayName(Utils.color(name));
        ITEM.setItemMeta(META);
        return this;
    }

    public ItemStackBuilder lore(String name) {
        List<String> lore = META.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(Utils.color(name));
        META.setLore(lore);
        ITEM.setItemMeta(META);
        return this;
    }

    public ItemStackBuilder durability(int durability) {
        ITEM.setDurability((short) durability);
        return this;
    }

    public ItemStackBuilder data(int data) {
        ITEM.setData(new MaterialData(ITEM.getType(), (byte) data));
        return this;
    }

    public ItemStackBuilder enchantment(Enchantment enchantment, int level) {
        ITEM.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder enchantment(Enchantment enchantment) {
        ITEM.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder type(Material material) {
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
        if (ITEM.getType() == Material.LEATHER_BOOTS || ITEM.getType() == Material.LEATHER_CHESTPLATE
                || ITEM.getType() == Material.LEATHER_HELMET  || ITEM.getType() == Material.LEATHER_LEGGINGS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) META;
            meta.setColor(color);
            ITEM.setItemMeta(meta);
            return this;
        } else {
            throw new IllegalArgumentException("color is only applicable for leather armor!");
        }
    }

    public ItemStack build() {
        return ITEM;
    }
}
