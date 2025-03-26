package org.SJYPlugin.rPGBeta2.listeners;

import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener implements Listener {

    private final ConfigUtilStat configUtilStat = ConfigUtilStat.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPlayedBefore()) {
            configUtilStat.setupFirst(event.getPlayer());
            configUtilStat.ConfigMapLoad(event.getPlayer());
        }
        else{
            configUtilStat.setupFirst(event.getPlayer());
            configUtilStat.ConfigMapLoad(event.getPlayer());
        }


    }

}
