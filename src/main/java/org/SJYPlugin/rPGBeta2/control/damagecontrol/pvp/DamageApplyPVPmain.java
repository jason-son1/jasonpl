package org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp;

import org.SJYPlugin.rPGBeta2.control.hpcontrol.HPControl;
import org.SJYPlugin.rPGBeta2.customevents.damage.PlayerDieEvent;
import org.SJYPlugin.rPGBeta2.customevents.damage.RPGDamageEvent;
import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.SJYPlugin.rPGBeta2.util.persistantdata.DamageCausePersistantData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DamageApplyPVPmain {

    private static final DamageApplyPVPmain instance = new DamageApplyPVPmain();

    public static DamageApplyPVPmain getInstance() {
        return instance;
    }

    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();
    DamageCausePersistantData damageCausePersistantData = DamageCausePersistantData.getInstance();
    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();


    public void DamagePVP(DamageModifiers damageModifiers) {

        Player attacker = damageModifiers.getAttackerPlayer();
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
                offender.setKiller(attacker);
                offender.setLastDamage(RealHealth);
            } else {
                damageCausePersistantData.addData(attacker, "CustomDamageCause_PVP", "SPELL_DAMAGE");
                configUtilStat2.HPChange(offender, (double)0, -1*(FinalDamage));
                offender.damage(RealDamageCal(FinalDamage, VirtualMaxHealth, RealMaxHealth), attacker);
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

    private boolean isProcessingPlainDamage = false;

    public void ETCDamagePVP(DamageModifiers damageModifiers) {
        if(isProcessingPlainDamage) {
            return;
        }

        isProcessingPlainDamage = true;

        try {

            Player attacker = damageModifiers.getAttackerPlayer();
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
                    offender.setKiller(attacker);
                    offender.setLastDamage(RealHealth);
                } else {
                    configUtilStat2.HPChange(offender, (double)0, -1*(FinalDamage));
                    offender.damage(RealDamageCal(FinalDamage, VirtualMaxHealth, RealMaxHealth), attacker);
                    offender.setLastDamage(FinalDamage);
                }
            } else {
                configUtilStat2.HPChange(offender, (double)0, (double) 0);
                HPControl.getInstance().HealthSetPlayer(offender);
            }
        } finally {
            isProcessingPlainDamage = false;
        }
    }



}
