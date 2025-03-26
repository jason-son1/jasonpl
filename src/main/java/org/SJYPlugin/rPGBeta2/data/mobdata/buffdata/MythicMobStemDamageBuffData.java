package org.SJYPlugin.rPGBeta2.data.mobdata.buffdata;

import org.SJYPlugin.rPGBeta2.data.DamageData;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MythicMobStemDamageBuffData {

    private static final MythicMobStemDamageBuffData instance = new MythicMobStemDamageBuffData();

    public static MythicMobStemDamageBuffData getInstance() {
        return instance;
    }

    DamageData damageData = DamageData.getInstance();

    private final Map<UUID, Map<String, Integer>> MythicMobStemDamageBuffData = new HashMap<>();

    public void setBuffData(LivingEntity livingEntity, String damageType, int value) {
        defaultBuffSet(livingEntity);
        if(damageData.getDamgeTypeSet("DamageStemType").contains(damageType)) {
            MythicMobStemDamageBuffData.computeIfAbsent(livingEntity.getUniqueId(), k -> new HashMap<>()).put(damageType, value);
        }
    }

    public Map<String, Integer> getBuffData(LivingEntity livingEntity) {
        return MythicMobStemDamageBuffData.getOrDefault(livingEntity.getUniqueId(), defaultBuffSet(livingEntity));
    }

    public Map<String, Integer> defaultBuffSet(LivingEntity livingEntity) {
        Map<String, Integer> map = new HashMap<>();
        if(MythicMobStemDamageBuffData == null || MythicMobStemDamageBuffData.get(livingEntity.getUniqueId()) == null ||
                MythicMobStemDamageBuffData.get(livingEntity.getUniqueId()).isEmpty()) {
            MythicMobStemDamageBuffData.put(livingEntity.getUniqueId(), new HashMap<>());
            for (String type : damageData.getDamgeTypeSet("DamageStemType")) {
                MythicMobStemDamageBuffData.get(livingEntity.getUniqueId()).put(type, 0);
            }
            map = MythicMobStemDamageBuffData.get(livingEntity.getUniqueId());
            return map;
        }
        return MythicMobStemDamageBuffData.get(livingEntity.getUniqueId());
    }

    public void ControlBuffData(LivingEntity livingEntity, String damageType, int changevalue) {
        defaultBuffSet(livingEntity);
        int PreValue = MythicMobStemDamageBuffData.get(livingEntity.getUniqueId()).get(damageType);
        setBuffData(livingEntity, damageType, PreValue + changevalue);
    }

    public int getBuffData(LivingEntity livingEntity, String damageType) {
        return MythicMobStemDamageBuffData.get(livingEntity.getUniqueId()).get(damageType);
    }


}
