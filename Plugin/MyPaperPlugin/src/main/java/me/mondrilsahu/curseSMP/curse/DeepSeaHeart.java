package me.mondrilsahu.curseSMP.curse;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class DeepSeaHeart {

    public static final NamespacedKey KEY =
            new NamespacedKey(CurseSMP.get(), "deep_sea_heart");

    public static ItemStack create(){

        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§1§lHeart of the Abyss");

        meta.setLore(Arrays.asList(
                "§7Right click to obtain",
                "§1Deep Sea Creature Curse"
        ));

        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);

        return item;
    }
}