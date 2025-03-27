package org.SJYPlugin.rPGBeta2.data.mobdata.damagedata;

import io.lumine.mythic.api.mobs.MythicMob;
import org.SJYPlugin.rPGBeta2.control.hpcontrol.ShieldControlMob;
import org.SJYPlugin.rPGBeta2.data.AttributeData;
import org.SJYPlugin.rPGBeta2.data.DamageData;
import org.SJYPlugin.rPGBeta2.data.mobdata.buffdata.MythicMobRootDamageBuffData;
import org.SJYPlugin.rPGBeta2.data.mobdata.buffdata.MythicMobStemDamageBuffData;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.control.MobDataControlGet;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata.MobData;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public class MythicMobDamageData {

    private static final MythicMobDamageData instance = new MythicMobDamageData();

    public static MythicMobDamageData getInstance() {
        return instance;
    }

    MobDataControlGet mobDataControlGet = MobDataControlGet.getInstance();
    MobData mobData = MobData.getInstance();
    DamageData damageData = DamageData.getInstance();
    MythicMobStemDamageBuffData mythicMobStemDamageBuffData = MythicMobStemDamageBuffData.getInstance();
    MythicMobRootDamageBuffData mythicMobRootDamageBuffData = MythicMobRootDamageBuffData.getInstance();
    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();
    ShieldControlMob shieldControlMob = ShieldControlMob.getInstance();
    AttributeData attributeData = AttributeData.getInstance();



    public double MobShiledPass(double finaldamage, LivingEntity offender) {
        if(shieldControlMob.ExistShield(offender)) {
            double ShieldValue = shieldControlMob.GetShield(offender);
            if(finaldamage <= ShieldValue) {
                shieldControlMob.DownShield(offender, finaldamage);
                return 0;
            } else {
                shieldControlMob.DownShield(offender, finaldamage);
                return finaldamage - ShieldValue;
            }
        } else {
            return finaldamage;
        }
    }

    private final double finalDefMagCONSTANT = 1.0;

    public double MobDefMag(LivingEntity attacker, double finaldef, String DamageBaseType) {
        double FinaldamageValue = MobBaseDamageValue(attacker, DamageBaseType);
        double FinalDef = finaldef;
        if(FinalDef < FinaldamageValue) {
            double ex = Math.exp(-1*finalDefMagCONSTANT*(FinaldamageValue-FinalDef + Math.log(1.2)));
            return 1-ex;
        }
        else{
            return 0.2;
        }
    }

    public double MobFinalDef(LivingEntity attacker, Player offender) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(attacker);
        if(mythicMob != null) {
            double Def = configUtilStat2.getDef(offender);
            double IgnDef = mobData.getMobDefIgn(attacker);
            return Def - Def*IgnDef;
        } else {
            return 0;
        }
    }

    public double DamageTypeMag(LivingEntity attacker, String RootDamageType, String StemDamageType) {

        Map<String, Integer> RootBuff = mythicMobRootDamageBuffData.getBuffData(attacker);
        Map<String, Integer> StemBuff = mythicMobStemDamageBuffData.getBuffData(attacker);

        int RootBuffValue = RootBuff.getOrDefault(RootDamageType, 0);
        int StemBuffValue = StemBuff.getOrDefault(StemDamageType, 0);

        return (1 + (double) StemBuffValue/100)*(1 + (double) RootBuffValue/100);
    }


    public int MobAttUp(LivingEntity attacker, String Attribute) {
        Set<String> AttList = attributeData.getAttList();
        if(AttList.contains(Attribute)) {
            MythicMob mythicMob = mobDataControlGet.MythicMobCheck(attacker);
            if(mythicMob != null) {
                Map<String, Integer> AttUp = mobData.getMobAttUp(attacker);
                if(AttUp != null) {
                    if(AttUp.containsKey(Attribute)) {
                        return AttUp.get(Attribute);
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public double MobBaseDamageValue(LivingEntity attacker, String DamageBaseType) {
        Set<String> DamageBaseSet = damageData.getDamgeTypeSet("DamageBaseType");
        if(DamageBaseSet.contains(DamageBaseType)) {

            if(DamageBaseType.equalsIgnoreCase("ATTACK")) {
                return mobData.getMobAttack(attacker)*damageData.BaseCorrectionCONSTANT_Attack;
            } else if(DamageBaseType.equalsIgnoreCase("DEF")) {
                return mobData.getMobDef(attacker)*damageData.BaseCorrectionCONSTANT_Def;
            } else if(DamageBaseType.equalsIgnoreCase("MAXHP")) {
                return mobData.getMobMaxHealth(attacker)*damageData.BaseCorrectionCONSTANT_HP;
            } else if(DamageBaseType.equalsIgnoreCase("ETC")) {
                return 0;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int MobAttResist(LivingEntity entity, String Attribute) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(entity);
        if(mythicMob != null) {
            Map<String, Integer> AttResist = mobData.getMobResistAtt(entity);
            if(AttResist != null) {
                if(AttResist.containsKey(Attribute)) {
                    return AttResist.get(Attribute);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

}
