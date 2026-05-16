package me.mondrilsahu.curseSMP.commands;

import me.mondrilsahu.curseSMP.gui.CurseGuideGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CurseGuideCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(!(sender instanceof Player)) return true;

        CurseGuideGUI.open((Player) sender);

        return true;
    }
}