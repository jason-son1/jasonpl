package org.SJYPlugin.rPGBeta2.control.attributecontrol.fire;

import org.SJYPlugin.rPGBeta2.customevents.attribute.AttributeApplyEvent;
import org.SJYPlugin.rPGBeta2.data.attribute.AttDamageDeBuff;
import org.SJYPlugin.rPGBeta2.data.attribute.AttributeModifiers;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class FireAttControl implements Listener {

    private static final FireAttControl instance = new FireAttControl();

    public static FireAttControl getInstance() {
        return instance;
    }

    AttDamageDeBuff attDamageDeBuff = AttDamageDeBuff.getInstance();
    FireAttributeGauge fireAttributeGauge = FireAttributeGauge.getInstance();
    FireDotDamaging fireDotDamaging = FireDotDamaging.getInstance();

    public Set<AttributeModifiers> getFireAttributeData(LivingEntity livingEntity) {
        if(attDamageDeBuff.getBuffData(livingEntity, "FIRE") != null) {
            Set<AttributeModifiers> fireAttributeData = new HashSet<>();
            attDamageDeBuff.getBuffData(livingEntity).forEach((attributeType, data) -> {
                if(attributeType.equalsIgnoreCase("FIRE")) {
                    fireAttributeData.add(data);
                }
            });
            return fireAttributeData;
        } else {
            return null;
        }
    }

    public void PreSet(LivingEntity livingEntity) {
        Set<AttributeModifiers> fireAttributeData = getFireAttributeData(livingEntity);
        int OriginGaugeValue = fireAttributeGauge.getFireAttributeGauge(livingEntity);
        AtomicInteger GaugeValue = new AtomicInteger();
        if(fireAttributeData != null) {
            fireAttributeData.forEach(data -> GaugeValue.addAndGet(data.getGaugeValue()));
            if(OriginGaugeValue != GaugeValue.get()) {
                fireAttributeGauge.setFireAttributeGauge(livingEntity, GaugeValue.get());
            }
        } else {
            fireAttributeGauge.InitializeFireAttributeGauge(livingEntity);
        }
    }


    public void FireApply(LivingEntity offender, AttributeModifiers attributeModifiers) {
        PreSet(offender);
        fireAttributeGauge.controlFireAttributeGauge(offender, attributeModifiers);
    }


    @EventHandler
    public void FireListener(AttributeApplyEvent event) {
        if(event.getAttributeModifiers().getAttributeType().equalsIgnoreCase("FIRE")) {
            if(event.isGaugeFull()) {
                Set<AttributeModifiers> fireAttributeData = getFireAttributeData(event.getOffender());
                AttributeModifiers latestData = null;

                for (AttributeModifiers data : fireAttributeData) {
                    if (latestData == null || data.getTimestamp() > latestData.getTimestamp()) {
                        latestData = data;
                    }
                }

                if (latestData != null) {
                    if(latestData.getCauseEntity() == null) {

                    } else {
                        UUID causeEntityUUID = latestData.getCauseEntity();
                        LivingEntity causeEntity = (LivingEntity) Bukkit.getEntity(causeEntityUUID);

//                        fireDotDamaging.FireDotDamage(event.getOffender(), causeEntity,
//                                20, 5, "FIREATTRIBUTEDAMAGE", "ATTACK", 10);
                    }
                }
            } else {

            }
        }
    }












}
