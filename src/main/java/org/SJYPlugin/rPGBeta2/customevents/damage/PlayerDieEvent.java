package org.SJYPlugin.rPGBeta2.customevents.damage;

import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDieEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private DamageModifiers damageModifiers;

    public PlayerDieEvent(DamageModifiers damageModifiers) {
        this.damageModifiers = damageModifiers;
    }

    public DamageModifiers getDamageModifiers() {
        return damageModifiers;
    }


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
