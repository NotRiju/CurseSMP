/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.persistence.PersistentDataType
 */
package me.mondrilsahu.curseSMP.listeners;

import me.mondrilsahu.curseSMP.curse.CurseManager;
import me.mondrilsahu.curseSMP.curse.CurseType;
import me.mondrilsahu.curseSMP.curse.DeepSeaHeart;
import me.mondrilsahu.curseSMP.curse.DragonHeart;
import me.mondrilsahu.curseSMP.curse.LevelItem;
import me.mondrilsahu.curseSMP.curse.RerollBook;
import me.mondrilsahu.curseSMP.curse.WitherHeart;
import me.mondrilsahu.curseSMP.utils.LegendaryAnimation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class UseListener
implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        if (item.getItemMeta().getPersistentDataContainer().has(RerollBook.KEY, PersistentDataType.INTEGER)) {
            if (!CurseManager.isEnabled()) {
                p.sendMessage("\u00a7cCurse system is disabled.");
                return;
            }
            if (CurseManager.isLocked(CurseManager.getType(p))) {
                p.sendMessage("\u00a7cYour curse cannot be rerolled.");
                return;
            }
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("curse reroll " + p.getName()));
            this.consume(item, p);
            return;
        }
        if (item.getItemMeta().getPersistentDataContainer().has(LevelItem.KEY, PersistentDataType.INTEGER)) {
            if (!CurseManager.has(p)) {
                p.sendMessage("\u00a7cYou don't have a curse.");
                return;
            }
            int level = CurseManager.getLevel(p);
            if (level >= 3) {
                p.sendMessage("\u00a7cYou are already max level!");
                return;
            }
            CurseManager.setLevel(p, level + 1);
            p.sendMessage("\u00a7aLevel increased to \u00a7e" + (level + 1));
            this.consume(item, p);
        }
        if (item.getItemMeta().getPersistentDataContainer().has(DeepSeaHeart.KEY, PersistentDataType.INTEGER)) {
            if (!CurseManager.isObtainable(CurseType.DEEP_SEA_CREATURE)) {
                p.sendMessage("\u00a7cThis legendary curse is already taken.");
                return;
            }
            CurseManager.remove(p);
            CurseManager.setCurse(p, CurseType.DEEP_SEA_CREATURE);
            p.sendMessage("\u00a71You have been bound to the Deep Sea Creature Curse!");
            CurseManager.setObtained(CurseType.DEEP_SEA_CREATURE);
            LegendaryAnimation.play("Deep Sea Creature", p.getName());
            this.consume(item, p);
            return;
        }
        if (item.getItemMeta().getPersistentDataContainer().has(DragonHeart.KEY, PersistentDataType.INTEGER)) {
            if (!CurseManager.isObtainable(CurseType.ENDER_DRAGON)) {
                p.sendMessage("\u00a7cThis legendary curse is already taken.");
                return;
            }
            CurseManager.remove(p);
            CurseManager.setCurse(p, CurseType.ENDER_DRAGON);
            p.sendMessage("\u00a75You obtained the Ender Dragon Curse!");
            CurseManager.setObtained(CurseType.ENDER_DRAGON);
            LegendaryAnimation.play("Ender Dragon Curse", p.getName());
            this.consume(item, p);
            return;
        }
        if (item.getItemMeta().getPersistentDataContainer().has(WitherHeart.KEY, PersistentDataType.INTEGER)) {
            if (!CurseManager.isObtainable(CurseType.WITHER)) {
                p.sendMessage("\u00a7cThis legendary curse is already taken.");
                return;
            }
            CurseManager.remove(p);
            CurseManager.setCurse(p, CurseType.WITHER);
            p.sendMessage("\u00a78You obtained the Wither Curse!");
            CurseManager.setObtained(CurseType.WITHER);
            LegendaryAnimation.play("Wither Curse", p.getName());
            this.consume(item, p);
            return;
        }
    }

    private void consume(ItemStack item, Player p) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            p.getInventory().setItemInMainHand(null);
        }
    }
}

