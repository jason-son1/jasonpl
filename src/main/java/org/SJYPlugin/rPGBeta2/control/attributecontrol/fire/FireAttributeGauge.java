package org.SJYPlugin.rPGBeta2.control.attributecontrol.fire;

import org.SJYPlugin.rPGBeta2.customevents.attribute.AttributeGaugeEvent;
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
        if(!fireAttributeGauge.containsKey(livingEntity.getUniqueId()) ||
         fireAttributeGauge == null || fireAttributeGauge.get(livingEntity.getUniqueId()) == null) {
            fireAttributeGauge.put(livingEntity.getUniqueId(), 0);
        }
    }

    public int getFireAttributeGauge(LivingEntity livingEntity) {
        return fireAttributeGauge.getOrDefault(livingEntity.getUniqueId(), 0);
    }

    public void setFireAttributeGauge(LivingEntity livingEntity, int value) {
        InitializeFireAttributeGauge(livingEntity);
        if(value < 1000) {
            fireAttributeGauge.put(livingEntity.getUniqueId(), value);
        } else {
            AttributeGaugeEvent attributeGaugeEvent = new AttributeGaugeEvent(livingEntity, "FIRE", value, true);
            attributeGaugeEvent.callEvent();
            fireAttributeGauge.put(livingEntity.getUniqueId(), 0);
        }
    }

    public void ControlFireAttributeGauge(LivingEntity livingEntity, int changevalue) {
        InitializeFireAttributeGauge(livingEntity);
        int PreValue = fireAttributeGauge.get(livingEntity.getUniqueId());
        setFireAttributeGauge(livingEntity, PreValue + changevalue);
    }





}
