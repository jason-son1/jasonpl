package org.SJYPlugin.rPGBeta2.control.hpcontrol;

import org.SJYPlugin.rPGBeta2.mythicMobs.mob.control.MobDataControlGet;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata.MobData;
import org.bukkit.entity.LivingEntity;

public class ShieldControlMob {

    private static final ShieldControlMob instance = new ShieldControlMob();

    public static ShieldControlMob getInstance() {return instance;}

    MobData mobData = MobData.getInstance();

    public boolean ExistShield(LivingEntity livingEntity) {
        if(mobData.getMobShield(livingEntity) > 0) {
            return true;
        } else {
            SetShieldZero(livingEntity);
            return false;
        }
    }

    public double GetShield(LivingEntity livingEntity) {
        if(ExistShield(livingEntity)) {
            return mobData.getMobShield(livingEntity);
        } else {
            SetShieldZero(livingEntity);
            return 0;
        }
    }

    public void UpShield(LivingEntity livingEntity, double Value) {
        mobData.SetMobShield(livingEntity, mobData.getMobShield(livingEntity) + Value);
    }

    public void DownShield(LivingEntity livingEntity, double Value) {
        mobData.SetMobShield(livingEntity, mobData.getMobShield(livingEntity) - Value);
        ExistShield(livingEntity);
    }



    public void SetShieldZero(LivingEntity livingEntity) {
        mobData.SetMobShield(livingEntity, 0);
    }

}
