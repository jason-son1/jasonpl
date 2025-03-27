package org.SJYPlugin.rPGBeta2.listeners.damage;

import org.SJYPlugin.rPGBeta2.customevents.damage.RPGDamageEvent;
import org.SJYPlugin.rPGBeta2.util.hologram.DamageHologram;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RPGDamageDisplayListener implements Listener {

    DamageHologram damageHologram = DamageHologram.getInstance();

    @EventHandler
    public void onRPGDamageDisplay(RPGDamageEvent event) {
        if(!event.isCancelled()) {
            if(event.getAttacker() instanceof Player) {
                if(event.getOffender() instanceof Player) {
                    if(event.isCritical()) {
                        damageHologram.DamageDisplay_Cri(event.getOffender(), event.getFinalDamage(), event.getAttacker());
                    } else {
                        damageHologram.DamageDisplay_noCri(event.getOffender(), event.getFinalDamage(), event.getAttacker());
                    }
                } else {
                    if(event.isCritical()) {
                        damageHologram.DamageDisplay_Cri(event.getOffender(), event.getFinalDamage(), event.getAttacker());
                    } else {
                        damageHologram.DamageDisplay_noCri(event.getOffender(), event.getFinalDamage(), event.getAttacker());
                    }
                }
            } else {
//                if(event.getOffender() instanceof Player) {
//                    damageHologram.DamageDisplay_noCri(event.getOffender(), event.getFinalDamage(), event.getAttacker());
//                } else {
//                    damageHologram.DamageDisplay_noCri(event.getOffender(), event.getFinalDamage(), event.getAttacker());
//                }
            }
        }
    }

}
