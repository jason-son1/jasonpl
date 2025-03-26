package org.SJYPlugin.rPGBeta2.customevents.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChangeHPEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final double ChangeValue;
    private final double PreValue;
    private final String Cause;
    private final double MaxHealth;

    public PlayerChangeHPEvent(Player player, double PreValue ,double ChangeValue, double MaxHealth, String Cause) {
        this.player = player;
        this.ChangeValue = ChangeValue;
        this.PreValue = PreValue;
        this.Cause = Cause;
        this.MaxHealth = MaxHealth;
    }

    public Player getPlayer() {return player;}

    public double getChangeValue() {return ChangeValue;}

    public double getPreValue() {return PreValue;}

    public String getCause() {return Cause;}

    public double getMaxHealth() {return MaxHealth;}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }




}
