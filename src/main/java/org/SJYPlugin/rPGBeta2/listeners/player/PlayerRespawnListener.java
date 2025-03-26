package org.SJYPlugin.rPGBeta2.listeners.player;

import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event) {
        double MaxHP = configUtilStat2.getMaxHP(event.getPlayer());
        event.getPlayer().sendMessage("MHP " + MaxHP);
        if(MaxHP > 0) {
            configUtilStat2.HPChange(event.getPlayer(), MaxHP, (double) 0);
        } else {
            configUtilStat2.HPChange(event.getPlayer(), 20.0, (double) 0);
        }
    }

}
