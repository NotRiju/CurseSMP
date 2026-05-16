/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 */
package me.mondrilsahu.curseSMP.listeners;

import me.mondrilsahu.curseSMP.curse.CurseManager;
import me.mondrilsahu.curseSMP.curse.LevelItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener
implements Listener {
    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        if (killer == null) {
            return;
        }
        int killerLevel = CurseManager.getLevel(killer);
        int victimLevel = CurseManager.getLevel(victim);
        if (victimLevel <= 0) {
            return;
        }
        if (killerLevel >= 3) {
            CurseManager.setLevel(victim, victimLevel - 1);
            victim.getWorld().dropItemNaturally(victim.getLocation(), LevelItem.create());
        } else {
            CurseManager.setLevel(victim, victimLevel - 1);
            CurseManager.setLevel(killer, killerLevel + 1);
        }
    }
}

