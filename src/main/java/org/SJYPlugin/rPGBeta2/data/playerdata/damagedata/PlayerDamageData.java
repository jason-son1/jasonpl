package org.SJYPlugin.rPGBeta2.data.playerdata.damagedata;

import org.SJYPlugin.rPGBeta2.control.hpcontrol.ShieldControlPlayer;
import org.SJYPlugin.rPGBeta2.data.AttributeData;
import org.SJYPlugin.rPGBeta2.data.DamageData;
import org.SJYPlugin.rPGBeta2.data.mobdata.damagedata.MythicMobDamageData;
import org.SJYPlugin.rPGBeta2.data.playerdata.buffdata.PlayerMobTypeDamageBuffData;
import org.SJYPlugin.rPGBeta2.data.playerdata.buffdata.PlayerRootDamageBuffData;
import org.SJYPlugin.rPGBeta2.data.playerdata.buffdata.PlayerStemDamageBuffData;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata.MobData;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat3;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PlayerDamageData {

    private static final PlayerDamageData instance = new PlayerDamageData();

    public static PlayerDamageData getInstance() {
        return instance;
    }


    DamageData damageData = DamageData.getInstance();
    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();
    ConfigUtilStat3 configUtilStat3 = ConfigUtilStat3.getInstance();
    PlayerRootDamageBuffData playerRootDamageBuffData = PlayerRootDamageBuffData.getInstance();
    PlayerStemDamageBuffData playerStemDamageBuffData = PlayerStemDamageBuffData.getInstance();
    ShieldControlPlayer shieldControlPlayer = ShieldControlPlayer.getInstance();
    MobData mobData = MobData.getInstance();
    PlayerMobTypeDamageBuffData playerMobTypeDamageBuffData = PlayerMobTypeDamageBuffData.getInstance();
    AttributeData attributeData = AttributeData.getInstance();
    private final Random random = new Random();


    private final double attributeDamageCONSTANT = 1.0;
    private final double attributeResistCONSTANT = 1.0;


    public double playerShiledPass(double finaldamage, Player offender) {
        if(shieldControlPlayer.ExistShield(offender)) {
            double ShieldValue = shieldControlPlayer.GetShield(offender);
            if(finaldamage <= ShieldValue) {
                shieldControlPlayer.DownShield(offender, finaldamage);
                return 0;
            } else {
                shieldControlPlayer.DownShield(offender, finaldamage);
                return finaldamage - ShieldValue;
            }
        } else {
            return finaldamage;
        }
    }



    public boolean OnCritical(Player attacker) {
        double Critical = configUtilStat2.getCriPer(attacker);
        return random.nextDouble() < (Critical / 100);
    }

    public double CriticalDamage(Player attacker) {
        return (configUtilStat2.getCriDam(attacker))/100;
    }

    public double AttDamageMagControl(Integer attUp, Integer attRes) {
        if(attUp != 0 && attRes != 0) {
            double dc = (1-1/(attributeDamageCONSTANT*attUp));
            double rc = (1-1/(attributeResistCONSTANT*attRes));
            if (dc <= rc) {
                return 0;
            }
            else{
                return dc - rc;
            }
        } else {
            return 0;
        }
    }

    public double PlayerFinalDef(Player attacker, Player offender) {
        double Def = configUtilStat2.getDef(offender);
        double IgnDef = configUtilStat2.getIgnDef(attacker);
        return Def - Def*(IgnDef/100);
    }

    public double PlayerFinalDef(Player attacker, LivingEntity offender) {
        double Def = mobData.getMobDef(offender);
        double IgnDef = configUtilStat2.getIgnDef(attacker);
        return Def - Def*(IgnDef/100);
    }


    private final double finalDefMagCONSTANT = 1.0;

    public double PlayerDefMag(Player attacker, double finaldef, String DamageBaseType) {
        double FinaldamageValue = PlayerBaseDamageValue(attacker, DamageBaseType);
        double FinalDef = finaldef;
        if(FinalDef < FinaldamageValue) {
            double ex = Math.exp(-1*finalDefMagCONSTANT*(FinaldamageValue-FinalDef + Math.log(1.2)));
            return 1-ex;
        }
        else{
            return 0.2;
        }
    }

    public double PlayerBaseDamageValue(Player attacker, String DamageBaseType) {
        Set<String> DamageBaseSet = damageData.getDamgeTypeSet("DamageBaseType");
        if(DamageBaseSet.contains(DamageBaseType)) {

            if(DamageBaseType.equalsIgnoreCase("ATTACK")) {
                return configUtilStat2.getAtc(attacker)*damageData.BaseCorrectionCONSTANT_Attack;
            } else if(DamageBaseType.equalsIgnoreCase("DEF")) {
                return configUtilStat2.getDef(attacker)*damageData.BaseCorrectionCONSTANT_Def;
            } else if(DamageBaseType.equalsIgnoreCase("MAXHP")) {
                return configUtilStat2.getMaxHP(attacker)*damageData.BaseCorrectionCONSTANT_HP;
            } else if(DamageBaseType.equalsIgnoreCase("ETC")) {
                return 0;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int PlayerAttUp(Player attacker, String Attribute) {
        Set<String> AttList = attributeData.getAttList();
        if(AttList.contains(Attribute)) {
            return configUtilStat3.getAttUp(attacker).getOrDefault(Attribute, 0);
        } else {
            return 0;
        }
    }


    public int PlayerAttResist(Player attacker, String Attribute) {
        Set<String> AttList = attributeData.getAttList();
        if(AttList.contains(Attribute)) {
            return configUtilStat3.getAttResist(attacker).getOrDefault(Attribute, 0);
        } else {
            return 0;
        }
    }

    public double DamageTypeMag(Player attacker, String RootDamageType, String StemDamageType) {

        Map<String, Integer> RootBuff = playerRootDamageBuffData.getBuffData(attacker);
        Map<String, Integer> StemBuff = playerStemDamageBuffData.getBuffData(attacker);

        int RootBuffValue = RootBuff.getOrDefault(RootDamageType, 0);
        int StemBuffValue = StemBuff.getOrDefault(StemDamageType, 0);

        return (1 + (double) StemBuffValue/100)*(1 + (double) RootBuffValue/100);
    }

    public double MobTypeMag(Player attacker, String MobType) {

        Map<String, Integer> MobBuff = playerMobTypeDamageBuffData.getBuffData(attacker);

        int MobBuffValue = MobBuff.getOrDefault(MobType, 0);

        return (1 + (double) MobBuffValue/100);
    }



}
