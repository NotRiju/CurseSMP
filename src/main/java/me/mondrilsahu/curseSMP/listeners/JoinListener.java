/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package me.mondrilsahu.curseSMP.listeners;

import me.mondrilsahu.curseSMP.curse.CurseManager;
import me.mondrilsahu.curseSMP.curse.CurseType;
import me.mondrilsahu.curseSMP.utils.BeamEffect;
import me.mondrilsahu.curseSMP.utils.CircleParticle;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener
implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        CurseManager.load(p);
        if (!CurseManager.isEnabled()) {
            return;
        }
        if (!CurseManager.has(p)) {
            CurseManager.random(p);
            CurseType type = CurseManager.getType(p);
            CircleParticle.play(p, Color.WHITE);
            BeamEffect.play(p, Color.WHITE);
            p.sendTitle("\u00a75NEW CURSE", "\u00a7d" + this.format(type), 10, 60, 10);
        } else {
            CurseType type = CurseManager.getType(p);
            p.sendMessage("\u00a77Your curse: \u00a7d" + this.format(type));
            p.sendMessage("\u00a77Level: \u00a7e" + CurseManager.getLevel(p));
        }
    }

    private String format(CurseType type) {
        return type.name().charAt(0) + type.name().substring(1).toLowerCase();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        CurseManager.savePlayer(e.getPlayer());
        CurseManager.unload(e.getPlayer());
    }
}

