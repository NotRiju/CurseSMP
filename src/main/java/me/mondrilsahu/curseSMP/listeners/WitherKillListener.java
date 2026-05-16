/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Wither
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.ItemStack
 */
package me.mondrilsahu.curseSMP.listeners;

import java.util.HashMap;
import java.util.UUID;
import me.mondrilsahu.curseSMP.curse.WitherHeart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class WitherKillListener
implements Listener {
    private static final HashMap<UUID, Integer> kills = new HashMap();

    @EventHandler
    public void onWitherKill(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Wither)) {
            return;
        }
        Player killer = e.getEntity().getKiller();
        if (killer == null) {
            return;
        }
        int count = kills.getOrDefault(killer.getUniqueId(), 0) + 1;
        kills.put(killer.getUniqueId(), count);
        killer.sendMessage("\u00a77Wither kills: \u00a7e" + count + "/5");
        if (count >= 5) {
            kills.put(killer.getUniqueId(), 0);
            killer.getInventory().addItem(new ItemStack[]{WitherHeart.create()});
            killer.sendMessage("\u00a78You obtained the Wither Heart!");
        }
    }
}

