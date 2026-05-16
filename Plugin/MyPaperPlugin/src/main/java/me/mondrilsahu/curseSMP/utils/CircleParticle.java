package me.mondrilsahu.curseSMP.utils;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CircleParticle {

    public static void play(Player p, Color color){

        new BukkitRunnable(){

            double rotation = 0;
            int ticks = 0;

            @Override
            public void run(){

                // END AFTER 6 SECONDS
                if(ticks > 120 || !p.isOnline()){
                    cancel();

                    // FINAL LIGHTNING
                    p.getWorld().strikeLightningEffect(
                            p.getLocation()
                    );

                    return;
                }

                Location center = p.getLocation();
                World world = p.getWorld();

                double radius = 5;

                // ================= MAIN ROTATING CIRCLE =================

                for(double t = 0; t < Math.PI * 2; t += 0.05){

                    double x = Math.cos(t + rotation) * radius;
                    double z = Math.sin(t + rotation) * radius;

                    Location point = center.clone().add(x, 0.1, z);

                    world.spawnParticle(
                            Particle.DUST,
                            point,
                            1,
                            new Particle.DustOptions(color, 1.8f)
                    );
                }

                // ================= ROTATING PILLARS =================

                for(int i = 0; i < 6; i++){

                    double angle =
                            ((Math.PI * 2) / 6) * i - rotation;

                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;

                    // 5 BLOCK TALL PILLARS
                    for(double y = 0; y <= 5; y += 0.2){

                        world.spawnParticle(
                                Particle.DUST,
                                center.clone().add(x, y, z),
                                1,
                                new Particle.DustOptions(color, 1.6f)
                        );
                    }

                    // ================= SPINNING HEAD PATTERN =================

                    Location headCenter =
                            center.clone().add(0, 6, 0);

                    for(double a = 0; a < Math.PI * 2; a += 0.4){

                        double px =
                                Math.cos(a + rotation * 2) * 1.2;

                        double pz =
                                Math.sin(a + rotation * 2) * 1.2;

                        world.spawnParticle(
                                Particle.SOUL_FIRE_FLAME,
                                headCenter.clone().add(px, 0, pz),
                                1,
                                0,
                                0,
                                0,
                                0
                        );
                    }
                }

                // ================= SOUL RAIN =================

                for(int i = 0; i < 15; i++){

                    double rx = (Math.random() - 0.5) * 6;
                    double rz = (Math.random() - 0.5) * 6;

                    Location rain =
                            center.clone().add(rx, 7, rz);

                    world.spawnParticle(
                            Particle.SOUL,
                            rain,
                            0,
                            0,
                            -0.15,
                            0,
                            1
                    );
                }

                // ================= PUSH ENTITIES AWAY =================

                for(Entity e : world.getNearbyEntities(
                        center,
                        radius,
                        2,
                        radius
                )){

                    if(e == p) continue;

                    Vector push = e.getLocation()
                            .toVector()
                            .subtract(center.toVector())
                            .normalize()
                            .multiply(1.5);

                    push.setY(0.2);

                    e.setVelocity(push);
                }

                // ================= ROTATION =================

                rotation += 0.08;

                ticks++;
            }

        }.runTaskTimer(CurseSMP.get(), 0L, 1L);
    }
}