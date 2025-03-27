package org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp;

import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
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

    public void PlainHitDamagePVP(DamageModifiers damageModifiers) {
        boolean isCritical = playerDamageData.OnCritical(damageModifiers);
        double FinalDamage = damageComputePVPmain.FinalDamage(damageModifiers);
        damageApplyPVPmain.ETCDamagePVP(damageModifiers);
    }

}
