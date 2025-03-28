package org.SJYPlugin.rPGBeta2.customevents.attribute;

import org.SJYPlugin.rPGBeta2.data.attribute.AttributeModifiers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AttributeApplyEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final LivingEntity offender;

    private final AttributeModifiers attributeModifiers;

    private final boolean isGaugeFull;


    public AttributeApplyEvent(LivingEntity offender, AttributeModifiers attributeModifiers, boolean isGaugeFull) {
        this.offender = offender;
        this.attributeModifiers = attributeModifiers;
        this.isGaugeFull = isGaugeFull;
    }

    public LivingEntity getOffender() {
        return offender;
    }

    public AttributeModifiers getAttributeModifiers() {
        return attributeModifiers;
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
