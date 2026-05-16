package me.mondrilsahu.curseSMP;

import me.mondrilsahu.curseSMP.commands.*;
import me.mondrilsahu.curseSMP.curse.*;
import me.mondrilsahu.curseSMP.listeners.*;

import org.bukkit.plugin.java.JavaPlugin;

public class CurseSMP extends JavaPlugin {

    private static CurseSMP instance;

    @Override
    public void onEnable(){

        instance = this;

        saveDefaultConfig();

        CurseManager.init();

        CurseManager.setEnabled(
                getConfig().getBoolean("enabled")
        );

        getCommand("curse").setExecutor(new CurseCommand());
        getCommand("curseguide").setExecutor(new CurseGuideCommand());

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new UseListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new KillListener(), this);
        getServer().getPluginManager().registerEvents(new WitherKillListener(), this);

        RerollBook.registerRecipe();
    }

    public static CurseSMP get(){
        return instance;
    }
}