package org.SJYPlugin.rPGBeta2.control.hpcontrol;

import io.lumine.mythic.api.mobs.MythicMob;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.control.MobDataControlGet;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.control.MobDataControlSet;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata.MobData;
import org.bukkit.entity.LivingEntity;

public class MythicControl {



    MobDataControlGet mobDataControlGet = MobDataControlGet.getInstance();
    MobDataControlSet mobDataControlSet = MobDataControlSet.getInstance();
    MobData mobData = MobData.getInstance();
    HPControl hpControl = HPControl.getInstance();


    public void MythicMobChangeVMaxHealth(LivingEntity livingEntity, double ChangeValue) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(livingEntity);
        if(mythicMob != null) {
            if(ChangeValue != 0) {
                double existingVMaxHealth = mobData.getMobMaxHealth(livingEntity);

                mobData.SetMobMaxHealth(livingEntity, existingVMaxHealth + ChangeValue);

                hpControl.HealthSetEntity_Mythic(livingEntity);
            } else {
                hpControl.HealthSetEntity_Mythic(livingEntity);
            }
        } else {

        }
    }

    public void MythicMobChangeVCurrHealth(LivingEntity livingEntity, double ChangeValue) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(livingEntity);
            if(mythicMob != null) {
                if(ChangeValue != 0) {
                    double existingVCurrHealth = mobData.getMobCurrHealth(livingEntity);

                    mobData.SetMobCurrHealth(livingEntity, existingVCurrHealth + ChangeValue);

//                    hpControl.HealthSetEntity_Mythic(livingEntity);
                } else {
//                    hpControl.HealthSetEntity_Mythic(livingEntity);
                }
            } else {

            }
    }



}
