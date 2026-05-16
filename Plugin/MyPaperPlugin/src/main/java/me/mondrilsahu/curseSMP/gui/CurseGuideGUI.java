package me.mondrilsahu.curseSMP.gui;

import me.mondrilsahu.curseSMP.curse.LevelItem;
import me.mondrilsahu.curseSMP.curse.RerollBook;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CurseGuideGUI {

    public static void open(Player p){

        Inventory gui = Bukkit.createInventory(null, 54, "§5§lCurse Guide");

        gui.setItem(10, create(Material.BLAZE_POWDER, "§6§lFire Curse", "§7Burn abilities"));
        gui.setItem(11, create(Material.ENDER_PEARL, "§5§lVoid Curse", "§7Teleportation abilities"));
        gui.setItem(12, create(Material.TRIDENT, "§b§lStorm Curse", "§7Lightning abilities"));
        gui.setItem(13, create(Material.BLACK_DYE, "§8§lShadow Curse", "§7Darkness abilities"));
        gui.setItem(14, create(Material.REDSTONE, "§c§lBlood Curse", "§7Lifesteal abilities"));
        gui.setItem(15, create(Material.ICE, "§b§lFreeze Curse", "§7Ice abilities"));
        gui.setItem(16, create(Material.FEATHER, "§a§lWind Curse", "§7Movement abilities"));
        gui.setItem(28, create(Material.NETHER_STAR, "§5§lDemon Curse", "§7Demonic abilities"));
        gui.setItem(29, create(Material.VINE, "§2§lNature Curse", "§7Nature abilities"));
        gui.setItem(30, create(Material.WITHER_SKELETON_SKULL, "§7§lDeath Curse", "§7Death abilities"));

        gui.setItem(45, RerollBook.create());
        gui.setItem(53, LevelItem.create());

        p.openInventory(gui);
    }

    private static ItemStack create(Material material, String name, String lore){

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}