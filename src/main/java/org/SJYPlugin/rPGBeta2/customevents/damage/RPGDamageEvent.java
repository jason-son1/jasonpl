package org.SJYPlugin.rPGBeta2.customevents.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RPGDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final LivingEntity attacker;
    private final LivingEntity offender;
    private final double finalDamage;
    private final String baseType;
    private final String stemType;
    private final String rootType;
    private final String attribute;
    private final boolean isCritical;
    private final long tiemstamp;
    private boolean cancelled;

    public RPGDamageEvent(LivingEntity attacker, LivingEntity offender, double finalDamage,
                          String BaseType, String StemType, String RootType, String Attribute, boolean isCritical) {
        this.attacker = attacker;
        this.offender = offender;
        this.finalDamage = finalDamage;
        this.baseType = BaseType;
        this.stemType = StemType;
        this.rootType = RootType;
        this.attribute = Attribute;
        this.isCritical = isCritical;
        this.tiemstamp = System.currentTimeMillis();
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public LivingEntity getOffender() {
        return offender;
    }

    public double getFinalDamage() {
        return finalDamage;
    }

    public String getBaseType() {
        return baseType;
    }

    public String getStemType() {
        return stemType;
    }

    public String getRootType() {
        return rootType;
    }

    public String getAttribute() {
        return attribute;
    }

    public long getTimestamp() { return tiemstamp; }

    public boolean isCritical() { return isCritical; }


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
