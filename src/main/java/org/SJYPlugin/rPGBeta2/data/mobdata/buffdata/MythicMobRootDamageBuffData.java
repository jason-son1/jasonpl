package org.SJYPlugin.rPGBeta2.data.mobdata.buffdata;

import org.SJYPlugin.rPGBeta2.data.DamageData;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MythicMobRootDamageBuffData {

    private static final MythicMobRootDamageBuffData instance = new MythicMobRootDamageBuffData();

    public static MythicMobRootDamageBuffData getInstance() {
        return instance;
    }

    DamageData damageData = DamageData.getInstance();

    private final Map<UUID, Map<String, Integer>> MythicMobRootDamageBuffData = new HashMap<>();

    public void setBuffData(LivingEntity livingEntity, String damageType, int value) {
        defaultBuffSet(livingEntity);
        if(damageData.getDamgeTypeSet("DamageRootType").contains(damageType)) {
            MythicMobRootDamageBuffData.computeIfAbsent(livingEntity.getUniqueId(), k -> new HashMap<>()).put(damageType, value);
        }
    }

    public Map<String, Integer> getBuffData(LivingEntity livingEntity) {
        return MythicMobRootDamageBuffData.getOrDefault(livingEntity.getUniqueId(), defaultBuffSet(livingEntity));
    }

    public Map<String, Integer> defaultBuffSet(LivingEntity livingEntity) {
        Map<String, Integer> map;
        if(MythicMobRootDamageBuffData == null || MythicMobRootDamageBuffData.get(livingEntity.getUniqueId()) == null ||
                MythicMobRootDamageBuffData.get(livingEntity.getUniqueId()).isEmpty()) {
            MythicMobRootDamageBuffData.put(livingEntity.getUniqueId(), new HashMap<>());
            for (String type : damageData.getDamgeTypeSet("DamageRootType")) {
                MythicMobRootDamageBuffData.get(livingEntity.getUniqueId()).put(type, 0);
            }
            map = MythicMobRootDamageBuffData.get(livingEntity.getUniqueId());
            return map;
        }
        return MythicMobRootDamageBuffData.get(livingEntity.getUniqueId());
    }

    public void ControlBuffData(LivingEntity livingEntity, String damageType, int changevalue) {
        defaultBuffSet(livingEntity);
        int PreValue = MythicMobRootDamageBuffData.get(livingEntity.getUniqueId()).get(damageType);
        setBuffData(livingEntity, damageType, PreValue + changevalue);
    }

    public int getBuffData(LivingEntity livingEntity, String damageType) {
        return MythicMobRootDamageBuffData.get(livingEntity.getUniqueId()).get(damageType);
    }

}
