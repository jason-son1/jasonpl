package org.SJYPlugin.rPGBeta2.data.playerdata.damagedata;

import org.SJYPlugin.rPGBeta2.control.hpcontrol.ShieldControlPlayer;
import org.SJYPlugin.rPGBeta2.data.AttributeData;
import org.SJYPlugin.rPGBeta2.data.damage.DamageData;
import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
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


    public double playerShiledPass(DamageModifiers damageModifiers) {
        if(shieldControlPlayer.ExistShield(damageModifiers.getOffenderPlayer())) {
            double ShieldValue = shieldControlPlayer.GetShield(damageModifiers.getOffenderPlayer());
            if(damageModifiers.getFinalDamage() <= ShieldValue) {
                shieldControlPlayer.DownShield(damageModifiers.getOffenderPlayer(), damageModifiers.getFinalDamage());
                return 0;
            } else {
                shieldControlPlayer.DownShield(damageModifiers.getOffenderPlayer(), damageModifiers.getFinalDamage());
                return damageModifiers.getFinalDamage() - ShieldValue;
            }
        } else {
            return damageModifiers.getFinalDamage();
        }
    }



    public boolean OnCritical(DamageModifiers damageModifiers) {
        double Critical = configUtilStat2.getCriPer(damageModifiers.getAttackerPlayer());
        return random.nextDouble() < (Critical / 100);
    }

    public double CriticalDamage(DamageModifiers damageModifiers) {
        return (configUtilStat2.getCriDam(damageModifiers.getAttackerPlayer()))/100;
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


    public double PlayerFinalDef(DamageModifiers damageModifiers) {
        if(damageModifiers.getOffender() instanceof Player) {
            double Def = configUtilStat2.getDef(damageModifiers.getOffenderPlayer());
            double IgnDef = configUtilStat2.getIgnDef(damageModifiers.getAttackerPlayer());
            return Def - Def*(IgnDef/100);
        } else {
            double Def = mobData.getMobDef(damageModifiers.getOffender());
            double IgnDef = configUtilStat2.getIgnDef(damageModifiers.getAttackerPlayer());
            return Def - Def*(IgnDef/100);
        }
    }


    private final double finalDefMagCONSTANT = 1.0;

    public double PlayerDefMag(DamageModifiers damageModifiers, double finaldef) {
        double FinaldamageValue = PlayerBaseDamageValue(damageModifiers);
        double FinalDef = finaldef;
        if(FinalDef < FinaldamageValue) {
            double ex = Math.exp(-1*finalDefMagCONSTANT*(FinaldamageValue-FinalDef + Math.log(1.2)));
            return 1-ex;
        }
        else{
            return 0.2;
        }
    }

    public double PlayerBaseDamageValue(DamageModifiers damageModifiers) {
        if(damageData.getDamgeTypeSet("DamageBaseType").contains(damageModifiers.getBaseType())) {
            if(damageModifiers.getBaseType().equalsIgnoreCase("ATTACK")) {
                return configUtilStat2.getAtc(damageModifiers.getAttackerPlayer())*damageData.BaseCorrectionCONSTANT_Attack;
            } else if(damageModifiers.getBaseType().equalsIgnoreCase("DEF")) {
                return configUtilStat2.getDef(damageModifiers.getAttackerPlayer())*damageData.BaseCorrectionCONSTANT_Def;
            } else if(damageModifiers.getBaseType().equalsIgnoreCase("MAXHP")) {
                return configUtilStat2.getMaxHP(damageModifiers.getAttackerPlayer())*damageData.BaseCorrectionCONSTANT_HP;
            } else if(damageModifiers.getBaseType().equalsIgnoreCase("ETC")) {
                return 0;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int PlayerAttUp(DamageModifiers damageModifiers) {
        if(attributeData.getAttList().contains(damageModifiers.getAttribute())) {
            return configUtilStat3.getAttUp(damageModifiers.getAttackerPlayer()).getOrDefault(damageModifiers.getAttribute(), 0);
        } else {
            return 0;
        }
    }

    public int PlayerAttResist(DamageModifiers damageModifiers) {
        if(attributeData.getAttList().contains(damageModifiers.getAttribute())) {
            return configUtilStat3.getAttResist(damageModifiers.getOffenderPlayer()).getOrDefault(damageModifiers.getAttribute(), 0);
        } else {
            return 0;
        }
    }

    public double DamageTypeMag(DamageModifiers damageModifiers) {

        Map<String, Integer> RootBuff = playerRootDamageBuffData.getBuffData(damageModifiers.getAttackerPlayer());
        Map<String, Integer> StemBuff = playerStemDamageBuffData.getBuffData(damageModifiers.getAttackerPlayer());

        int RootBuffValue = RootBuff.getOrDefault(damageModifiers.getRootType(), 0);
        int StemBuffValue = StemBuff.getOrDefault(damageModifiers.getStemType(), 0);

        return (1 + (double) StemBuffValue/100)*(1 + (double) RootBuffValue/100);
    }

    public double MobTypeMag(DamageModifiers damageModifiers, String MobType) {

        Map<String, Integer> MobBuff = playerMobTypeDamageBuffData.getBuffData(damageModifiers.getAttackerPlayer());

        int MobBuffValue = MobBuff.getOrDefault(MobType, 0);

        return (1 + (double) MobBuffValue/100);
    }



}
