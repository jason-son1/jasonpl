package org.SJYPlugin.rPGBeta2.listeners.attribute;

import org.SJYPlugin.rPGBeta2.control.attributecontrol.AttributeApply;
import org.SJYPlugin.rPGBeta2.customevents.damage.RPGDamageEvent;
import org.SJYPlugin.rPGBeta2.data.attribute.AttributeData;
import org.SJYPlugin.rPGBeta2.data.attribute.AttributeModifiers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AttributeDamageListener implements Listener {

    AttributeData attributeData = AttributeData.getInstance();
    AttributeApply attributeApply = AttributeApply.getInstance();


    @EventHandler
    public void testonAttributeDamage(RPGDamageEvent event) {
        if(!event.isCancelled()) {
            LivingEntity attacker = event.getAttacker();
            LivingEntity offender = event.getOffender();
            if(event.getAttribute() == null || event.getAttribute().isEmpty()) {
                return;
            }
            if(attributeData.getAttList().contains(event.getAttribute())) {
                AttributeModifiers attributeModifiers = attributeApply.getAttributeData(offender,
                        event.getAttribute(), 0, attacker, null, 300);
                attributeApply.applyAttributeEffect(offender, attributeModifiers);
            }
        }
    }

}
