package org.SJYPlugin.rPGBeta2.mythicMobs.mob.control;

import io.lumine.mythic.api.mobs.MythicMob;
import org.bukkit.entity.LivingEntity;

public class MobDataControlSet {

    private static final MobDataControlSet instance = new MobDataControlSet();

    public static MobDataControlSet getInstance() {
        return instance;
    }

    MobDataControlGet mobDataControlGet = new MobDataControlGet();

    public void setMobVMaxHealth(LivingEntity livingEntity, double MaxHealth) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            mythicMob.getConfig().set("VMaxHealth", MaxHealth);
        }
    }

    public void setMobVCurrHealth(LivingEntity livingEntity, double CurrHealth) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            mythicMob.getConfig().set("VCurrHealth", CurrHealth);
        }
    }

    public void setMobShield(LivingEntity livingEntity, double Shield) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            mythicMob.getConfig().set("VShield", Shield);
        }
    }

}
