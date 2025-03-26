package org.SJYPlugin.rPGBeta2.control.attributecontrol.fire;

import org.SJYPlugin.rPGBeta2.control.damagecontrol.dot.DotDamageApply;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.evp.DamageComputeEVPmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.DamageComputePVEmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp.DamageComputePVPmain;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class FireDotDamaging {

    private static final FireDotDamaging instance = new FireDotDamaging();

    public static FireDotDamaging getInstance() {
        return instance;
    }

    DotDamageApply dotDamageApply = DotDamageApply.getInstance();
    DamageComputePVPmain damageComputePVPmain = DamageComputePVPmain.getInstance();
    DamageComputePVEmain damageComputePVEmain = DamageComputePVEmain.getInstance();
    DamageComputeEVPmain damageComputeEVPmain = DamageComputeEVPmain.getInstance();

    public void FireDotDamage(LivingEntity offender, LivingEntity attacker,
                              int duration, int interval, String DotNam, String BaseType, int mag) {
        if(offender instanceof Player) {
            if(attacker instanceof Player) {
                double FinalDamage = damageComputePVPmain.FinalDamage((Player) attacker, (Player) offender,
                        BaseType, mag, "DOT", "SPECIALSKILL", "FIRE", false);
                dotDamageApply.DotDamageApply(attacker, offender, FinalDamage, BaseType,
                        "DOT", "SPECIALSKILL", "FIRE", false, duration, interval, DotNam);
            } else {
                double FinalDamage = damageComputeEVPmain.FinalDamage(attacker, (Player) offender,
                        BaseType, mag, "DOT", "SPECIALSKILL", "FIRE");
                dotDamageApply.DotDamageApply(attacker, offender, FinalDamage, BaseType,
                        "DOT", "SPECIALSKILL", "FIRE", false, duration, interval, DotNam);
            }
        } else {
            if(attacker instanceof Player) {
                double FinalDamage = damageComputePVEmain.FinalDamage((Player) attacker, offender,
                        BaseType, mag, "DOT", "SPECIALSKILL", "FIRE", false);
                dotDamageApply.DotDamageApply(attacker, offender, FinalDamage, BaseType,
                        "DOT", "SPECIALSKILL", "FIRE", false, duration, interval, DotNam);
            } else {

            }
        }
    }

}
