package org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp;

import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.bukkit.entity.Player;

import java.util.Random;

public class DamageComputePVPmain {

    private static final DamageComputePVPmain instance = new DamageComputePVPmain();

    public static DamageComputePVPmain getInstance() {
        return instance;
    }


    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();
    private final Random random = new Random();

    public double FinalDamage(Player attacker, Player offender, String DamageBaseType ,Integer magnification,
                              String RootDamageType, String StemDamageType, String Attribute, boolean isCritical) {

        double normalDamageCorr = NormalDamageCorrection(attacker, offender, DamageBaseType, magnification,
                RootDamageType, StemDamageType, Attribute, isCritical);
        double finaldef = playerDamageData.PlayerFinalDef(attacker, offender);
        Double DefMag = playerDamageData.PlayerDefMag(attacker, finaldef, DamageBaseType);

//        Integer AttackerLevel = configUtilStat2.getLevel(attacker);
//        Integer OffenderLevel = configUtilStat2.getLevel(offender);

        return normalDamageCorr*DefMag;
    }

    public double NormalDamageCorrection(Player attacker, Player offender, String DamageBaseType ,Integer magnification,
                                         String RootDamageType, String StemDamageType, String Attribute, boolean isCritical) {
        double normalDamage = NormalDamage(attacker, offender, DamageBaseType, magnification, RootDamageType, StemDamageType, Attribute, isCritical);
        double mag = playerDamageData.DamageTypeMag(attacker, RootDamageType, StemDamageType);
        return normalDamage*mag;
    }

    public double NormalDamage(Player attacker, Player offender, String DamageBaseType ,Integer magnification,
                               String RootDamageType, String StemDamageType, String Attribute, boolean isCritical) {
        double FinaldamageValue = playerDamageData.PlayerBaseDamageValue(attacker, DamageBaseType);
        int attUp, attRes;
        double AttDamMag;
        double CriDam = playerDamageData.CriticalDamage(attacker);

        attUp = playerDamageData.PlayerAttUp(attacker, Attribute);
        attRes = playerDamageData.PlayerAttResist(attacker, Attribute);

        if(isCritical) {
            AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);
            return (FinaldamageValue*(magnification/100) + FinaldamageValue*(magnification/100)*AttDamMag)*CriDam;
        } else {
            AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);
            return (FinaldamageValue*(magnification/100) + FinaldamageValue*(magnification/100)*AttDamMag);
        }
    }



}
