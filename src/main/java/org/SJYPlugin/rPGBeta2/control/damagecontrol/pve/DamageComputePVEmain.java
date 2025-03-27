package org.SJYPlugin.rPGBeta2.control.damagecontrol.pve;

import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
import org.SJYPlugin.rPGBeta2.data.mobdata.damagedata.MythicMobDamageData;
import org.SJYPlugin.rPGBeta2.data.playerdata.buffdata.PlayerMobTypeDamageBuffData;
import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata.MobData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class DamageComputePVEmain {

    private static final DamageComputePVEmain instance = new DamageComputePVEmain();

    public static DamageComputePVEmain getInstance() {
        return instance;
    }

    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();
    MythicMobDamageData mythicMobDamageData = MythicMobDamageData.getInstance();
    MobData mobData = MobData.getInstance();

    public double FinalDamage(DamageModifiers damageModifiers) {

        double normalDamgeCorr = NormalDamageCorrection(damageModifiers);
        double finaldef = playerDamageData.PlayerFinalDef(damageModifiers);
        double DefMag = playerDamageData.PlayerDefMag(damageModifiers, finaldef);

//        Integer AttackerLevel = configUtilStat2.getLevel(attacker);
//        Integer OffenderLevel = configUtilStat2.getLevel(offender);

//        attacker.sendMessage("파이널 대미지 계산 통과");

        return normalDamgeCorr*DefMag;
    }


    public double NormalDamageCorrection(DamageModifiers damageModifiers) {
        double normalDamage = NormalDamage(damageModifiers);
        double DamageTypeMag = playerDamageData.DamageTypeMag(damageModifiers);
        double MobTypeMag = playerDamageData.MobTypeMag(damageModifiers, mobData.getMobType(damageModifiers.getOffender()));
        return normalDamage*DamageTypeMag*MobTypeMag;
    }


    public double NormalDamage(DamageModifiers damageModifiers) {
        double FinaldamageValue = playerDamageData.PlayerBaseDamageValue(damageModifiers);
        int attUp, attRes;
        double AttDamMag;
        double CriDam = playerDamageData.CriticalDamage(damageModifiers);

        attUp = playerDamageData.PlayerAttUp(damageModifiers);
        attRes = mythicMobDamageData.MobAttResist(damageModifiers);

        if(damageModifiers.isCritical()) {
            AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);
            return (FinaldamageValue*(damageModifiers.getMagnification()/100) + FinaldamageValue*(damageModifiers.getMagnification()/100)*AttDamMag)*CriDam;
        } else {
            AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);
            return (FinaldamageValue*(damageModifiers.getMagnification()/100) + FinaldamageValue*(damageModifiers.getMagnification()/100)*AttDamMag);
        }
    }


}
