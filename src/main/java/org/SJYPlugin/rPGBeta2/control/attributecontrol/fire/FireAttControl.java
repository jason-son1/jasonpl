package org.SJYPlugin.rPGBeta2.control.attributecontrol.fire;

import org.SJYPlugin.rPGBeta2.customevents.attribute.AttributeGaugeEvent;
import org.SJYPlugin.rPGBeta2.data.generaldata.debuffdata.AttDamageDeBuff;
import org.SJYPlugin.rPGBeta2.data.generaldata.debuffdata.AttributtAccessData;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.rmi.server.UID;
import java.util.Set;
import java.util.UUID;

public class FireAttControl implements Listener {

    private static final FireAttControl instance = new FireAttControl();

    public static FireAttControl getInstance() {
        return instance;
    }

    AttDamageDeBuff attDamageDeBuff = AttDamageDeBuff.getInstance();
    FireAttributeGauge fireAttributeGauge = FireAttributeGauge.getInstance();
    FireDotDamaging fireDotDamaging = FireDotDamaging.getInstance();

    public Set<AttributtAccessData> getFireAttributeData(LivingEntity livingEntity) {
        if(attDamageDeBuff.getBuffData(livingEntity, "FIRE") != null) {
            Set<AttributtAccessData> fireAttributeData = Set.of();
            attDamageDeBuff.getBuffData(livingEntity).forEach((attributeName, data) -> {
                if(attributeName.equalsIgnoreCase("FIRE")) {
                    fireAttributeData.add(data);
                }
            });
            return fireAttributeData;
        } else {
            return null;
        }
    }


    public void FireApply(LivingEntity offender, LivingEntity attacker, long timestamp ,String cause, int GaugeValue) {
        Set<AttributtAccessData> fireAttributeData = getFireAttributeData(offender);
        final int[] preGuageValue = {0};
        if(fireAttributeData != null) {
            fireAttributeData.forEach(data -> {
                preGuageValue[0] += data.getGaugeValue();
            });
            attDamageDeBuff.setBuffData(offender, "FIRE", timestamp, attacker, cause, GaugeValue);
            fireAttributeGauge.setFireAttributeGauge(offender, preGuageValue[0] + GaugeValue);
        } else {
            attDamageDeBuff.setBuffData(offender, "FIRE", timestamp, attacker, cause, GaugeValue);
            fireAttributeGauge.setFireAttributeGauge(offender, GaugeValue);
        }
    }


    @EventHandler
    public void FireListener(AttributeGaugeEvent event) {
        if(event.getAttributeName().equalsIgnoreCase("FIRE")) {
            if(event.isGaugeFull()) {
                Set<AttributtAccessData> fireAttributeData = getFireAttributeData(event.getOffender());
                AttributtAccessData latestData = null;

                for (AttributtAccessData data : fireAttributeData) {
                    if (latestData == null || data.getTimestamp() > latestData.getTimestamp()) {
                        latestData = data;
                    }
                }

                if (latestData != null) {
                    if(latestData.getCauseEntity() == null) {

                    } else {
                        UUID causeEntityUUID = latestData.getCauseEntity();
                        LivingEntity causeEntity = (LivingEntity) Bukkit.getEntity(causeEntityUUID);

                        fireDotDamaging.FireDotDamage(event.getOffender(), causeEntity,
                                20, 5, "FIREATTRIBUTEDAMAGE", "ATTACK", 10);
                    }
                }
            } else {

            }
        }
    }












}
