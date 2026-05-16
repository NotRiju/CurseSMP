/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.mondrilsahu.curseSMP.curse;

import java.util.Arrays;
import me.mondrilsahu.curseSMP.curse.LevelItem;
import me.mondrilsahu.curseSMP.curse.RerollBook;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CurseGuideGUI {
    public static void open(Player p) {
        Inventory gui = Bukkit.createInventory(null, (int)54, (String)"\u00a75\u00a7lCurse Guide");
        gui.setItem(10, CurseGuideGUI.create(Material.BLAZE_POWDER, "\u00a76Fire Curse", "\u00a77Fire Burst", "\u00a77Inferno Ring"));
        gui.setItem(11, CurseGuideGUI.create(Material.ENDER_PEARL, "\u00a75Void Curse", "\u00a77Void Teleport", "\u00a77Void Collapse"));
        gui.setItem(12, CurseGuideGUI.create(Material.TRIDENT, "\u00a7bStorm Curse", "\u00a77Lightning Strike", "\u00a77Thunderstorm"));
        gui.setItem(13, CurseGuideGUI.create(Material.BLACK_DYE, "\u00a78Shadow Curse", "\u00a77Shadow Teleport", "\u00a77Shadow Realm"));
        gui.setItem(14, CurseGuideGUI.create(Material.REDSTONE, "\u00a7cBlood Curse", "\u00a77Lifesteal Hit", "\u00a77Blood Burst"));
        gui.setItem(15, CurseGuideGUI.create(Material.ICE, "\u00a7bFreeze Curse", "\u00a77Freeze Blast", "\u00a77Ice Prison"));
        gui.setItem(16, CurseGuideGUI.create(Material.FEATHER, "\u00a7aWind Curse", "\u00a77Wind Dash", "\u00a77Tornado"));
        gui.setItem(30, CurseGuideGUI.create(Material.NETHER_STAR, "\u00a7dDemon Curse", "\u00a77Demon Strength", "\u00a77Demon Rage"));
        gui.setItem(31, CurseGuideGUI.create(Material.VINE, "\u00a72Nature Curse", "\u00a77Vine Trap", "\u00a77Nature Burst"));
        gui.setItem(32, CurseGuideGUI.create(Material.WITHER_SKELETON_SKULL, "\u00a77Death Curse", "\u00a77Wither Strike", "\u00a77Death Pulse"));
        gui.setItem(49, CurseGuideGUI.create(Material.WARDEN_SPAWN_EGG, "\u00a7x\u00a70\u00a70\u00a70\u00a70\u00a78\u00a7B\u00a7lDeep Sea Creature's Curse", "\u00a75\u00a7lStrength 3 and Resistance 4 for 10 seconds", "\u00a75\u00a7lSonic boom"));
        gui.setItem(48, CurseGuideGUI.create(Material.DRAGON_EGG, "\u00a7c\u00a7lDragon's Curse", "\u00a76\u00a7lDragon's Breath", "\u00a76\u00a7lGraity Pull"));
        gui.setItem(50, CurseGuideGUI.create(Material.WITHER_ROSE, "\u00a70\u00a7lWither's Curse", "\u00a7f\u00a7lExplosive Skulls", "\u00a7f\u00a7lDecay"));
        gui.setItem(46, RerollBook.create());
        gui.setItem(52, LevelItem.create());
        p.openInventory(gui);
    }

    private static ItemStack create(Material mat, String name, String ... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}

