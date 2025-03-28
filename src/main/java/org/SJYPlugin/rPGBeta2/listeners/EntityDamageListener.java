package org.SJYPlugin.rPGBeta2.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent event) {
        if(event instanceof EntityDamageByEntityEvent) {
            return;
        }
        LivingEntity offender = (LivingEntity) event.getEntity();
    }


}
