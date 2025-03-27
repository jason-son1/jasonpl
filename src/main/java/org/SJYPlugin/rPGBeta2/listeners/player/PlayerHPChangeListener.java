package org.SJYPlugin.rPGBeta2.listeners.player;

import org.SJYPlugin.rPGBeta2.control.hpcontrol.HPControl;
import org.SJYPlugin.rPGBeta2.customevents.player.PlayerChangeHPEvent;
import org.SJYPlugin.rPGBeta2.util.hologram.PlayerHPHologram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHPChangeListener implements Listener {

    private final Map<UUID, Long> DisplayTime = new HashMap<>();
    private final long DISPLAY_TIME = 500;

    PlayerHPHologram playerHPHologram = PlayerHPHologram.getInstance();
    HPControl hpControl = HPControl.getInstance();

    @EventHandler
    public void DisplayHPChange(PlayerChangeHPEvent event) {
        hpControl.HealthSetPlayer(event.getPlayer());
        if(DisplayTime.containsKey(event.getPlayer().getUniqueId())) {
            long displaytime = DisplayTime.get(event.getPlayer().getUniqueId());
            if(System.currentTimeMillis() - displaytime <= DISPLAY_TIME) {
                return;
            } else {
                playerHPHologram.PlayerHPHologramSpawn(event.getPlayer(), event.getChangeValue());
            }
            DisplayTime.remove(event.getPlayer().getUniqueId());
        } else {
            playerHPHologram.PlayerHPHologramSpawn(event.getPlayer(), event.getChangeValue());
        }
    }

}
