/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Particle
 *  org.bukkit.Particle$DustOptions
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.util.Vector
 */
package me.mondrilsahu.curseSMP.utils;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CircleParticle {
    public static void play(final Player p, final Color color) {
        new BukkitRunnable(){
            double angle = 0.0;
            int ticks = 0;

            public void run() {
                double z;
                double x;
                int i;
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                Location loc = p.getLocation().clone().add(0.0, 0.1, 0.0);
                double radius = 5.0;
                for (Entity e : p.getWorld().getNearbyEntities(loc, radius, radius, radius)) {
                    if (e == p || !(e instanceof LivingEntity)) continue;
                    Vector dir = e.getLocation().toVector().subtract(loc.toVector()).normalize();
                    dir.multiply(1.5).setY(0.3);
                    e.setVelocity(dir);
                }
                for (double i2 = 0.0; i2 < Math.PI * 2; i2 += 0.06544984694978735) {
                    double x2 = Math.cos(i2 + this.angle) * radius;
                    double z2 = Math.sin(i2 + this.angle) * radius;
                    CircleParticle.particle(loc, x2, z2, color, 1.5f);
                }
                Location[] pts = new Location[5];
                for (i = 0; i < 5; ++i) {
                    double a = this.angle + (double)i * 1.2566370614359172;
                    x = Math.cos(a) * radius * 0.7;
                    z = Math.sin(a) * radius * 0.7;
                    pts[i] = loc.clone().add(x, 0.0, z);
                }
                CircleParticle.line(pts[0], pts[2], color);
                CircleParticle.line(pts[2], pts[4], color);
                CircleParticle.line(pts[4], pts[1], color);
                CircleParticle.line(pts[1], pts[3], color);
                CircleParticle.line(pts[3], pts[0], color);
                for (i = 0; i < 6; ++i) {
                    double a = -this.angle + (double)i * 1.0471975511965976;
                    x = Math.cos(a) * radius;
                    z = Math.sin(a) * radius;
                    for (double y = 0.0; y <= 5.0; y += 0.3) {
                        loc.getWorld().spawnParticle(Particle.DUST, loc.clone().add(x, y, z), 2, (Object)new Particle.DustOptions(color, 1.4f));
                    }
                }
                Location head = p.getLocation().clone().add(0.0, 6.0, 0.0);
                for (int i3 = 0; i3 < 8; ++i3) {
                    double a = this.angle + (double)i3 * Math.PI / 4.0;
                    double x3 = Math.cos(a) * 1.2;
                    double z3 = Math.sin(a) * 1.2;
                    head.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, head.clone().add(x3, 0.0, z3), 1);
                }
                Location rainCenter = p.getLocation().clone().add(0.0, 6.0, 0.0);
                for (int i4 = 0; i4 < 12; ++i4) {
                    x = (Math.random() - 0.5) * 4.0;
                    z = (Math.random() - 0.5) * 4.0;
                    Location spawn = rainCenter.clone().add(x, 2.0, z);
                    rainCenter.getWorld().spawnParticle(Particle.SOUL, spawn, 0, 0.0, -0.5, 0.0, 0.1);
                }
                this.angle += 0.08;
                ++this.ticks;
                if (this.ticks > 60) {
                    p.getWorld().strikeLightningEffect(p.getLocation());
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)CurseSMP.get(), 0L, 1L);
    }

    private static void particle(Location loc, double x, double z, Color color, float size) {
        loc.getWorld().spawnParticle(Particle.DUST, loc.clone().add(x, 0.0, z), 1, (Object)new Particle.DustOptions(color, size));
    }

    private static void line(Location a, Location b, Color color) {
        double d = a.distance(b);
        int points = (int)(d * 8.0);
        for (int i = 0; i <= points; ++i) {
            double t = (double)i / (double)points;
            double x = a.getX() + (b.getX() - a.getX()) * t;
            double y = a.getY() + (b.getY() - a.getY()) * t;
            double z = a.getZ() + (b.getZ() - a.getZ()) * t;
            a.getWorld().spawnParticle(Particle.DUST, new Location(a.getWorld(), x, y, z), 1, (Object)new Particle.DustOptions(color, 1.2f));
        }
    }
}

