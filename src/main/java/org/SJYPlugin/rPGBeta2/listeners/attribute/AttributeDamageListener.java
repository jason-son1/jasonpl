package org.SJYPlugin.rPGBeta2.listeners.attribute;

import org.SJYPlugin.rPGBeta2.control.attributecontrol.AttributeApply;
import org.SJYPlugin.rPGBeta2.customevents.damage.RPGDamageEvent;
import org.SJYPlugin.rPGBeta2.data.AttributeData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AttributeDamageListener implements Listener {

    AttributeData attributeData = AttributeData.getInstance();
    AttributeApply attributeApply = AttributeApply.getInstance();


    @EventHandler
    public void onAttributeDamage(RPGDamageEvent event) {
        if(!event.isCancelled()) {
            LivingEntity attacker = event.getAttacker();
            LivingEntity offender = event.getOffender();
            if(attributeData.getAttList().contains(event.getAttribute())) {
                attributeApply.applyAttributeEffect(attacker, offender, event.getAttribute(),
                        "RPGDAMAGE", 300);
            }
        }
    }

}
