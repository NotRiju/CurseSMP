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
        Inventory gui = Bukkit.createInventory(null, 54, "§5§lCurse Guide");

        gui.setItem(10, CurseGuideGUI.create(Material.BLAZE_POWDER, "§6§lFire Curse", 
                "§7Passive: §fFire Immunity (Immune to fire/lava)", 
                "§7Active: §fFire Burst (Wide flame shockwave)"));

        gui.setItem(11, CurseGuideGUI.create(Material.ENDER_PEARL, "§5§lVoid Curse", 
                "§7Passive: §fEnd Aura (Particle trails)", 
                "§7Active: §fVoid Step (Teleport forward through walls)"));

        gui.setItem(12, CurseGuideGUI.create(Material.TRIDENT, "§b§lStorm Curse", 
                "§7Passive: §fSwift Torrent (Speed boost in rain/water)", 
                "§7Active: §fTempest Strike (Summon target lightning)"));

        gui.setItem(13, CurseGuideGUI.create(Material.BLACK_DYE, "§8§lShadow Curse", 
                "§7Passive: §fVanish (Invisibility at night)", 
                "§7Active: §fShadow Step (Teleport behind nearest target)"));

        gui.setItem(14, CurseGuideGUI.create(Material.REDSTONE, "§c§lBlood Curse", 
                "§7Passive: §fRage & Lifesteal (Heal on hit / low health boost)", 
                "§7Active: §fBlood Burst (Drain nearby health to heal)"));

        gui.setItem(15, CurseGuideGUI.create(Material.ICE, "§b§lFreeze Curse", 
                "§7Passive: §fFrost Aura (Slow entities around you)", 
                "§7Active: §fGlacial Imprisonment (Encase enemies in ice blocks)"));

        gui.setItem(16, CurseGuideGUI.create(Material.FEATHER, "§a§lWind Curse", 
                "§7Passive: §fDouble Jump (Press space twice to glide)", 
                "§7Active: §fWind Dash (Launch forward at high speed)"));

        gui.setItem(30, CurseGuideGUI.create(Material.NETHER_STAR, "§d§lDemon Curse", 
                "§7Passive: §fDemonic Strength (Permanent Strength boost)", 
                "§7Active: §fDemon Rage (Bloodlust mode: Strength, Speed & Resist)"));

        gui.setItem(31, CurseGuideGUI.create(Material.VINE, "§2§lNature Curse", 
                "§7Passive: §fSlow Heal (Passive health regeneration)", 
                "§7Active: §fEntangling Vines (Trap & Poison surrounding targets)"));

        gui.setItem(32, CurseGuideGUI.create(Material.WITHER_SKELETON_SKULL, "§7§lDeath Curse", 
                "§7Passive: §fUndying Soul (Defy fatal death and full revive)", 
                "§7Active: §fWither Blast (Fire custom explosive skulls)"));

        // Legendary/Locked Curses
        gui.setItem(49, CurseGuideGUI.create(Material.WARDEN_SPAWN_EGG, "§1§lDeep Sea Creature's Curse", 
                "§7Passive: §fAbyssal Swim (Water Breathing & swim speed boost)", 
                "§7Active: §fSonic Boom (Heavy sonic blast & Strength 3 / Resist 4)"));

        gui.setItem(48, CurseGuideGUI.create(Material.DRAGON_EGG, "§5§lDragon's Curse", 
                "§7Passive: §fDragon Flight (Permanent immunity to fall damage)", 
                "§7Active: §fDragon's Breath (lingering purple cloud & Gravity Pull)"));

        gui.setItem(50, CurseGuideGUI.create(Material.WITHER_ROSE, "§0§lWither's Curse", 
                "§7Passive: §fDecay Immunity (Immune to Wither decay effects)", 
                "§7Active: §fWither Barrage (Triple skull burst & AOE decay)"));

        // Helper Items
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
