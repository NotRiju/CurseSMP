package me.mondrilsahu.curseSMP.listeners;

import me.mondrilsahu.curseSMP.curse.CurseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        CurseManager.loadPlayer(e.getPlayer());

        if(!CurseManager.isEnabled()) return;

        if(!CurseManager.has(e.getPlayer())){
            CurseManager.random(e.getPlayer());

            e.getPlayer().sendMessage("§5You received a curse...");
        }
    }
}