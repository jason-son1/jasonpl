package org.SJYPlugin.rPGBeta2.control.damagecontrol.dot;

import org.SJYPlugin.rPGBeta2.RPGBeta2;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.GeneralDamageApply;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.DamageApplyPVEmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp.DamageApplyPVPmain;
import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
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
    GeneralDamageApply generalDamageApply = GeneralDamageApply.getInstance();

    public void DotDamageApply(DamageModifiers damageModifiers,
                               int duration, int interval, String DotName) {
        LivingEntity attacker = damageModifiers.getAttacker();
        LivingEntity offender = damageModifiers.getOffender();
        if(offender.isDead()) {
            return;
        }

        if(attacker == null) {
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
                        generalDamageApply.DamageApply(damageModifiers);
                    } else {
                        this.cancel();
                        return;
                    }
                    ticks += interval;
                }
            }.runTaskTimer(RPGBeta2.getInstance(), 0, interval);
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
                        damageApplyPVPmain.DamagePVP(damageModifiers);
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
                        damageApplyPVEmain.DamagePVE_Mythic(damageModifiers);
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
