/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.NamespacedKey
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.Recipe
 *  org.bukkit.inventory.ShapedRecipe
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.persistence.PersistentDataType
 *  org.bukkit.plugin.Plugin
 */
package me.mondrilsahu.curseSMP.curse;

import java.util.Arrays;
import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class LevelItem {
    public static final NamespacedKey KEY = new NamespacedKey((Plugin)CurseSMP.get(), "level_item");

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("\u00a7b\u00a7lCurse Level Orb");
        meta.setLore(Arrays.asList("\u00a77Right-click to gain +1 level", "\u00a78Max Level: 3", "", "\u00a7dCondensed cursed energy"));
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, (Object)1);
        item.setItemMeta(meta);
        return item;
    }

    public static void recipelevel() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey((Plugin)CurseSMP.get(), "level_item"), LevelItem.create());
        recipe.shape(new String[]{"NDN", "DBD", "NDN"});
        recipe.setIngredient('N', Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('B', Material.TOTEM_OF_UNDYING);
        Bukkit.addRecipe((Recipe)recipe);
    }
}

