package me.mondrilsahu.curseSMP.utils;

import me.mondrilsahu.curseSMP.CurseSMP;
import me.mondrilsahu.curseSMP.curse.CurseManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class BeamEffect {

    public static void play(Player p, Color color){

        Location loc = p.getLocation().clone();

        BlockDisplay beam = p.getWorld().spawn(loc, BlockDisplay.class);

        beam.setBlock(CurseManager.getGlass(color).createBlockData());

        beam.setTransformation(new Transformation(
                new Vector3f(-0.5f, -100f, -0.5f),
                new AxisAngle4f(),
                new Vector3f(1f, 1100f, 1f),
                new AxisAngle4f()
        ));

        new BukkitRunnable(){
            @Override
            public void run(){
                beam.remove();
            }
        }.runTaskLater(CurseSMP.get(), 60L);
    }
}