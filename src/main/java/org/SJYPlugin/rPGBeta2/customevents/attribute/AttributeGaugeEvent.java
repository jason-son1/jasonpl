package org.SJYPlugin.rPGBeta2.customevents.attribute;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AttributeGaugeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final LivingEntity offender;

    private final String attributeName;

    private final int gaugeValue;

    private final boolean isGaugeFull;


    public AttributeGaugeEvent(LivingEntity offender,
                               String attributeName, int gaugeValue, boolean isGaugeFull) {
        this.offender = offender;
        this.attributeName = attributeName;
        this.gaugeValue = gaugeValue;
        this.isGaugeFull = isGaugeFull;
    }

    public LivingEntity getOffender() {
        return offender;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public int getGaugeValue() {
        return gaugeValue;
    }

    public boolean isGaugeFull() {
        return isGaugeFull;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
