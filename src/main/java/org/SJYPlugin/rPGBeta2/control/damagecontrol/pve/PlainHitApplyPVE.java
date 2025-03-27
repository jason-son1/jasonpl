package org.SJYPlugin.rPGBeta2.control.damagecontrol.pve;

import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
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

    public void PlainHitDamagePVE(DamageModifiers damageModifiers) {
        boolean isCritical = playerDamageData.OnCritical(damageModifiers);
        damageModifiers.setCritical(isCritical);
        double FinalDamage = damageComputePVEmain.FinalDamage(damageModifiers);
        damageModifiers.setFinalDamage(FinalDamage);
        damageApplyPVEmain.ETCDamagePVE(damageModifiers);
    }

}
