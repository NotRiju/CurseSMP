package me.mondrilsahu.curseSMP.utils;

import me.mondrilsahu.curseSMP.CurseSMP;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LegendaryAnimation {

    // ================= OBTAIN ANIMATION =================

    public static void play(String curse, String player){

        String base = "The " + curse + " was obtained!";

        new BukkitRunnable(){

            int step = 0;

            @Override
            public void run(){

                // FINAL FRAME
                if(step >= base.length()){

                    for(Player p : Bukkit.getOnlinePlayers()){

                        p.sendTitle(
                                "§5§l" + base,
                                "§7by §e" + player,
                                10, 60, 20
                        );

                        // FINAL IMPACT SOUND
                        p.playSound(
                                p.getLocation(),
                                getFinalSound(curse),
                                1f,
                                1f
                        );
                    }

                    cancel();
                    return;
                }

                String partial = base.substring(0, step + 1);

                for(Player p : Bukkit.getOnlinePlayers()){

                    // SPIN / TYPEWRITER EFFECT
                    p.sendTitle(
                            "§5" + partial,
                            "§7by §e" + player,
                            0,
                            10,
                            0
                    );

                    // ARCADE TICK SOUND
                    p.playSound(
                            p.getLocation(),
                            Sound.BLOCK_NOTE_BLOCK_HAT,
                            0.4f,
                            1.8f
                    );
                }

                step++;
            }

        }.runTaskTimer(CurseSMP.get(), 0L, 2L);
    }

    // ================= REMOVE ANIMATION =================

    public static void playRemove(String curse, String player){

        String base = "The " + curse + " was removed!";

        new BukkitRunnable(){

            int step = base.length();

            @Override
            public void run(){

                // FINAL FRAME
                if(step <= 0){

                    for(Player p : Bukkit.getOnlinePlayers()){

                        p.playSound(
                                p.getLocation(),
                                getRemoveSound(curse),
                                1f,
                                0.8f
                        );
                    }

                    cancel();
                    return;
                }

                String partial = base.substring(0, step);

                for(Player p : Bukkit.getOnlinePlayers()){

                    p.sendTitle(
                            "§c" + partial,
                            "§7from §e" + player,
                            0,
                            10,
                            0
                    );

                    // DARK TICK SOUND
                    p.playSound(
                            p.getLocation(),
                            Sound.BLOCK_NOTE_BLOCK_BASS,
                            0.5f,
                            0.6f
                    );
                }

                step--;
            }

        }.runTaskTimer(CurseSMP.get(), 0L, 2L);
    }

    // ================= FINAL SOUNDS =================

    private static Sound getFinalSound(String curse){

        String lower = curse.toLowerCase();

        if(lower.contains("dragon")){
            return Sound.ENTITY_ENDER_DRAGON_GROWL;
        }

        if(lower.contains("wither")){
            return Sound.ENTITY_WITHER_SPAWN;
        }

        if(lower.contains("deep")){
            return Sound.AMBIENT_UNDERWATER_ENTER;
        }

        return Sound.UI_TOAST_CHALLENGE_COMPLETE;
    }

    // ================= REMOVE SOUNDS =================

    private static Sound getRemoveSound(String curse){

        String lower = curse.toLowerCase();

        if(lower.contains("dragon")){
            return Sound.ENTITY_ENDER_DRAGON_DEATH;
        }

        if(lower.contains("wither")){
            return Sound.ENTITY_WITHER_DEATH;
        }

        if(lower.contains("deep")){
            return Sound.AMBIENT_UNDERWATER_EXIT;
        }

        return Sound.BLOCK_BEACON_DEACTIVATE;
    }
}