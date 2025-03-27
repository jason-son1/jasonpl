package org.SJYPlugin.rPGBeta2.control.damagecontrol.pve;

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

    public double FinalDamage(Player attacker, LivingEntity offender, String DamageBaseType ,Integer magnification,
                              String RootDamageType, String StemDamageType, String Attribute, boolean isCritical) {

        double normalDamgeCorr = NormalDamageCorrection(attacker, offender, DamageBaseType, magnification,
                RootDamageType, StemDamageType, Attribute, isCritical);
        double finaldef = playerDamageData.PlayerFinalDef(attacker, offender);
        double DefMag = playerDamageData.PlayerDefMag(attacker, finaldef, DamageBaseType);

//        Integer AttackerLevel = configUtilStat2.getLevel(attacker);
//        Integer OffenderLevel = configUtilStat2.getLevel(offender);

//        attacker.sendMessage("파이널 대미지 계산 통과");

        return normalDamgeCorr*DefMag;
    }


    public double NormalDamageCorrection(Player attacker, LivingEntity offender, String DamageBaseType ,Integer magnification,
                                         String RootDamageType, String StemDamageType, String Attribute, boolean isCritical) {
        double normalDamage = NormalDamage(attacker, offender, DamageBaseType, magnification, RootDamageType, StemDamageType, Attribute, isCritical);
        double DamageTypeMag = playerDamageData.DamageTypeMag(attacker, RootDamageType, StemDamageType);
        double MobTypeMag = playerDamageData.MobTypeMag(attacker, mobData.getMobType(offender));
        return normalDamage*DamageTypeMag*MobTypeMag;
    }


    public double NormalDamage(Player attacker, LivingEntity offender, String DamageBaseType ,
                               Integer magnification, String RootDamageType, String StemDamageType, String Attribute, boolean isCritical) {
        double FinaldamageValue = playerDamageData.PlayerBaseDamageValue(attacker, DamageBaseType);
        int attUp, attRes;
        double AttDamMag;
        double CriDam = playerDamageData.CriticalDamage(attacker);

        attUp = playerDamageData.PlayerAttUp(attacker, Attribute);
        attRes = mythicMobDamageData.MobAttResist(offender, Attribute);

        if(isCritical) {
            AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);
            return (FinaldamageValue*(magnification/100) + FinaldamageValue*(magnification/100)*AttDamMag)*CriDam;
        } else {
            AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);
            return (FinaldamageValue*(magnification/100) + FinaldamageValue*(magnification/100)*AttDamMag);
        }
    }


}
