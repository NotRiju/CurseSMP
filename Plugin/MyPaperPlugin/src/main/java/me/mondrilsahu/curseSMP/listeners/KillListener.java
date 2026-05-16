package me.mondrilsahu.curseSMP.listeners;

import me.mondrilsahu.curseSMP.curse.CurseManager;
import me.mondrilsahu.curseSMP.curse.LevelItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent e){

        Player dead = e.getEntity();
        Player killer = dead.getKiller();

        if(killer == null) return;

        if(!CurseManager.has(dead)) return;
        if(!CurseManager.has(killer)) return;

        int deadLevel = CurseManager.getLevel(dead);
        int killerLevel = CurseManager.getLevel(killer);

        if(deadLevel <= 1) return;

        CurseManager.setLevel(dead, deadLevel - 1);

        if(killerLevel < 3){
            CurseManager.setLevel(killer, killerLevel + 1);
        } else {
            dead.getWorld().dropItemNaturally(dead.getLocation(), LevelItem.create());
        }
    }
}