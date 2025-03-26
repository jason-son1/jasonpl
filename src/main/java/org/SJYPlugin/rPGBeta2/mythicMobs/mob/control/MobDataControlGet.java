package org.SJYPlugin.rPGBeta2.mythicMobs.mob.control;


import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MobDataControlGet {

    private static final MobDataControlGet instance = new MobDataControlGet();

    public static MobDataControlGet getInstance() {
        return instance;
    }

    public MythicMob MythicMobCheck(LivingEntity livingEntity) {
        Optional<ActiveMob> activeMob = MythicBukkit.inst().getMobManager().getActiveMob(livingEntity.getUniqueId());
        if(activeMob.isPresent()) {
            try {;
                return activeMob.get().getType();
            } catch (Exception e) {
                System.out.println("[오류] : MythicMobCheck 오류");
                throw new RuntimeException(e);
            }
        } else {
            if(MythicBukkit.inst().getMobManager().isMythicMob(livingEntity)) {
                MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMobInstance(livingEntity).getType();
                if(mythicMob != null) {
                    return mythicMob;
                } else {
                    return null;
                }
            }
            return null;
        }
    }

    public String getMobType(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            String type =  mythicMob.getConfig().getString("VType", "NORMAL");
            return type;
        }
        return "NORMAL";
    }

    public double getMobVMaxHealth(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            double maxHealth =  mythicMob.getConfig().getDouble("VMaxHealth", 20);
            return maxHealth;
        }
        return 20;
    }

    public double getMobVCurrHealth(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            double CurrHealth =  mythicMob.getConfig().getDouble("VCurrHealth", getMobVMaxHealth(livingEntity));
            return CurrHealth;
        }
        return 20;
    }

    public double getMobVAttack(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            double VAttack =  mythicMob.getConfig().getDouble("VAttack", 0);
            return VAttack;
        }
        return 0;
    }

    public double getMobVDefense(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            double VDef =  mythicMob.getConfig().getInt("VDef", 0);
            return VDef;
        }
        return 0;
    }

    public double getMobVDefIgn(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            double VDefIgn =  mythicMob.getConfig().getInt("VDefIgn", 0);
            return VDefIgn;
        }
        return 0;
    }

    public double getMobVShield(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if (mythicMob != null) {
            double shield =  mythicMob.getConfig().getDouble("VShield", 0);
            return shield;
        } else {
            return 0;
        }
    }

    public int getMobVLevel(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if (mythicMob != null) {
            int level =  mythicMob.getConfig().getInt("VLevel", 0);
            return level;
        } else {
            return 0;
        }
    }

    public Map<String, Integer> getMobAttUp(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if (mythicMob != null) {
            Map<String, Integer> AttUpMap = new HashMap<>();
            if(mythicMob.getConfig().isConfigurationSection("AttributeUp")) {
                mythicMob.getConfig().getKeys("AttributeUp").forEach(key -> {
                    int value = mythicMob.getConfig().getInt("AttributeUp." + key, 0);
                    AttUpMap.put(key, value);
                });
                return AttUpMap;
            } else {
                return new HashMap<>();
            }
        } else {
            return new HashMap<>();
        }
    }

    public Map<String, Integer> getMobResistAtt(LivingEntity livingEntity) {
        MythicMob mythicMob = MythicMobCheck(livingEntity);
        if (mythicMob != null) {
            Map<String, Integer> ResistMap = new HashMap<>();
            if(mythicMob.getConfig().isConfigurationSection("ResistAttribute")) {
                mythicMob.getConfig().getKeys("ResistAttribute").forEach(key -> {
                    int value = mythicMob.getConfig().getInt("ResistAttribute." + key, 0);
                    ResistMap.put(key, value);
                });
                return ResistMap;
            } else {
                return new HashMap<>();
            }
        } else {
            return new HashMap<>();
        }
    }






    // +++ EXP, SHILED, -----




}
