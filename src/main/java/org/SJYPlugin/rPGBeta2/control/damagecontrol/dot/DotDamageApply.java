package org.SJYPlugin.rPGBeta2.control.damagecontrol.dot;

import org.SJYPlugin.rPGBeta2.RPGBeta2;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.DamageApplyPVEmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp.DamageApplyPVPmain;
import org.SJYPlugin.rPGBeta2.data.generaldata.debuffdata.DotDamageDeBuff;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DotDamageApply {

    private static final DotDamageApply instance = new DotDamageApply();

    public static DotDamageApply getInstance() {
        return instance;
    }

    DotDamageDeBuff dotDamageDeBuff = DotDamageDeBuff.getInstance();
    DamageApplyPVPmain damageApplyPVPmain = DamageApplyPVPmain.getInstance();
    DamageApplyPVEmain damageApplyPVEmain = DamageApplyPVEmain.getInstance();

    public void DotDamageApply(LivingEntity attacker, LivingEntity offender, double FinalDamage, String BaseType,
                               String StemType, String RootType, String Attribute, boolean isCritical,
                               int duration, int interval, String DotName) {
        if(offender.isDead() || offender == null) {
            return;
        }

        if(offender instanceof Player) {
            dotDamageDeBuff.setBuffData(offender, DotName, duration);
            new BukkitRunnable() {
                int ticks = 0;
                @Override
                public void run() {
                    dotDamageDeBuff.ControlBuffData(offender, DotName, -interval);
                    if (ticks >= duration) {
                        dotDamageDeBuff.RemoveBuffData(offender, DotName);
                        this.cancel();
                        return;
                    }
                    if(dotDamageDeBuff.getBuffData(offender).containsKey(DotName)) {
                        damageApplyPVPmain.DamagePVP((Player) attacker, (Player) offender, FinalDamage, BaseType, StemType, RootType, Attribute, isCritical);
                    } else {
                        this.cancel();
                        return;
                    }
                    ticks += interval;
                }
            }.runTaskTimer(RPGBeta2.getInstance(), 0, interval);
        } else {
            dotDamageDeBuff.setBuffData(offender, DotName, duration);
            new BukkitRunnable() {
                int ticks = 0;
                @Override
                public void run() {
                    dotDamageDeBuff.ControlBuffData(offender, DotName, -interval);
                    if (ticks >= duration) {
                        dotDamageDeBuff.RemoveBuffData(offender, DotName);
                        this.cancel();
                        return;
                    }
                    if(dotDamageDeBuff.getBuffData(offender).containsKey(DotName)) {
                        damageApplyPVEmain.DamagePVE_Mythic((Player) attacker, offender, FinalDamage, BaseType, StemType, RootType, Attribute, isCritical);
                    } else {
                        this.cancel();
                        return;
                    }
                    ticks += interval;
                }
            }.runTaskTimer(RPGBeta2.getInstance(), 0, interval);
        }

    }


}
