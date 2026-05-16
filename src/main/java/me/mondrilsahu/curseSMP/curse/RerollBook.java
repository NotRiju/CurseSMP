
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

public class RerollBook {
    public static final NamespacedKey KEY = new NamespacedKey((Plugin)CurseSMP.get(), "reroll_book");

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("\u00a75\u00a7lCurse Reroll Tome");
        meta.setLore(Arrays.asList("\u00a77Right-click to reroll your curse", "\u00a78Consumes on use", "", "\u00a7dForged with forbidden magic"));
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        return item;
    }

    public static void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey((Plugin)CurseSMP.get(), "reroll_book"), RerollBook.create());
        recipe.shape(new String[]{"NDN", "DBD", "NDN"});
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('B', Material.BOOK);
        Bukkit.addRecipe((Recipe)recipe);
    }
}

