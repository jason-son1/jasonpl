package org.SJYPlugin.rPGBeta2.control.damagecontrol.evp;


import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlainHitApplyEVP {

    private static final PlainHitApplyEVP instance = new PlainHitApplyEVP();

    public static PlainHitApplyEVP getInstance() {
        return instance;
    }

    DamageApplyEVPmain damageApplyEVPmain = DamageApplyEVPmain.getInstance();
    DamageComputeEVPmain damageComputeEVPmain = DamageComputeEVPmain.getInstance();

    public void PlainHitDamageEVP(DamageModifiers damageModifiers) {
        double FinalDamage = damageComputeEVPmain.FinalDamage(damageModifiers);
        damageApplyEVPmain.ETCDamageEVP(damageModifiers);
    }



}
