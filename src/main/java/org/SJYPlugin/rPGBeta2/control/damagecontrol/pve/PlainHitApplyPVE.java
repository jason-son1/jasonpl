package org.SJYPlugin.rPGBeta2.control.damagecontrol.pve;

import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlainHitApplyPVE {

    private static final PlainHitApplyPVE instance = new PlainHitApplyPVE();

    public static PlainHitApplyPVE getInstance() {
        return instance;
    }

    DamageApplyPVEmain damageApplyPVEmain = DamageApplyPVEmain.getInstance();
    DamageComputePVEmain damageComputePVEmain = DamageComputePVEmain.getInstance();
    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();

    public void PlainHitDamagePVE(Player attacker, LivingEntity offender, String BaseType, Integer mag, String Attribute) {
        boolean isCritical = playerDamageData.OnCritical(attacker);
        double FinalDamage = damageComputePVEmain.FinalDamage(attacker, offender, "ATTACK", 100,
                "NORMAL", "NORMAL","PHYSICS", isCritical);
        damageApplyPVEmain.ETCDamagePVE(attacker, offender, FinalDamage, "ATTACK", "NORMAL",
                "NORMAL", "PHYSICS", isCritical);
    }

}
