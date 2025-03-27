package org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata;

import org.SJYPlugin.rPGBeta2.mythicMobs.mob.control.MobDataControlGet;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobData {

    private static final MobData instance = new MobData();

    public static MobData getInstance() {
        return instance;
    }

    MobDataControlGet mobDataControlGet = MobDataControlGet.getInstance();

    private final Map<UUID, Double> MobMaxHealth;

    private final Map<UUID, Double> MobCurrHealth;

    private final Map<UUID, Double> MobAttack;

    private final Map<UUID, Double> MobDef;

    private final Map<UUID, Double> MobDefIgn;

    private final Map<UUID, Double> MobShield;

    private final Map<UUID, Map<String, Integer>> MobResistAtt;

    private final Map<UUID, Map<String, Integer>> MobAttUp;

    private final Map<UUID, String> MobType;

    private MobData() {
        this.MobMaxHealth = new HashMap<>();
        this.MobCurrHealth = new HashMap<>();
        this.MobDef = new HashMap<>();
        this.MobAttack = new HashMap<>();
        this.MobShield = new HashMap<>();
        this.MobResistAtt = new HashMap<>();
        this.MobAttUp = new HashMap<>();
        this.MobDefIgn = new HashMap<>();
        this.MobType = new HashMap<>();
    }


    public double getMobMaxHealth(LivingEntity livingEntity) {
        return this.MobMaxHealth.get(livingEntity.getUniqueId());
    }

    public double getMobCurrHealth(LivingEntity livingEntity) {
        return this.MobCurrHealth.get(livingEntity.getUniqueId());
    }

    public double getMobDef(LivingEntity livingEntity) {
        return this.MobDef.get(livingEntity.getUniqueId());
    }

    public double getMobDefIgn(LivingEntity livingEntity) {
        return this.MobDefIgn.get(livingEntity.getUniqueId());
    }

    public double getMobAttack(LivingEntity livingEntity) {
        return this.MobAttack.get(livingEntity.getUniqueId());
    }

    public double getMobShield(LivingEntity livingEntity) {
        return this.MobShield.get(livingEntity.getUniqueId());
    }

    public Map<String, Integer> getMobResistAtt(LivingEntity livingEntity) {
        return this.MobResistAtt.get(livingEntity.getUniqueId());
    }

    public Map<String, Integer> getMobAttUp(LivingEntity livingEntity) {
        return this.MobAttUp.get(livingEntity.getUniqueId());
    }

    public String getMobType(LivingEntity livingEntity) {
        return this.MobType.get(livingEntity.getUniqueId());
    }



    public void DefaultSetting_Mythic(LivingEntity livingEntity) {
        this.MobMaxHealth.put(livingEntity.getUniqueId(), mobDataControlGet.getMobVMaxHealth(livingEntity));
        this.MobCurrHealth.put(livingEntity.getUniqueId(), mobDataControlGet.getMobVCurrHealth(livingEntity));
        this.MobAttack.put(livingEntity.getUniqueId(), mobDataControlGet.getMobVAttack(livingEntity));
        this.MobDef.put(livingEntity.getUniqueId(), mobDataControlGet.getMobVDefense(livingEntity));
        this.MobShield.put(livingEntity.getUniqueId(), mobDataControlGet.getMobVShield(livingEntity));
        this.MobResistAtt.put(livingEntity.getUniqueId(), mobDataControlGet.getMobResistAtt(livingEntity));
        this.MobAttUp.put(livingEntity.getUniqueId(), mobDataControlGet.getMobAttUp(livingEntity));
        this.MobDefIgn.put(livingEntity.getUniqueId(), mobDataControlGet.getMobVDefIgn(livingEntity));
        this.MobType.put(livingEntity.getUniqueId(), mobDataControlGet.getMobType(livingEntity));
    }


    public void SetMobMaxHealth(LivingEntity livingEntity, double Value) {
        this.MobMaxHealth.put(livingEntity.getUniqueId(), Value);
    }

    public void SetMobCurrHealth(LivingEntity livingEntity, double Value) {
        this.MobCurrHealth.put(livingEntity.getUniqueId(), Value);
    }

    public void SetMobAttack(LivingEntity livingEntity, double Value) {
        this.MobAttack.put(livingEntity.getUniqueId(), Value);
    }

    public void SetMobDef(LivingEntity livingEntity, double Value) {
        this.MobDef.put(livingEntity.getUniqueId(), Value);
    }

    public void SetMobDefIgn(LivingEntity livingEntity, double Value) {
        this.MobDefIgn.put(livingEntity.getUniqueId(), Value);
    }

    public void SetMobShield(LivingEntity livingEntity, double Value) {
        this.MobShield.put(livingEntity.getUniqueId(), Value);
    }

    public void SetMobResistAtt(LivingEntity livingEntity, Map<String, Integer> Value) {
        this.MobResistAtt.put(livingEntity.getUniqueId(), Value);
    }

    public void SetMobAttUp(LivingEntity livingEntity, Map<String, Integer> Value) {
        this.MobAttUp.put(livingEntity.getUniqueId(), Value);
    }

    public void SetMobType(LivingEntity livingEntity, String Value) {
        this.MobType.put(livingEntity.getUniqueId(), Value);
    }


    public void MobDataRemove(LivingEntity livingEntity) {
        this.MobMaxHealth.remove(livingEntity.getUniqueId());
        this.MobCurrHealth.remove(livingEntity.getUniqueId());
        this.MobAttack.remove(livingEntity.getUniqueId());
        this.MobDef.remove(livingEntity.getUniqueId());
        this.MobShield.remove(livingEntity.getUniqueId());
        this.MobResistAtt.remove(livingEntity.getUniqueId());
        this.MobAttUp.remove(livingEntity.getUniqueId());
        this.MobDefIgn.remove(livingEntity.getUniqueId());
        this.MobType.remove(livingEntity.getUniqueId());
    }

    public void MobAllClear() {
        this.MobMaxHealth.clear();
        this.MobCurrHealth.clear();
        this.MobAttack.clear();
        this.MobShield.clear();
        this.MobDef.clear();
        this.MobResistAtt.clear();
        this.MobAttUp.clear();
        this.MobDefIgn.clear();
        this.MobType.clear();
    }




}
