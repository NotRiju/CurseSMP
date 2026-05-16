package me.mondrilsahu.curseSMP.curse;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class LevelItem {

    public static final NamespacedKey KEY =
            new NamespacedKey(CurseSMP.get(), "level_item");

    public static ItemStack create(){

        ItemStack item = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§b§lCurse Level Orb");

        meta.setLore(Arrays.asList(
                "§7Right click to gain +1 level",
                "§7Max level: 3"
        ));

        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);

        return item;
    }
}