package org.SJYPlugin.rPGBeta2;

import org.SJYPlugin.rPGBeta2.listeners.*;

import org.SJYPlugin.rPGBeta2.listeners.damage.*;
import org.SJYPlugin.rPGBeta2.listeners.player.PlayerHPChangeListener;
import org.SJYPlugin.rPGBeta2.listeners.player.PlayerMoveListener;
import org.SJYPlugin.rPGBeta2.listeners.player.PlayerRespawnListener;
import org.SJYPlugin.rPGBeta2.mythicMobs.load.MechanicLoad;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;



public final class RPGBeta2 extends JavaPlugin {

    private static RPGBeta2 instance;

    private final ConfigUtilStat configUtilStat = ConfigUtilStat.getInstance();

    @Override
    public void onEnable() {

        instance = this;

        // Plugin startup logic
        getCommand("standspawn").setExecutor(new StandSpawn());

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamagebyEntityListener(), this);
        getServer().getPluginManager().registerEvents(new MythicMobSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new RPGDamageDisplayListener() , this);
//        getServer().getPluginManager().registerEvents(new PlayerDieListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerHPChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener() , this);

        getServer().getPluginManager().registerEvents(new MechanicLoad(), this);




        getCommand("stats").setExecutor(new StatTestCommand());



        configUtilStat.Allreload();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static RPGBeta2 getInstance() {
        return instance;
    }

}
