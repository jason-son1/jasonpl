package org.SJYPlugin.rPGBeta2.control.damagecontrol;

import org.SJYPlugin.rPGBeta2.control.damagecontrol.evp.DamageApplyEVPmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.DamageApplyPVEmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp.DamageApplyPVPmain;
import org.SJYPlugin.rPGBeta2.control.hpcontrol.HPControl;
import org.SJYPlugin.rPGBeta2.customevents.damage.PlayerDieEvent;
import org.SJYPlugin.rPGBeta2.customevents.damage.RPGDamageEvent;
import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GeneralDamageApply {

    private static final GeneralDamageApply instance = new GeneralDamageApply();

    public static GeneralDamageApply getInstance() {
        return instance;
    }

    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();
    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();
    DamageApplyPVPmain damageApplyPVPmain = DamageApplyPVPmain.getInstance();
    DamageApplyPVEmain damageApplyPVEmain = DamageApplyPVEmain.getInstance();
    DamageApplyEVPmain damageApplyEVPmain = DamageApplyEVPmain.getInstance();

    public void DamageApply(DamageModifiers damageModifiers) {
        if (damageModifiers.getOffender() == null) {
            return;
        }
        if (damageModifiers.getAttacker() == null) {
            DamageApplyNonAttacker(damageModifiers);
        }
        if (damageModifiers.getAttacker() instanceof Player) {
            if (damageModifiers.getOffender() instanceof Player) {
                damageApplyPVPmain.DamagePVP(damageModifiers);
            } else {
                damageApplyPVEmain.DamagePVE_Mythic(damageModifiers);
            }
        } else {
            if (damageModifiers.getOffender() instanceof Player) {
                damageApplyEVPmain.DamageEVP(damageModifiers);
            } else {
                damageApplyPVEmain.DamagePVE_Mythic(damageModifiers);
            }
        }
    }

    private void DamageApplyNonAttacker(DamageModifiers damageModifiers) {
        Player offender = damageModifiers.getOffenderPlayer();

        HPControl.getInstance().HealthSetPlayer(offender);

        double RealMaxHealth = offender.getHealthScale();
        double RealHealth = offender.getHealth();

        double VirtualMaxHealth = configUtilStat2.getMaxHP(offender);
        double VirtualHealth = configUtilStat2.getHP(offender);

        RPGDamageEvent event = new RPGDamageEvent(damageModifiers);
        Bukkit.getPluginManager().callEvent(event);

        double FinalDamage = playerDamageData.playerShiledPass(damageModifiers);

        if(FinalDamage != 0) {
            if(FinalDamage >= VirtualHealth) {
                configUtilStat2.HPChange(offender, (double) 0, (double) -1*(VirtualHealth));
                offender.setHealth(0);
                PlayerDieEvent playerDieEvent = new PlayerDieEvent(damageModifiers);
                Bukkit.getPluginManager().callEvent(playerDieEvent);
                offender.setKiller(null);
                offender.setLastDamage(RealHealth);
            } else {
//                damageCausePersistantData.addData(attacker, "CustomDamageCause_PVP", "SPELL_DAMAGE");
                configUtilStat2.HPChange(offender, (double)0, -1*(FinalDamage));
                offender.damage(RealDamageCal(FinalDamage, VirtualMaxHealth, RealMaxHealth));
                offender.setLastDamage(FinalDamage);
            }
        } else {
            configUtilStat2.HPChange(offender, (double)0, (double) 0);
            HPControl.getInstance().HealthSetPlayer(offender);
        }
    }

    private double RealDamageCal(double FinalDamage, double VMaxHP, double RMaxHP) {
        return ((FinalDamage)/(VMaxHP))*RMaxHP;
    }


}
