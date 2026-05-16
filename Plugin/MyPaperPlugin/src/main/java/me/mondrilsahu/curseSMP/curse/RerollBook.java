package me.mondrilsahu.curseSMP.curse;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class RerollBook {

    public static final NamespacedKey KEY =
            new NamespacedKey(CurseSMP.get(), "reroll_book");

    public static ItemStack create(){

        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§5§lCurse Reroll Tome");

        meta.setLore(Arrays.asList(
                "§7Right click to reroll your curse",
                "§8Consumes on use"
        ));

        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);

        return item;
    }

    public static void registerRecipe(){

        ShapedRecipe recipe = new ShapedRecipe(
                new NamespacedKey(CurseSMP.get(), "reroll_book"),
                create()
        );

        recipe.shape(
                "NDN",
                "DBD",
                "NDN"
        );

        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('B', Material.BOOK);

        Bukkit.addRecipe(recipe);
    }
}