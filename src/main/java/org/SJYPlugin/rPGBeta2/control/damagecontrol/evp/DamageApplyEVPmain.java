package org.SJYPlugin.rPGBeta2.control.damagecontrol.evp;

import org.SJYPlugin.rPGBeta2.control.hpcontrol.HPControl;
import org.SJYPlugin.rPGBeta2.customevents.damage.PlayerDieEvent;
import org.SJYPlugin.rPGBeta2.customevents.damage.RPGDamageEvent;
import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class DamageApplyEVPmain {


    private static final DamageApplyEVPmain instance = new DamageApplyEVPmain();

    public static DamageApplyEVPmain getInstance() {return instance;}


    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();
    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();

    public double DamageEVP(LivingEntity attacker, Player offender, double FinalDamage, String BaseType, String StemType, String RootType, String Attribute) {

        HPControl.getInstance().HealthSetPlayer(offender);

        double RealMaxHealth = offender.getHealthScale();
        double RealHealth = offender.getHealth();

        double VirtualMaxHealth = configUtilStat2.getMaxHP(offender);
        double VirtualHealth = configUtilStat2.getHP(offender);

        RPGDamageEvent event = new RPGDamageEvent(attacker, offender, FinalDamage, BaseType, StemType, RootType, Attribute, false);
        Bukkit.getPluginManager().callEvent(event);

        FinalDamage = playerDamageData.playerShiledPass(FinalDamage, offender);

        if(FinalDamage != 0) {
            if(FinalDamage >= VirtualHealth) {
                configUtilStat2.HPChange(offender, (double) 0, (double) -1*(VirtualHealth));
                offender.setHealth(0);
                offender.setLastDamage(RealHealth);
                PlayerDieEvent playerDieEvent = new PlayerDieEvent(attacker, offender, FinalDamage, BaseType, StemType, RootType, Attribute);
                Bukkit.getPluginManager().callEvent(playerDieEvent);
                offender.sendMessage("대미지 " + FinalDamage);
                return 0;
            } else {
                configUtilStat2.HPChange(offender, (double)0, -1*(FinalDamage));
                offender.setLastDamage(FinalDamage);
                offender.sendMessage("대미지 " + FinalDamage);
                return RealDamageCal(FinalDamage, VirtualMaxHealth, RealMaxHealth);
            }
        } else {
            configUtilStat2.HPChange(offender, (double)0, (double) 0);
            HPControl.getInstance().HealthSetPlayer(offender);
            return 0;
        }
    }

    private boolean isProcessingPlainDamage = false;

    public void ETCDamageEVP(LivingEntity attacker, Player offender, double FinalDamage, String BaseType, String StemType, String RootType, String Attribute) {
        if(isProcessingPlainDamage) {
            return;
        }

        isProcessingPlainDamage = true;

        try {

            HPControl.getInstance().HealthSetPlayer(offender);

            double RealMaxHealth = offender.getHealthScale();
            double RealHealth = offender.getHealth();

            double VirtualMaxHealth = configUtilStat2.getMaxHP(offender);
            double VirtualHealth = configUtilStat2.getHP(offender);

            RPGDamageEvent event = new RPGDamageEvent(attacker, offender, FinalDamage, BaseType, StemType, RootType, Attribute, false);
            Bukkit.getPluginManager().callEvent(event);

            FinalDamage = playerDamageData.playerShiledPass(FinalDamage, offender);

            if(FinalDamage != 0) {
                if(FinalDamage >= VirtualHealth) {
                    configUtilStat2.HPChange(offender, (double) 0, (double) -1*(VirtualHealth));
                    offender.setLastDamage(VirtualHealth);
                    offender.setHealth(0);
                    PlayerDieEvent playerDieEvent = new PlayerDieEvent(attacker, offender, FinalDamage, BaseType, StemType, RootType, Attribute);
                    Bukkit.getPluginManager().callEvent(playerDieEvent);
                    offender.sendMessage("플레인히트1");
                    offender.sendMessage("대미지 " + FinalDamage);
                } else {
                    configUtilStat2.HPChange(offender, (double)0, -1*(FinalDamage));
                    offender.setLastDamage(FinalDamage);
                    offender.damage(RealDamageCal(FinalDamage, VirtualMaxHealth, RealMaxHealth), attacker);
                    offender.sendMessage("플레인히트2");
                    offender.sendMessage("대미지 " + FinalDamage);
                }
            } else {
                configUtilStat2.HPChange(offender, (double)0, (double) 0);
                HPControl.getInstance().HealthSetPlayer(offender);

            }
        } finally {
            isProcessingPlainDamage = false;
        }
    }


    private double RealDamageCal(double FinalDamage, double VMaxHP, double RMaxHP) {
        return ((FinalDamage)/(VMaxHP))*RMaxHP;
    }

}
