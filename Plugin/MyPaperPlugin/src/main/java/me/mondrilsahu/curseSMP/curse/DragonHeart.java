package me.mondrilsahu.curseSMP.curse;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class DragonHeart {

    public static final NamespacedKey KEY =
            new NamespacedKey(CurseSMP.get(), "dragon_heart");

    public static ItemStack create(){

        ItemStack item = new ItemStack(Material.DRAGON_EGG);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§5§lDragon Heart");

        meta.setLore(Arrays.asList(
                "§7Right click to obtain",
                "§5Ender Dragon Curse"
        ));

        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);

        return item;
    }
}