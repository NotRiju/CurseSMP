/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.mondrilsahu.curseSMP;

import me.mondrilsahu.curseSMP.commands.CurseCommand;
import me.mondrilsahu.curseSMP.commands.CurseGuideCommand;
import me.mondrilsahu.curseSMP.curse.CurseManager;
import me.mondrilsahu.curseSMP.curse.LevelItem;
import me.mondrilsahu.curseSMP.curse.RerollBook;
import me.mondrilsahu.curseSMP.listeners.GUIListener;
import me.mondrilsahu.curseSMP.listeners.JoinListener;
import me.mondrilsahu.curseSMP.listeners.UseListener;
import me.mondrilsahu.curseSMP.listeners.WitherKillListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CurseSMP
extends JavaPlugin {
    private static CurseSMP instance;

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        boolean enabled = this.getConfig().getBoolean("enabled");
        CurseManager.setEnabled(enabled);
        CurseManager.init();
        this.getServer().getPluginManager().registerEvents((Listener)new JoinListener(), (Plugin)this);
        RerollBook.registerRecipe();
        LevelItem.recipelevel();
        this.getServer().getPluginManager().registerEvents((Listener)new UseListener(), (Plugin)this);
        this.getCommand("curseguide").setExecutor((CommandExecutor)new CurseGuideCommand());
        this.getServer().getPluginManager().registerEvents((Listener)new GUIListener(), (Plugin)this);
        this.getCommand("curse").setExecutor((CommandExecutor)new CurseCommand());
        this.getServer().getPluginManager().registerEvents((Listener)new WitherKillListener(), (Plugin)this);
    }

    public static CurseSMP get() {
        return instance;
    }
}

