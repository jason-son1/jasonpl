package org.SJYPlugin.rPGBeta2.customevents.damage;

import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RPGDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final long tiemstamp;
    private boolean cancelled;

    private DamageModifiers damageModifiers;

    public RPGDamageEvent(DamageModifiers damageModifiers) {
        this.damageModifiers = damageModifiers;
        this.tiemstamp = System.currentTimeMillis();
    }

    public LivingEntity getAttacker() {
        return damageModifiers.getAttacker();
    }

    public Player getAttackerPlayer() {
        return damageModifiers.getAttackerPlayer();
    }

    public LivingEntity getOffender() {
        return damageModifiers.getOffender();
    }

    public Player getOffenderPlayer() {
        return damageModifiers.getOffenderPlayer();
    }

    public double getDamage() {
        return damageModifiers.getFinalDamage();
    }

    public double getFinalDamage() {
        return damageModifiers.getFinalDamage();
    }

    public String getBaseType() {
        return damageModifiers.getBaseType();
    }

    public String getStemType() {
        return damageModifiers.getStemType();
    }

    public String getRootType() {
        return damageModifiers.getRootType();
    }

    public String getAttribute() {
        return damageModifiers.getAttribute();
    }

    public boolean isCritical() {
        return damageModifiers.isCritical();
    }

    public DamageModifiers getDamageModifiers() {
        return damageModifiers;
    }

    public long getTimestamp() { return tiemstamp; }

    // Setters

    public void setDamageModifiers(DamageModifiers damageModifiers) {
        this.damageModifiers = damageModifiers;
    }

    public void setFinalDamage(double damage) {
        this.damageModifiers.setFinalDamage(damage);
    }

    public void setBaseType(String baseType) {
        this.damageModifiers.setBaseType(baseType);
    }

    public void setStemType(String stemType) {
        this.damageModifiers.setStemType(stemType);
    }

    public void setRootType(String rootType) {
        this.damageModifiers.setRootType(rootType);
    }

    public void setAttribute(String attribute) {
        this.damageModifiers.setAttribute(attribute);
    }

    public void setCritical(boolean isCritical) {
        this.damageModifiers.setCritical(isCritical);
    }

    public void setTimestamp(long timestamp) {
        this.damageModifiers.setTimestamp(timestamp);
    }

    // Event



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
