package me.mondrilsahu.curseSMP.curse;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class WitherHeart {

    public static final NamespacedKey KEY =
            new NamespacedKey(CurseSMP.get(), "wither_heart");

    public static ItemStack create(){

        ItemStack item = new ItemStack(Material.WITHER_ROSE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§8§lWither Heart");

        meta.setLore(Arrays.asList(
                "§7Right click to obtain",
                "§8Wither Curse"
        ));

        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);

        return item;
    }
}