package org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp;

import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.bukkit.entity.Player;

public class PlainHitApplyPVP {

    private static final PlainHitApplyPVP instance = new PlainHitApplyPVP();

    public static PlainHitApplyPVP getInstance() {
        return instance;
    }

    DamageApplyPVPmain damageApplyPVPmain = new DamageApplyPVPmain();
    DamageComputePVPmain damageComputePVPmain = new DamageComputePVPmain();
    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();

    public void PlainHitDamagePVP(Player attacker, Player offender, String BaseType, Integer mag, String Attribute) {
        boolean isCritical = playerDamageData.OnCritical(attacker);
        double FinalDamage = damageComputePVPmain.FinalDamage(attacker, offender, "ATTACK",
                100, "NORMAL", "NORMAL", "PHYSICS", isCritical);
        damageApplyPVPmain.ETCDamagePVP(attacker, offender, FinalDamage,
                "ATTACK", "NORMAL", "NORMAL", "PHYSICS", isCritical);
    }

}
