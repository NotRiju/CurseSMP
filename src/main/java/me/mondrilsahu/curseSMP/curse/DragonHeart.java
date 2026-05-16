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

public class DragonHeart {
    public static final NamespacedKey KEY = new NamespacedKey((Plugin)CurseSMP.get(), "dragon_heart");

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.DRAGON_EGG);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("\u00a75\u00a7lDragon Heart");
        meta.setLore(Arrays.asList("\u00a77Right-click to obtain", "\u00a75Ender Dragon Curse", "", "\u00a78Power of the End"));
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        return item;
    }
}

