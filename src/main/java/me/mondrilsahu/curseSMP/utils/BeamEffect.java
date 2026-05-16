/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.entity.BlockDisplay
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.util.Transformation
 *  org.joml.AxisAngle4f
 *  org.joml.Vector3f
 */
package me.mondrilsahu.curseSMP.utils;

import me.mondrilsahu.curseSMP.CurseSMP;
import me.mondrilsahu.curseSMP.curse.CurseManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class BeamEffect {
    public static void play(Player p, Color color) {
        Location loc = p.getLocation().clone();
        loc.setYaw(0.0f);
        loc.setPitch(0.0f);
        final BlockDisplay beam = (BlockDisplay)p.getWorld().spawn(loc, BlockDisplay.class);
        beam.setBlock(CurseManager.getGlass(color).createBlockData());
        float width = 1.0f;
        beam.setTransformation(new Transformation(new Vector3f(-width / 2.0f, 0.0f, -width / 2.0f), new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f), new Vector3f(width, 1000.0f, width), new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f)));
        beam.setInterpolationDuration(0);
        new BukkitRunnable(){

            public void run() {
                beam.remove();
            }
        }.runTaskLater((Plugin)CurseSMP.get(), 60L);
    }
}

