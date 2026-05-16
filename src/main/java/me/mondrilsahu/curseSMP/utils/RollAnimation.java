/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.mondrilsahu.curseSMP.utils;

import java.util.Random;
import me.mondrilsahu.curseSMP.CurseSMP;
import me.mondrilsahu.curseSMP.curse.CurseType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RollAnimation {
    public static void play(final Player p, final Runnable onFinish) {
        final CurseType[] values = CurseType.values();
        final Random random = new Random();
        new BukkitRunnable(){
            int ticks = 0;

            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                CurseType randomCurse = values[random.nextInt(values.length)];
                p.sendTitle("\u00a75ROLLING...", "\u00a77" + RollAnimation.format(randomCurse), 0, 10, 0);
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.5f);
                ++this.ticks;
                if (this.ticks >= 20) {
                    this.cancel();
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
                    if (onFinish != null) {
                        onFinish.run();
                    }
                }
            }
        }.runTaskTimer((Plugin)CurseSMP.get(), 0L, 2L);
    }

    private static String format(CurseType c) {
        return c.name().charAt(0) + c.name().substring(1).toLowerCase();
    }
}

