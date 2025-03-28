package org.SJYPlugin.rPGBeta2.data.attribute;

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

    private final Map<UUID, Map<String, AttributeModifiers>> attDamageDeBuffData = new HashMap<>();

    public void setBuffData(LivingEntity livingEntity, String attributeType, AttributeModifiers attributeModifiers) {
        cleanupOldData(livingEntity);
        attDamageDeBuffData.computeIfAbsent(livingEntity.getUniqueId(), k -> new HashMap<>())
                .put(attributeType, attributeModifiers);
    }

    public AttributeModifiers getBuffData(LivingEntity livingEntity, String attributeName) {
        cleanupOldData(livingEntity);
        return attDamageDeBuffData.getOrDefault(livingEntity.getUniqueId(), new HashMap<>()).get(attributeName);
    }

    public Map<String, AttributeModifiers> getBuffData(LivingEntity livingEntity) {
        cleanupOldData(livingEntity);
        return attDamageDeBuffData.getOrDefault(livingEntity.getUniqueId(), new HashMap<>());
    }

    public void removeBuffData(LivingEntity livingEntity, String attributeName) {
        Map<String, AttributeModifiers> attributes = attDamageDeBuffData.get(livingEntity.getUniqueId());
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
