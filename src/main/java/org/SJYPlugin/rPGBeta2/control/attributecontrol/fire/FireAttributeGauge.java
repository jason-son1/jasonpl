package org.SJYPlugin.rPGBeta2.control.attributecontrol.fire;

import org.SJYPlugin.rPGBeta2.customevents.attribute.AttributeApplyEvent;
import org.SJYPlugin.rPGBeta2.data.attribute.AttributeModifiers;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FireAttributeGauge {

    private static final FireAttributeGauge instance = new FireAttributeGauge();

    public static FireAttributeGauge getInstance() {
        return instance;
    }

    private final Map<UUID, Integer> fireAttributeGauge = new HashMap<>();

    public void InitializeFireAttributeGauge(LivingEntity livingEntity) {
        if(!fireAttributeGauge.containsKey(livingEntity.getUniqueId()) || fireAttributeGauge.get(livingEntity.getUniqueId()) == null) {
            fireAttributeGauge.put(livingEntity.getUniqueId(), 0);
        }
    }

    public int getFireAttributeGauge(LivingEntity livingEntity) {
        return fireAttributeGauge.getOrDefault(livingEntity.getUniqueId(), 0);
    }

    public void setFireAttributeGauge(LivingEntity livingEntity, int value) {
        fireAttributeGauge.put(livingEntity.getUniqueId(), value);
    }

    public void controlFireAttributeGauge(LivingEntity livingEntity, AttributeModifiers attributeModifiers) {
        InitializeFireAttributeGauge(livingEntity);
        int PreValue = fireAttributeGauge.get(livingEntity.getUniqueId());
        if(PreValue + attributeModifiers.getGaugeValue() < 1000) {
            AttributeApplyEvent attributeApplyEvent = new AttributeApplyEvent(livingEntity, attributeModifiers, false);
            attributeApplyEvent.callEvent();
            fireAttributeGauge.put(livingEntity.getUniqueId(), PreValue + attributeModifiers.getGaugeValue());
        } else {
            AttributeApplyEvent attributeApplyEvent = new AttributeApplyEvent(livingEntity, attributeModifiers, true);
            attributeApplyEvent.callEvent();
            fireAttributeGauge.put(livingEntity.getUniqueId(), 0);
        }
    }





}
