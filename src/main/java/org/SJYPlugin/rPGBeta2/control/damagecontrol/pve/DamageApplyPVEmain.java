package org.SJYPlugin.rPGBeta2.control.damagecontrol.pve;

import io.lumine.mythic.api.mobs.MythicMob;
import org.SJYPlugin.rPGBeta2.control.hpcontrol.HPControl;
import org.SJYPlugin.rPGBeta2.control.hpcontrol.MythicControl;
import org.SJYPlugin.rPGBeta2.customevents.damage.RPGDamageEvent;
import org.SJYPlugin.rPGBeta2.data.mobdata.damagedata.MythicMobDamageData;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.control.MobDataControlGet;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata.MobData;

import org.SJYPlugin.rPGBeta2.util.persistantdata.DamageCausePersistantData;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageApplyPVEmain {

    private static final DamageApplyPVEmain instance = new DamageApplyPVEmain();

    public static DamageApplyPVEmain getInstance() {
        return instance;
    }

    HPControl hpControl = HPControl.getInstance();
    MobDataControlGet mobDataControlGet = MobDataControlGet.getInstance();
    MythicControl mythicControl = new MythicControl();
    MobData mobData = MobData.getInstance();
    DamageCausePersistantData damageCausePersistantData = DamageCausePersistantData.getInstance();
    MythicMobDamageData mythicMobDamageData = MythicMobDamageData.getInstance();


    public void DamagePVE_Mythic(Player attacker, LivingEntity offender, double FinalDamage,
                                 String BaseType, String StemType, String RootType, String Attribute, boolean isCritical) {
        try {

            hpControl.HealthSetEntity_Mythic(offender);

            MythicMob mythicMob = mobDataControlGet.MythicMobCheck(offender);

            if(mythicMob != null) {

                double VirtualMaxHealth = mobData.getMobMaxHealth(offender);
                double VirtualHealth = mobData.getMobCurrHealth(offender);

                double RealMaxHealth = offender.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double RealCurrHealth = offender.getHealth();

                RPGDamageEvent event = new RPGDamageEvent(attacker, offender, FinalDamage, BaseType, StemType, RootType, Attribute, isCritical);
                Bukkit.getPluginManager().callEvent(event);

                FinalDamage = mythicMobDamageData.MobShiledPass(FinalDamage, offender);

                if(FinalDamage != 0) {
                    if(FinalDamage >= VirtualHealth) {
                        mythicControl.MythicMobChangeVCurrHealth(offender, -1*(VirtualHealth));
                        offender.setHealth(0);
                        offender.setKiller(attacker);
                        offender.setLastDamage(RealCurrHealth);
                    } else {
                        damageCausePersistantData.addData(attacker, "CustomDamageCause_PVE", "SPELL_DAMAGE");
                        mythicControl.MythicMobChangeVCurrHealth(offender, -1*(FinalDamage));
                        offender.damage(RealDamageCal(FinalDamage, VirtualMaxHealth, RealMaxHealth), attacker);
                        offender.setLastDamage(FinalDamage);
                    }
                } else {
                    mythicControl.MythicMobChangeVCurrHealth(offender, 0);
                    hpControl.HealthSetEntity_Mythic(offender);
                }
            } else {

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isProcessingPlainDamage = false;

    public void ETCDamagePVE(Player attacker, LivingEntity offender, double FinalDamage,
                             String BaseType, String StemType, String RootType, String Attribute, boolean isCritical) {
        if(isProcessingPlainDamage) {
            return;
        }

        isProcessingPlainDamage = true;

        try {

            hpControl.HealthSetEntity_Mythic(offender);

            MythicMob mythicMob = mobDataControlGet.MythicMobCheck(offender);

            if(mythicMob != null) {

                double VirtualMaxHealth = mobData.getMobMaxHealth(offender);
                double VirtualHealth = mobData.getMobCurrHealth(offender);

                double RealMaxHealth = offender.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double RealCurrHealth = offender.getHealth();

                RPGDamageEvent event = new RPGDamageEvent(attacker, offender, FinalDamage, BaseType, StemType, RootType, Attribute, isCritical);
                Bukkit.getPluginManager().callEvent(event);

                FinalDamage = mythicMobDamageData.MobShiledPass(FinalDamage, offender);

                if(FinalDamage != 0) {
                    if(FinalDamage >= VirtualHealth) {
                        mythicControl.MythicMobChangeVCurrHealth(offender, -1*(VirtualHealth));
                        offender.setHealth(0);
                        offender.setKiller(attacker);
                        offender.setLastDamage(RealCurrHealth);
                        attacker.sendMessage("플레인히트 1 FinalD  " + FinalDamage);
                        attacker.sendMessage("VirCurrHP " + VirtualHealth);
                        attacker.sendMessage("RealHP " + RealCurrHealth);
                    } else {
                        mythicControl.MythicMobChangeVCurrHealth(offender, -1*(FinalDamage));
                        offender.damage(RealDamageCal(FinalDamage, VirtualMaxHealth, RealMaxHealth), attacker);
                        offender.setLastDamage(FinalDamage);
                        attacker.sendMessage("플레인히트 2 FinalD  " + FinalDamage);
                        attacker.sendMessage("VirCurrHP " + VirtualHealth);
                        attacker.sendMessage("RealHP " + RealCurrHealth);
                    }
                } else {
                    mythicControl.MythicMobChangeVCurrHealth(offender, 0);
                    hpControl.HealthSetEntity_Mythic(offender);
                    attacker.sendMessage("플레인히트 3");
                }
            } else {
            }
        } finally {
            isProcessingPlainDamage = false;
        }

    }


    private double RealDamageCal(double FinalDamage, double VMaxHP, double RMaxHP) {
        return ((FinalDamage)/(VMaxHP))*RMaxHP;
    }


}
