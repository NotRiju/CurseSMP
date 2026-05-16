package me.mondrilsahu.curseSMP.utils;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RollAnimation {

    public static void play(Player p, Runnable finish){

        new BukkitRunnable(){

            int ticks = 0;

            @Override
            public void run(){

                if(ticks >= 40){

                    p.playSound(
                            p.getLocation(),
                            Sound.UI_TOAST_CHALLENGE_COMPLETE,
                            1f, 1f
                    );

                    finish.run();

                    cancel();
                    return;
                }

                p.sendTitle(
                        "§5Rolling...",
                        "§dYour fate is changing",
                        0, 10, 0
                );

                p.playSound(
                        p.getLocation(),
                        Sound.BLOCK_NOTE_BLOCK_HAT,
                        0.5f, 1.8f
                );

                ticks++;
            }

        }.runTaskTimer(CurseSMP.get(), 0L, 2L);
    }
}