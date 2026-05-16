/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.TabCompleter
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.mondrilsahu.curseSMP.commands;

import java.util.ArrayList;
import java.util.List;
import me.mondrilsahu.curseSMP.curse.CurseManager;
import me.mondrilsahu.curseSMP.curse.CurseType;
import me.mondrilsahu.curseSMP.curse.DeepSeaHeart;
import me.mondrilsahu.curseSMP.curse.DragonHeart;
import me.mondrilsahu.curseSMP.curse.LevelItem;
import me.mondrilsahu.curseSMP.curse.RerollBook;
import me.mondrilsahu.curseSMP.curse.WitherHeart;
import me.mondrilsahu.curseSMP.utils.BeamEffect;
import me.mondrilsahu.curseSMP.utils.CircleParticle;
import me.mondrilsahu.curseSMP.utils.LegendaryAnimation;
import me.mondrilsahu.curseSMP.utils.RollAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CurseCommand
implements CommandExecutor,
TabCompleter {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String sub;
        if (args.length == 0) {
            sender.sendMessage("\u00a7cUsage: /curse <reroll|level|remove|set|ability|on|off>");
            return true;
        }
        switch (sub = args[0].toLowerCase()) {
            case "reroll": {
                if (!sender.hasPermission("curse.admin")) {
                    sender.sendMessage("\u00a7cNo permission");
                    return true;
                }
                Player target = this.getPlayer(sender, args, 1);
                if (target == null) {
                    return true;
                }
                CurseType old = CurseManager.getType(target);
                Color color = CurseManager.getColor(old);
                if (CurseManager.isLocked(CurseManager.getType(target))) {
                    sender.sendMessage("\u00a7cThis curse cannot be rerolled.");
                    return true;
                }
                CircleParticle.play(target, color);
                BeamEffect.play(target, color);
                RollAnimation.play(target, () -> {
                    CurseType newCurse = CurseManager.reroll(target);
                    target.sendTitle("\u00a75NEW CURSE", "\u00a7d" + this.format(newCurse), 10, 60, 10);
                });
                sender.sendMessage("\u00a7aRerolled curse for " + target.getName());
                break;
            }
            case "level": {
                String action;
                String string = action = args.length >= 2 ? args[1].toLowerCase() : "info";
                if (action.equals("info")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("\u00a7cPlayers only");
                        return true;
                    }
                    Player p = (Player)sender;
                    sender.sendMessage("\u00a75Your Curse Level: \u00a7e" + CurseManager.getLevel(p));
                    break;
                }
                if (action.equals("set")) {
                    int level;
                    if (!sender.hasPermission("curse.admin")) {
                        sender.sendMessage("\u00a7cNo permission");
                        return true;
                    }
                    if (args.length < 4) {
                        sender.sendMessage("\u00a7cUsage: /curse level set <player> <1-3>");
                        return true;
                    }
                    Player target = this.getPlayer(sender, args, 2);
                    if (target == null) {
                        return true;
                    }
                    try {
                        level = Integer.parseInt(args[3]);
                    }
                    catch (NumberFormatException e) {
                        sender.sendMessage("\u00a7cInvalid level (1-3)");
                        return true;
                    }
                    CurseManager.setLevel(target, level);
                    sender.sendMessage("\u00a7aSet level of " + target.getName() + " to " + level);
                    break;
                }
                sender.sendMessage("\u00a7cUsage: /curse level <info/set>");
                break;
            }
            case "remove": {
                if (!sender.hasPermission("curse.admin")) {
                    sender.sendMessage("\u00a7cNo permission");
                    return true;
                }
                Player target = this.getPlayer(sender, args, 1);
                if (target == null) {
                    return true;
                }
                CurseManager.remove(target);
                sender.sendMessage("\u00a7aRemoved curse from " + target.getName());
                target.sendMessage("\u00a7cYour curse has been removed.");
                CurseType old = CurseManager.getType(target);
                if (!CurseManager.isLocked(old)) break;
                LegendaryAnimation.playRemove(CurseManager.format(old), target.getName());
                CurseManager.setAvailable(old);
                break;
            }
            case "set": {
                if (!sender.hasPermission("curse.admin")) {
                    sender.sendMessage("\u00a7cNo permission");
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage("\u00a7cUsage: /curse set <player> <type>");
                    return true;
                }
                Player target = this.getPlayer(sender, args, 1);
                if (target == null) {
                    return true;
                }
                try {
                    CurseType type = CurseType.valueOf(args[2].toUpperCase());
                    CurseManager.setCurse(target, type);
                    if (CurseManager.isLocked(type) && !CurseManager.isObtainable(type)) {
                        sender.sendMessage("\u00a7cThis legendary curse is already taken.");
                        return true;
                    }
                    sender.sendMessage("\u00a7aSet curse of " + target.getName() + " to " + String.valueOf((Object)type));
                    CurseManager.setObtained(type);
                }
                catch (IllegalArgumentException e) {
                    sender.sendMessage("\u00a7cInvalid curse type");
                }
                break;
            }
            case "ability": {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Players only");
                    return true;
                }
                Player p = (Player)sender;
                if (!CurseManager.isEnabled()) {
                    p.sendMessage("\u00a7cSystem disabled.");
                    return true;
                }
                if (!CurseManager.has(p)) {
                    p.sendMessage("\u00a7cNo curse.");
                    return true;
                }
                CurseType type = CurseManager.getType(p);
                int level = CurseManager.getLevel(p);
                p.sendMessage("\u00a75Your Curse: \u00a7d" + this.format(type));
                p.sendMessage("\u00a77Level: \u00a7e" + level);
                p.sendMessage("\u00a75--- Passive Effects ---");
                p.sendMessage(this.getPassiveDescription(type, level));
                p.sendMessage("\u00a75--- Active Ability ---");
                p.sendMessage(this.getActiveDescription(type, level));
                p.sendMessage("\u00a7dTrigger: \u00a7fEmpty Hand Right-Click or Shift + Left-Click");
                return true;
            }
            case "on": {
                if (!sender.hasPermission("curse.admin")) {
                    sender.sendMessage("\u00a7cNo permission");
                    return true;
                }
                if (CurseManager.isEnabled()) {
                    sender.sendMessage("\u00a7eAlready enabled.");
                    return true;
                }
                CurseManager.setEnabled(true);
                Bukkit.broadcastMessage((String)"\u00a75\u00a7lCurse SMP begins...");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.kickPlayer("\u00a75Curse SMP begins...\n\u00a77Rejoin to receive your curse.");
                }
                sender.sendMessage("\u00a7aEnabled.");
                break;
            }
            case "off": {
                if (!sender.hasPermission("curse.admin")) {
                    sender.sendMessage("\u00a7cNo permission");
                    return true;
                }
                if (!CurseManager.isEnabled()) {
                    sender.sendMessage("\u00a7eAlready disabled.");
                    return true;
                }
                CurseManager.setEnabled(false);
                Bukkit.broadcastMessage((String)"\u00a7cCurse SMP disabled.");
                sender.sendMessage("\u00a7cDisabled.");
                break;
            }
            case "give": {
                ItemStack item;
                Player target;
                if (!sender.hasPermission("curse.admin")) {
                    sender.sendMessage("\u00a7cNo permission");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage("\u00a7cUsage: /curse give <reroll|level> [player]");
                    return true;
                }
                String itemName = args[1].toLowerCase();
                if (args.length >= 3) {
                    target = Bukkit.getPlayer((String)args[2]);
                    if (target == null) {
                        sender.sendMessage("\u00a7cPlayer not found.");
                        return true;
                    }
                } else {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("\u00a7cConsole must specify player.");
                        return true;
                    }
                    target = (Player)sender;
                }
                switch (itemName) {
                    case "reroll": {
                        item = RerollBook.create();
                        break;
                    }
                    case "level": {
                        item = LevelItem.create();
                        break;
                    }
                    case "heart": {
                        item = DeepSeaHeart.create();
                        break;
                    }
                    case "dragon": {
                        item = DragonHeart.create();
                        break;
                    }
                    case "wither": {
                        item = WitherHeart.create();
                        break;
                    }
                    default: {
                        sender.sendMessage("\u00a7cInvalid item.");
                        return true;
                    }
                }
                target.getInventory().addItem(new ItemStack[]{item});
                sender.sendMessage("\u00a7aGave " + itemName + " to " + target.getName());
                return true;
            }
            default: {
                sender.sendMessage("\u00a7cUnknown subcommand");
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        if (args.length == 1) {
            list.add("reroll");
            list.add("level");
            list.add("remove");
            list.add("set");
            list.add("ability");
            list.add("on");
            list.add("off");
            list.add("give");
            return list;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            list.add("reroll");
            list.add("level");
            list.add("heart");
            list.add("dragon");
            list.add("wither");
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                list.add(p.getName());
            }
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("level")) {
            list.add("info");
            list.add("set");
            return list;
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("reroll"))) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                list.add(p.getName());
            }
            return list;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            for (CurseType type : CurseType.values()) {
                list.add(type.name());
            }
            return list;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("level") && args[1].equalsIgnoreCase("set")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                list.add(p.getName());
            }
            return list;
        }
        if (args.length == 4 && args[0].equalsIgnoreCase("level") && args[1].equalsIgnoreCase("set")) {
            list.add("1");
            list.add("2");
            list.add("3");
            return list;
        }
        return list;
    }

    private Player getPlayer(CommandSender sender, String[] args, int index) {
        if (args.length <= index) {
            sender.sendMessage("\u00a7cPlease specify a player");
            return null;
        }
        Player target = Bukkit.getPlayer((String)args[index]);
        if (target == null) {
            sender.sendMessage("\u00a7cPlayer not found");
            return null;
        }
        return target;
    }

    private String format(CurseType c) {
        return c.name().charAt(0) + c.name().substring(1).toLowerCase();
    }

    private String getPassiveDescription(CurseType type, int level) {
        switch (type) {
            case FIRE: return "\u00a76Fire Immunity\u00a7f: You are immune to all fire and lava damage.";
            case VOID: return "\u00a75End Aura\u00a7f: Surrounded by particle trails of the End.";
            case STORM: return "\u00a7bSwift Torrent\u00a7f: Passive Speed " + (level == 1 ? "I" : level == 2 ? "II" : "III") + " while in rain or water.";
            case SHADOW: return "\u00a78Vanish\u00a7f: Passive invisibility during the night.";
            case BLOOD: return "\u00a7cRage & Lifesteal\u00a7f: Heal on hit (" + (8 * level) + "% damage). Gains Strength and Speed when below 4 hearts.";
            case FREEZE: return "\u00a7bFrost aura\u00a7f: Surrounded by a freezing frost field.";
            case WIND: return "\u00a7aDouble Jump\u00a7f: Double-tap spacebar to glide and jump in mid-air.";
            case DEMON: return "\u00a7dDemonic Strength\u00a7f: Permanent Strength " + (level >= 3 ? "II" : "I") + " boost.";
            case NATURE: return "\u00a72Regeneration\u00a7f: Passive health regeneration (" + (0.2 * level) + " HP/sec).";
            case DEATH: return "\u00a77Undying Soul\u00a7f: Defy death on fatal damage, instantly healing to full. Cooldown: " + (7 - level) + "m.";
            case DEEP_SEA_CREATURE: return "\u00a71Abyssal Swim\u00a7f: Permanent Water Breathing and Dolphin's Grace swimming speed boost.";
            case ENDER_DRAGON: return "\u00a75Dragon Flight\u00a7f: Absolute immunity to all fall damage.";
            case WITHER: return "\u00a78Decay Immunity\u00a7f: Absolute immunity to the Wither decay effect.";
            default: return "\u00a77No passive effects.";
        }
    }

    private String getActiveDescription(CurseType type, int level) {
        switch (type) {
            case FIRE: return "\u00a76Fire Burst\u00a7f: Unleash a ring of flames, damaging and burning nearby targets.";
            case VOID: return "\u00a75Void Teleport\u00a7f: Teleport " + (6 * level) + " blocks forward in your looking direction.";
            case STORM: return "\u00a7bThunderbolt\u00a7f: Strike lightning at the targeted block or entity.";
            case SHADOW: return "\u00a78Shadow Step\u00a7f: Teleport behind the nearest entity, gaining a Strength boost.";
            case BLOOD: return "\u00a7cBlood Burst\u00a7f: Drain " + (2.0 + 1.5 * level) + " HP from all surrounding entities to heal yourself.";
            case FREEZE: return "\u00a73Glacial Prison\u00a7f: Encase all nearby entities in physical ice blocks for " + (2 * level + 2) + " seconds.";
            case WIND: return "\u00a7aWind Dash\u00a7f: Launch yourself rapidly forward in your looking direction.";
            case DEMON: return "\u00a7dDemon Rage\u00a7f: Enter a demon rage for " + (5 + 2 * level) + " seconds, gaining Strength, Speed, and Resistance.";
            case NATURE: return "\u00a72Entangling Vines\u00a7f: Trap nearby enemies with Slowness and Poison.";
            case DEATH: return "\u00a77Wither Skull\u00a7f: Fire an explosive Wither skull that inflicts the Wither status.";
            case DEEP_SEA_CREATURE: return "\u00a71Sonic Boom\u00a7f: Fire a heavy Warden sonic boom, gaining Strength III & Resistance IV for 10s.";
            case ENDER_DRAGON: return "\u00a75Dragon's Breath\u00a7f: Emit a toxic cone of purple breath while pulling nearby entities to you.";
            case WITHER: return "\u00a78Wither Barrage\u00a7f: Launch a spread of 3 Wither skulls and wither surrounding targets.";
            default: return "\u00a77No active abilities.";
        }
    }
}

