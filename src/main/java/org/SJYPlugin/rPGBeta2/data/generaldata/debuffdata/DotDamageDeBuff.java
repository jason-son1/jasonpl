package org.SJYPlugin.rPGBeta2.data.generaldata.debuffdata;

import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DotDamageDeBuff {

    private static final DotDamageDeBuff instance = new DotDamageDeBuff();

    public static DotDamageDeBuff getInstance() {
        return instance;
    }

    private final Map<UUID, Map<String, Integer>> DotDamageDeBuffData = new HashMap<>();

    public void setBuffData(LivingEntity livingEntity, String DotName, int value) {
        defaultBuffSet(livingEntity);
        DotDamageDeBuffData.computeIfAbsent(livingEntity.getUniqueId(), k -> new HashMap<>()).put(DotName, value);
    }

    public Map<String, Integer> getBuffData(LivingEntity livingEntity) {
        return DotDamageDeBuffData.getOrDefault(livingEntity.getUniqueId(), defaultBuffSet(livingEntity));
    }

    public Map<String, Integer> defaultBuffSet(LivingEntity livingEntity) {
        Map<String, Integer> map;
        if(DotDamageDeBuffData == null || DotDamageDeBuffData.get(livingEntity.getUniqueId()) == null ||
                DotDamageDeBuffData.get(livingEntity.getUniqueId()).isEmpty()) {
            DotDamageDeBuffData.put(livingEntity.getUniqueId(), new HashMap<>());
            DotDamageDeBuffData.get(livingEntity.getUniqueId()).put("DEFAULT", 0);
            map = DotDamageDeBuffData.get(livingEntity.getUniqueId());
            return map;
        }
        return DotDamageDeBuffData.get(livingEntity.getUniqueId());
    }

    public void ControlBuffData(LivingEntity livingEntity, String damageType, int changevalue) {
        defaultBuffSet(livingEntity);
        int PreValue = DotDamageDeBuffData.get(livingEntity.getUniqueId()).get(damageType);
        if(PreValue <= 0) {
            DotDamageDeBuffData.get(livingEntity.getUniqueId()).remove(damageType);
            return;
        }
        setBuffData(livingEntity, damageType, PreValue + changevalue);
    }

    public int getBuffData(LivingEntity livingEntity, String damageType) {
        return DotDamageDeBuffData.get(livingEntity.getUniqueId()).get(damageType);
    }

    public void RemoveBuffData(LivingEntity livingEntity, String DotName) {
        DotDamageDeBuffData.get(livingEntity.getUniqueId()).remove(DotName);
    }

}
