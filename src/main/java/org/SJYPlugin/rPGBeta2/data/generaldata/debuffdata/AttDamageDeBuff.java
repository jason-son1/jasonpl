package org.SJYPlugin.rPGBeta2.data.generaldata.debuffdata;

import org.SJYPlugin.rPGBeta2.data.AttributeData;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AttDamageDeBuff {

    private static final AttDamageDeBuff instance = new AttDamageDeBuff();

    public static AttDamageDeBuff getInstance() {
        return instance;
    }

    private static final long Expiration_time = 1000 * 30;

    private final Map<UUID, Map<String, AttributtAccessData>> attDamageDeBuffData = new HashMap<>();

    public void setBuffData(LivingEntity livingEntity, String attributeName, long timestamp,
                            LivingEntity causeEntity, String cause, int GaugeValue) {
        cleanupOldData(livingEntity);
        attDamageDeBuffData.computeIfAbsent(livingEntity.getUniqueId(), k -> new HashMap<>())
                .put(attributeName, new AttributtAccessData(attributeName, timestamp, causeEntity, cause , GaugeValue));
    }

    public AttributtAccessData getBuffData(LivingEntity livingEntity, String attributeName) {
        return attDamageDeBuffData.getOrDefault(livingEntity.getUniqueId(), new HashMap<>()).get(attributeName);
    }

    public Map<String, AttributtAccessData> getBuffData(LivingEntity livingEntity) {
        return attDamageDeBuffData.getOrDefault(livingEntity.getUniqueId(), new HashMap<>());
    }

    public void removeBuffData(LivingEntity livingEntity, String attributeName) {
        Map<String, AttributtAccessData> attributes = attDamageDeBuffData.get(livingEntity.getUniqueId());
        if (attributes != null) {
            attributes.remove(attributeName);
            if (attributes.isEmpty()) {
                attDamageDeBuffData.remove(livingEntity.getUniqueId());
            }
        }
    }

    public void cleanupOldData(LivingEntity livingEntity) {
        long currentTime = System.currentTimeMillis();
        attDamageDeBuffData.get(livingEntity.getUniqueId()).values().removeIf(
                data -> currentTime - data.getTimestamp() > Expiration_time);
        attDamageDeBuffData.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }




}
