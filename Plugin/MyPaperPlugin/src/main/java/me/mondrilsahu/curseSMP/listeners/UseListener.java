package me.mondrilsahu.curseSMP.listeners;

import me.mondrilsahu.curseSMP.curse.*;
import me.mondrilsahu.curseSMP.utils.BeamEffect;
import me.mondrilsahu.curseSMP.utils.CircleParticle;
import me.mondrilsahu.curseSMP.utils.LegendaryAnimation;
import me.mondrilsahu.curseSMP.utils.RollAnimation;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class UseListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e){

        if(e.getAction() != Action.RIGHT_CLICK_AIR &&
                e.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        Player p = e.getPlayer();

        ItemStack item = e.getItem();

        if(item == null || !item.hasItemMeta()){
            return;
        }

        // ================= REROLL BOOK =================

        if(item.getItemMeta().getPersistentDataContainer()
                .has(RerollBook.KEY, PersistentDataType.INTEGER)){

            if(!CurseManager.has(p)){
                p.sendMessage("§cYou don't have a curse.");
                return;
            }

            // legendary lock
            if(CurseManager.isLocked(CurseManager.getType(p))){
                p.sendMessage("§cYour curse cannot be rerolled.");
                return;
            }

            RollAnimation.play(p, () -> {

                CurseType old = CurseManager.getType(p);

                CurseType newType = CurseManager.reroll(p);

                BeamEffect.play(
                        p,
                        CurseManager.getColor(newType)
                );

                CircleParticle.play(
                        p,
                        CurseManager.getColor(newType)
                );

                p.sendMessage(
                        "§aRerolled from §e"
                                + old +
                                " §ato §e"
                                + newType
                );
            });

            consume(item, p);

            return;
        }

        // ================= LEVEL ITEM =================

        if(item.getItemMeta().getPersistentDataContainer()
                .has(LevelItem.KEY, PersistentDataType.INTEGER)){

            if(!CurseManager.has(p)){
                p.sendMessage("§cYou don't have a curse.");
                return;
            }

            int level = CurseManager.getLevel(p);

            if(level >= 3){
                p.sendMessage("§cYou are already max level.");
                return;
            }

            CurseManager.setLevel(p, level + 1);

            p.sendMessage(
                    "§aYour curse level increased to §e"
                            + (level + 1)
            );

            p.playSound(
                    p.getLocation(),
                    Sound.ENTITY_PLAYER_LEVELUP,
                    1f,
                    1f
            );

            consume(item, p);

            return;
        }

        // ================= DEEP SEA HEART =================

        if(item.getItemMeta().getPersistentDataContainer()
                .has(DeepSeaHeart.KEY, PersistentDataType.INTEGER)){

            if(!CurseManager.isObtainable(
                    CurseType.DEEP_SEA_CREATURE
            )){
                p.sendMessage(
                        "§cThis legendary curse is already taken."
                );
                return;
            }

            CurseManager.setCurse(
                    p,
                    CurseType.DEEP_SEA_CREATURE
            );

            CurseManager.setObtained(
                    CurseType.DEEP_SEA_CREATURE
            );

            LegendaryAnimation.play(
                    "Deep Sea Curse",
                    p.getName()
            );

            BeamEffect.play(
                    p,
                    CurseManager.getColor(
                            CurseType.DEEP_SEA_CREATURE
                    )
            );

            CircleParticle.play(
                    p,
                    CurseManager.getColor(
                            CurseType.DEEP_SEA_CREATURE
                    )
            );

            consume(item, p);

            return;
        }

        // ================= DRAGON HEART =================

        if(item.getItemMeta().getPersistentDataContainer()
                .has(DragonHeart.KEY, PersistentDataType.INTEGER)){

            if(!CurseManager.isObtainable(
                    CurseType.ENDER_DRAGON
            )){
                p.sendMessage(
                        "§cThis legendary curse is already taken."
                );
                return;
            }

            CurseManager.setCurse(
                    p,
                    CurseType.ENDER_DRAGON
            );

            CurseManager.setObtained(
                    CurseType.ENDER_DRAGON
            );

            LegendaryAnimation.play(
                    "Ender Dragon Curse",
                    p.getName()
            );

            BeamEffect.play(
                    p,
                    CurseManager.getColor(
                            CurseType.ENDER_DRAGON
                    )
            );

            CircleParticle.play(
                    p,
                    CurseManager.getColor(
                            CurseType.ENDER_DRAGON
                    )
            );

            consume(item, p);

            return;
        }

        // ================= WITHER HEART =================

        if(item.getItemMeta().getPersistentDataContainer()
                .has(WitherHeart.KEY, PersistentDataType.INTEGER)){

            if(!CurseManager.isObtainable(
                    CurseType.WITHER
            )){
                p.sendMessage(
                        "§cThis legendary curse is already taken."
                );
                return;
            }

            CurseManager.setCurse(
                    p,
                    CurseType.WITHER
            );

            CurseManager.setObtained(
                    CurseType.WITHER
            );

            LegendaryAnimation.play(
                    "Wither Curse",
                    p.getName()
            );

            BeamEffect.play(
                    p,
                    CurseManager.getColor(
                            CurseType.WITHER
                    )
            );

            CircleParticle.play(
                    p,
                    CurseManager.getColor(
                            CurseType.WITHER
                    )
            );

            consume(item, p);

            return;
        }
    }

    // ================= ITEM CONSUME =================

    private void consume(ItemStack item, Player p){

        item.setAmount(item.getAmount() - 1);

        p.updateInventory();
    }
}