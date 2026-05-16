package me.mondrilsahu.curseSMP;

import me.mondrilsahu.curseSMP.abilities.AbilityManager;
import me.mondrilsahu.curseSMP.commands.CurseCommand;
import me.mondrilsahu.curseSMP.commands.CurseGuideCommand;
import me.mondrilsahu.curseSMP.curse.CurseManager;
import me.mondrilsahu.curseSMP.curse.LevelItem;
import me.mondrilsahu.curseSMP.curse.RerollBook;
import me.mondrilsahu.curseSMP.listeners.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CurseSMP extends JavaPlugin {
    private static CurseSMP instance;

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        boolean enabled = this.getConfig().getBoolean("enabled");
        CurseManager.setEnabled(enabled);
        CurseManager.init();
        
        // Register core event listeners
        this.getServer().getPluginManager().registerEvents((Listener)new JoinListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new UseListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new GUIListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new WitherKillListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new KillListener(), (Plugin)this); // REGISTERED BUG FIX
        
        // Register Ability Manager Listener and start loops
        this.getServer().getPluginManager().registerEvents(new AbilityManager(), this);
        AbilityManager.startTasks();

        // Custom Recipes
        RerollBook.registerRecipe();
        LevelItem.recipelevel();

        // Command executors
        this.getCommand("curseguide").setExecutor((CommandExecutor)new CurseGuideCommand());
        this.getCommand("curse").setExecutor((CommandExecutor)new CurseCommand());
    }

    public static CurseSMP get() {
        return instance;
    }
}
