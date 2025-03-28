package org.SJYPlugin.rPGBeta2.control.attributecontrol;

import org.SJYPlugin.rPGBeta2.control.attributecontrol.fire.FireAttControl;
import org.SJYPlugin.rPGBeta2.data.attribute.AttDamageDeBuff;
import org.SJYPlugin.rPGBeta2.data.attribute.AttributeModifiers;
import org.bukkit.entity.LivingEntity;

public class AttributeApply {

    private static final AttributeApply instance = new AttributeApply();

    public static AttributeApply getInstance() {
        return instance;
    }

    AttDamageDeBuff attDamageDeBuff = AttDamageDeBuff.getInstance();
    FireAttControl fireAttControl = FireAttControl.getInstance();

    public void applyAttributeEffect(LivingEntity offender, AttributeModifiers attributeModifiers) {
        long timestamp = System.currentTimeMillis();
        attributeModifiers.setTimestamp(timestamp);
        attDamageDeBuff.cleanupOldData(offender);
        attDamageDeBuff.setBuffData(offender, attributeModifiers.getAttributeType(), attributeModifiers);
        // Implement specific effects based on the attribute
        switch (attributeModifiers.getAttributeType().toUpperCase()) {
            case "FIRE":
                fireAttControl.FireApply(offender, attributeModifiers);
                break;
            case "WATER":
                // Apply water effect
                break;
            // Add more cases for other attributes
        }
    }

    public AttributeModifiers getAttributeData(LivingEntity livingEntity, String attributeType, int attributeValue,
                                               LivingEntity causeEntity, String cause, int gaugeValue) {
        return new AttributeModifiers(attributeType, 0, causeEntity, cause, gaugeValue, attributeValue);
    }



}
