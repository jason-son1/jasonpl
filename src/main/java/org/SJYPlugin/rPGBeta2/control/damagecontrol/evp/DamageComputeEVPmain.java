package org.SJYPlugin.rPGBeta2.control.damagecontrol.evp;

import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
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


    public double FinalDamage(DamageModifiers damageModifiers) {

        Double normalDamgeCorr = NormalDamageCorrection(damageModifiers);
        Double finaldef = mythicMobDamageData.MobFinalDef(damageModifiers);
        Double DefMag = mythicMobDamageData.MobDefMag(damageModifiers, finaldef);

//        Integer AttackerLevel = configUtilStat2.getLevel(attacker);
//        Integer OffenderLevel = configUtilStat2.getLevel(offender);

//        attacker.sendMessage("파이널 대미지 계산 통과");

        return normalDamgeCorr*DefMag;
    }

    public double NormalDamageCorrection(DamageModifiers damageModifiers) {
        Double normalDamage = NormalDamage(damageModifiers);
        Double mag = mythicMobDamageData.DamageTypeMag(damageModifiers);
        return normalDamage*mag;
    }

    public double NormalDamage(DamageModifiers damageModifiers) {
        double FinalDamageValue = mythicMobDamageData.MobBaseDamageValue(damageModifiers);
        int attUp, attRes;
        double AttDamMag;

        attUp = mythicMobDamageData.MobAttUp(damageModifiers);
        attRes = playerDamageData.PlayerAttResist(damageModifiers);

        AttDamMag = playerDamageData.AttDamageMagControl(attUp, attRes);

        return (FinalDamageValue*(damageModifiers.getMagnification()/100) + FinalDamageValue*(damageModifiers.getMagnification()/100)*AttDamMag);
    }



}
