package org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp;

import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
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

    public double FinalDamage(DamageModifiers damageModifiers) {

        double normalDamageCorr = NormalDamageCorrection(damageModifiers);
        double finaldef = playerDamageData.PlayerFinalDef(damageModifiers);
        double DefMag = playerDamageData.PlayerDefMag(damageModifiers, finaldef);

//        Integer AttackerLevel = configUtilStat2.getLevel(attacker);
//        Integer OffenderLevel = configUtilStat2.getLevel(offender);

        return normalDamageCorr*DefMag;
    }

    public double NormalDamageCorrection(DamageModifiers damageModifiers) {
        double normalDamage = NormalDamage(damageModifiers);
        double mag = playerDamageData.DamageTypeMag(damageModifiers);
        return normalDamage*mag;
    }

    public double NormalDamage(DamageModifiers damageModifiers) {
        double FinaldamageValue = playerDamageData.PlayerBaseDamageValue(damageModifiers);
        int attUp, attRes;
        double AttDamMag;
        double CriDam = playerDamageData.CriticalDamage(damageModifiers);

        attUp = playerDamageData.PlayerAttUp(damageModifiers);
        attRes = playerDamageData.PlayerAttResist(damageModifiers);

        if(damageModifiers.isCritical()) {
            AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);
            return (FinaldamageValue*(damageModifiers.getMagnification()/100) +
                    FinaldamageValue*(damageModifiers.getMagnification()/100)*AttDamMag)*CriDam;
        } else {
            AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);
            return (FinaldamageValue*(damageModifiers.getMagnification()/100) +
                    FinaldamageValue*(damageModifiers.getMagnification()/100)*AttDamMag);
        }
    }



}
