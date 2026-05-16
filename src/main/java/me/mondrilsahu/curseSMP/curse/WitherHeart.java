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

public class WitherHeart {
    public static final NamespacedKey KEY = new NamespacedKey((Plugin)CurseSMP.get(), "wither_heart");

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.WITHER_ROSE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("\u00a78\u00a7lWither Heart");
        meta.setLore(Arrays.asList("\u00a77Right-click to obtain", "\u00a78Wither Curse", "", "\u00a78Earned by slaying 5 Withers"));
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, (Object)1);
        item.setItemMeta(meta);
        return item;
    }
}

