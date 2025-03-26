package org.SJYPlugin.rPGBeta2.control.attributecontrol;

import org.SJYPlugin.rPGBeta2.control.attributecontrol.fire.FireAttControl;
import org.SJYPlugin.rPGBeta2.data.generaldata.debuffdata.AttDamageDeBuff;
import org.bukkit.entity.LivingEntity;

public class AttributeApply {

    private static final AttributeApply instance = new AttributeApply();

    public static AttributeApply getInstance() {
        return instance;
    }

    AttDamageDeBuff attDamageDeBuff = AttDamageDeBuff.getInstance();
    FireAttControl fireAttControl = FireAttControl.getInstance();

    public void applyAttributeEffect(LivingEntity attacker, LivingEntity offender,
                                     String attributeName, String cause, int GaugeValue) {
        long timestamp = System.currentTimeMillis();
        attDamageDeBuff.cleanupOldData(offender);
        attDamageDeBuff.setBuffData(offender, attributeName, timestamp, attacker, cause, GaugeValue);
        // Implement specific effects based on the attribute
        switch (attributeName.toUpperCase()) {
            case "FIRE":
                fireAttControl.FireApply(offender, attacker, timestamp, cause, GaugeValue);
                break;
            case "WATER":
                // Apply water effect
                break;
            // Add more cases for other attributes
        }
    }



}
