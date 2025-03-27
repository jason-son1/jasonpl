package org.SJYPlugin.rPGBeta2.control.damagecontrol.evp;


import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlainHitApplyEVP {

    private static final PlainHitApplyEVP instance = new PlainHitApplyEVP();

    public static PlainHitApplyEVP getInstance() {
        return instance;
    }

    DamageApplyEVPmain damageApplyEVPmain = DamageApplyEVPmain.getInstance();
    DamageComputeEVPmain damageComputeEVPmain = DamageComputeEVPmain.getInstance();

    public void PlainHitDamageEVP(LivingEntity attacker, Player offender, String BaseType, Integer mag, String Attribute) {
        double FinalDamage = damageComputeEVPmain.FinalDamage(attacker, offender, "ATTACK", 100, "NORMAL", "NORMAL","PHYSICS");
        damageApplyEVPmain.ETCDamageEVP(attacker, offender, FinalDamage, "ATTACK", "NORMAL", "NORMAL", "PHYSICS");
    }



}
