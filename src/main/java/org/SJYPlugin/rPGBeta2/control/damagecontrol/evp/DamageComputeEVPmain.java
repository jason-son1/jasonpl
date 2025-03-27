package org.SJYPlugin.rPGBeta2.control.damagecontrol.evp;

import org.SJYPlugin.rPGBeta2.data.mobdata.damagedata.MythicMobDamageData;
import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageComputeEVPmain {

    private static final DamageComputeEVPmain instance = new DamageComputeEVPmain();

    public static DamageComputeEVPmain getInstance() {return instance;}

    MythicMobDamageData mythicMobDamageData = MythicMobDamageData.getInstance();
    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();


    public double FinalDamage(LivingEntity attacker, Player offender, String DamageBaseType ,Integer magnification, String RootDamageType, String StemDamageType, String Attribute) {

        Double normalDamgeCorr = NormalDamageCorrection(attacker, offender, DamageBaseType, magnification, RootDamageType, StemDamageType, Attribute);
        Double finaldef = mythicMobDamageData.MobFinalDef(attacker, offender);
        Double DefMag = mythicMobDamageData.MobDefMag(attacker, finaldef, DamageBaseType);

//        Integer AttackerLevel = configUtilStat2.getLevel(attacker);
//        Integer OffenderLevel = configUtilStat2.getLevel(offender);

//        attacker.sendMessage("파이널 대미지 계산 통과");

        return normalDamgeCorr*DefMag;
    }

    public double NormalDamageCorrection(LivingEntity attacker, Player offender, String DamageBaseType ,Integer magnification, String RootDamageType, String StemDamageType, String Attribute) {
        Double normalDamage = NormalDamage(attacker, offender, DamageBaseType, magnification, RootDamageType, StemDamageType, Attribute);
        Double mag = mythicMobDamageData.DamageTypeMag(attacker, RootDamageType, StemDamageType);
        return normalDamage*mag;
    }

    public double NormalDamage(LivingEntity attacker, Player offender, String DamageBaseType , Integer magnification, String RootDamageType, String StemDamageType, String Attribute) {
        double FinalDamageValue = mythicMobDamageData.MobBaseDamageValue(attacker, DamageBaseType);
        int attUp, attRes;
        double AttDamMag;

        attUp = mythicMobDamageData.MobAttUp(attacker, Attribute);
        attRes = playerDamageData.PlayerAttResist(offender, Attribute);

        AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);

        return (FinalDamageValue*(magnification/100) + FinalDamageValue*(magnification/100)*AttDamMag);
    }



}
