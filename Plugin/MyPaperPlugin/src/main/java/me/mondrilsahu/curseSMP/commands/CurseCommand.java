package me.mondrilsahu.curseSMP.commands;

import me.mondrilsahu.curseSMP.CurseSMP;
import me.mondrilsahu.curseSMP.curse.*;
import me.mondrilsahu.curseSMP.utils.LegendaryAnimation;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CurseCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(args.length == 0){
            sender.sendMessage("§cUsage: /curse <subcommand>");
            return true;
        }

        switch (args[0].toLowerCase()){

            case "on": {

                CurseManager.setEnabled(true);

                CurseSMP.get().getConfig().set("enabled", true);
                CurseSMP.get().saveConfig();

                Bukkit.broadcastMessage("§5§lCurse SMP begins...");

                for(Player p : Bukkit.getOnlinePlayers()){
                    p.kickPlayer("§5Curse SMP begins...\n§7Rejoin to receive your curse.");
                }

                return true;
            }

            case "off": {

                CurseManager.setEnabled(false);

                CurseSMP.get().getConfig().set("enabled", false);
                CurseSMP.get().saveConfig();

                Bukkit.broadcastMessage("§cCurse SMP disabled.");

                return true;
            }

            case "remove": {

                if(args.length < 2) return true;

                Player target = Bukkit.getPlayer(args[1]);
                if(target == null) return true;

                CurseType old = CurseManager.getType(target);

                if(old != null && CurseManager.isLocked(old)){

                    LegendaryAnimation.playRemove(
                            CurseManager.format(old),
                            target.getName()
                    );

                    CurseManager.setAvailable(old);
                }

                CurseManager.remove(target);

                sender.sendMessage("§aRemoved curse from " + target.getName());
                return true;
            }

            case "give": {

                if(!(sender instanceof Player) && args.length < 3) return true;

                String type = args[1].toLowerCase();

                Player target;

                if(args.length >= 3){
                    target = Bukkit.getPlayer(args[2]);
                } else {
                    target = (Player) sender;
                }

                if(target == null) return true;

                ItemStack item;

                switch (type){

                    case "reroll":
                        item = RerollBook.create();
                        break;

                    case "level":
                        item = LevelItem.create();
                        break;

                    case "heart":
                        item = DeepSeaHeart.create();
                        break;

                    case "dragon":
                        item = DragonHeart.create();
                        break;

                    case "wither":
                        item = WitherHeart.create();
                        break;

                    default:
                        sender.sendMessage("§cInvalid item.");
                        return true;
                }

                target.getInventory().addItem(item);

                sender.sendMessage("§aGiven item.");

                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){

        List<String> list = new ArrayList<>();

        if(args.length == 1){

            list.add("on");
            list.add("off");
            list.add("give");
            list.add("remove");
        }

        return list;
    }
}