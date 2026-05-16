/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.NamespacedKey
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.persistence.PersistentDataType
 *  org.bukkit.plugin.Plugin
 */
package me.mondrilsahu.curseSMP.curse;

import java.util.Arrays;
import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class DeepSeaHeart {
    public static final NamespacedKey KEY = new NamespacedKey((Plugin)CurseSMP.get(), "deep_sea_heart");

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("\u00a71\u00a7lHeart of the Abyss");
        meta.setLore(Arrays.asList("\u00a77Right-click to obtain", "\u00a79Deep Sea Creature Curse", "", "\u00a78Dropped from mythical sea beasts"));
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        return item;
    }
}

