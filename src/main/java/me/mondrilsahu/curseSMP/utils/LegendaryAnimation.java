/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.mondrilsahu.curseSMP.utils;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LegendaryAnimation {
    public static void play(final String curse, final String player) {
        final String base = "The " + curse + " was obtained!";
        new BukkitRunnable(){
            int step = 0;

            public void run() {
                if (this.step >= base.length()) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendTitle("\u00a75\u00a7l" + base, "\u00a77by \u00a7e" + player, 10, 60, 20);
                        p.playSound(p.getLocation(), LegendaryAnimation.getFinalSound(curse), 1.0f, 1.0f);
                    }
                    this.cancel();
                    return;
                }
                String partial = base.substring(0, this.step + 1);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle("\u00a75" + partial, "\u00a77by \u00a7e" + player, 0, 10, 0);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.4f, 1.8f);
                }
                ++this.step;
            }
        }.runTaskTimer((Plugin)CurseSMP.get(), 0L, 2L);
    }

    public static void playRemove(final String curse, final String player) {
        final String base = "The " + curse + " was removed!";
        new BukkitRunnable(){
            int step;
            {
                this.step = base.length();
            }

            public void run() {
                if (this.step <= 0) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), LegendaryAnimation.getRemoveSound(curse), 1.0f, 0.8f);
                    }
                    this.cancel();
                    return;
                }
                String partial = base.substring(0, this.step);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle("\u00a7c" + partial, "\u00a77from \u00a7e" + player, 0, 10, 0);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.5f, 0.6f);
                }
                --this.step;
            }
        }.runTaskTimer((Plugin)CurseSMP.get(), 0L, 2L);
    }

    private static Sound getFinalSound(String curse) {
        if (curse.toLowerCase().contains("dragon")) {
            return Sound.ENTITY_ENDER_DRAGON_GROWL;
        }
        if (curse.toLowerCase().contains("wither")) {
            return Sound.ENTITY_WITHER_SPAWN;
        }
        if (curse.toLowerCase().contains("deep")) {
            return Sound.AMBIENT_UNDERWATER_ENTER;
        }
        return Sound.UI_TOAST_CHALLENGE_COMPLETE;
    }

    private static Sound getRemoveSound(String curse) {
        if (curse.toLowerCase().contains("dragon")) {
            return Sound.ENTITY_ENDER_DRAGON_DEATH;
        }
        if (curse.toLowerCase().contains("wither")) {
            return Sound.ENTITY_WITHER_DEATH;
        }
        if (curse.toLowerCase().contains("deep")) {
            return Sound.AMBIENT_UNDERWATER_EXIT;
        }
        return Sound.BLOCK_BEACON_DEACTIVATE;
    }
}

