package org.SJYPlugin.rPGBeta2.listeners.player;

import org.SJYPlugin.rPGBeta2.control.hpcontrol.HPControl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void PlayerMoving(PlayerMoveEvent event) {
//        HPControl.getInstance().HealthSetPlayer(event.getPlayer());
        event.getPlayer().setFoodLevel(15);
    }

}
