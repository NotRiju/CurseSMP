package me.mondrilsahu.curseSMP.listeners;

import me.mondrilsahu.curseSMP.curse.WitherHeart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class WitherKillListener implements Listener {

    private final HashMap<UUID, Integer> kills = new HashMap<>();

    @EventHandler
    public void onWitherKill(EntityDeathEvent e){

        if(!(e.getEntity() instanceof Wither)) return;

        Player killer = e.getEntity().getKiller();
        if(killer == null) return;

        int amount = kills.getOrDefault(killer.getUniqueId(), 0) + 1;

        kills.put(killer.getUniqueId(), amount);

        killer.sendMessage("§7Wither kills: §e" + amount + "/5");

        if(amount >= 5){

            kills.put(killer.getUniqueId(), 0);

            killer.getInventory().addItem(WitherHeart.create());

            killer.sendMessage("§8You obtained the Wither Heart!");
        }
    }
}