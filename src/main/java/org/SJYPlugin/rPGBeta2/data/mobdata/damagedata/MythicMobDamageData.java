package org.SJYPlugin.rPGBeta2.data.mobdata.damagedata;

import io.lumine.mythic.api.mobs.MythicMob;
import org.SJYPlugin.rPGBeta2.control.hpcontrol.ShieldControlMob;
import org.SJYPlugin.rPGBeta2.data.AttributeData;
import org.SJYPlugin.rPGBeta2.data.damage.DamageData;
import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
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



    public double MobShiledPass(DamageModifiers damageModifiers) {
        if(shieldControlMob.ExistShield(damageModifiers.getOffender())) {
            double ShieldValue = shieldControlMob.GetShield(damageModifiers.getOffender());
            if(damageModifiers.getFinalDamage() <= ShieldValue) {
                shieldControlMob.DownShield(damageModifiers.getOffender(), damageModifiers.getFinalDamage());
                return 0;
            } else {
                shieldControlMob.DownShield(damageModifiers.getOffender(), damageModifiers.getFinalDamage());
                return damageModifiers.getFinalDamage() - ShieldValue;
            }
        } else {
            return damageModifiers.getFinalDamage();
        }
    }

    private final double finalDefMagCONSTANT = 1.0;

    public double MobDefMag(DamageModifiers damageModifiers, double finaldef) {
        double FinaldamageValue = MobBaseDamageValue(damageModifiers);
        double FinalDef = finaldef;
        if(FinalDef < FinaldamageValue) {
            double ex = Math.exp(-1*finalDefMagCONSTANT*(FinaldamageValue-FinalDef + Math.log(1.2)));
            return 1-ex;
        }
        else{
            return 0.2;
        }
    }

    public double MobFinalDef(DamageModifiers damageModifiers) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(damageModifiers.getAttacker());
        if(mythicMob != null) {
            double Def = configUtilStat2.getDef(damageModifiers.getOffenderPlayer());
            double IgnDef = mobData.getMobDefIgn(damageModifiers.getAttacker());
            return Def - Def*IgnDef;
        } else {
            return 0;
        }
    }

    public double DamageTypeMag(DamageModifiers damageModifiers) {

        Map<String, Integer> RootBuff = mythicMobRootDamageBuffData.getBuffData(damageModifiers.getAttacker());
        Map<String, Integer> StemBuff = mythicMobStemDamageBuffData.getBuffData(damageModifiers.getAttacker());

        int RootBuffValue = RootBuff.getOrDefault(damageModifiers.getRootType(), 0);
        int StemBuffValue = StemBuff.getOrDefault(damageModifiers.getStemType(), 0);

        return (1 + (double) StemBuffValue/100)*(1 + (double) RootBuffValue/100);
    }


    public int MobAttUp(DamageModifiers damageModifiers) {
        if(attributeData.getAttList().contains(damageModifiers.getAttribute())) {
            MythicMob mythicMob = mobDataControlGet.MythicMobCheck(damageModifiers.getAttacker());
            if(mythicMob != null) {
                Map<String, Integer> AttUp = mobData.getMobAttUp(damageModifiers.getAttacker());
                if(AttUp != null) {
                    if(AttUp.containsKey(damageModifiers.getAttribute())) {
                        return AttUp.get(damageModifiers.getAttribute());
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

    public double MobBaseDamageValue(DamageModifiers damageModifiers) {
        if(damageData.getDamgeTypeSet("DamageBaseType").contains(damageModifiers.getBaseType())) {
            if(damageModifiers.getBaseType().equalsIgnoreCase("ATTACK")) {
                return mobData.getMobAttack(damageModifiers.getAttacker())*damageData.BaseCorrectionCONSTANT_Attack;
            } else if(damageModifiers.getBaseType().equalsIgnoreCase("DEF")) {
                return mobData.getMobDef(damageModifiers.getAttacker())*damageData.BaseCorrectionCONSTANT_Def;
            } else if(damageModifiers.getBaseType().equalsIgnoreCase("MAXHP")) {
                return mobData.getMobMaxHealth(damageModifiers.getAttacker())*damageData.BaseCorrectionCONSTANT_HP;
            } else if(damageModifiers.getBaseType().equalsIgnoreCase("ETC")) {
                return 0;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int MobAttResist(DamageModifiers damageModifiers) {
        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(damageModifiers.getOffender());
        if(mythicMob != null) {
            Map<String, Integer> AttResist = mobData.getMobResistAtt(damageModifiers.getOffender());
            if(AttResist != null) {
                if(AttResist.containsKey(damageModifiers.getAttribute())) {
                    return AttResist.get(damageModifiers.getAttribute());
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
