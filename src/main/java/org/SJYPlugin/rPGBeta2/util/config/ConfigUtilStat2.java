package org.SJYPlugin.rPGBeta2.util.config;

import org.SJYPlugin.rPGBeta2.customevents.player.PlayerChangeHPEvent;
import org.SJYPlugin.rPGBeta2.data.ClassData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ConfigUtilStat2 {

    ClassData classData = new ClassData();

    ConfigUtilStat configUtilStat = new ConfigUtilStat();

    private static final ConfigUtilStat2 instance = new ConfigUtilStat2();

    public static ConfigUtilStat2 getInstance() {
        return instance;
    }

    //변화 구문 시작

    public void ClassChange(Player player, String classname) {
        List<String> classes = classData.ClassList();
        if(classes.contains(classname)) {
            configUtilStat.StringSetting(player, "Player-static-class", classname);
        }
    }

    public void LevelChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrLevel = configUtilStat.getInt(player, "Player-highstat-Level");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "Player-highstat-Level", setvalue);
        }
        else {
            configUtilStat.IntSetting(player, "Player-highstat-Level", CurrLevel + changevalue);
        }
    }


    public void MaxHPChange(Player player, Double setvalue, Double changevalue) {
        Double CurrMaxHP = configUtilStat.getDouble(player, "PlayerStat-highstat-MaxHP");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-highstat-MaxHP", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-highstat-MaxHP", CurrMaxHP + changevalue);
        }
    }

    public void MaxMPChange(Player player, Double setvalue, Double changevalue) {
        Double CurrMaxMP = configUtilStat.getDouble(player, "PlayerStat-highstat-MaxMP");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-highstat-MaxMP", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-highstat-MaxMP", CurrMaxMP + changevalue);
        }
    }

    public void AtcChange(Player player, Double setvalue, Double changevalue) {
        Double CurrAtc = configUtilStat.getDouble(player, "PlayerStat-highstat-Atc");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-highstat-Atc", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-highstat-Atc", CurrAtc + changevalue);
        }
    }

    public void AttMasteryChange(Player player, Integer setvalue, Integer changevalue) {
        int CurrAttMastery = configUtilStat.getInt(player, "PlayerStat-highstat-AttMastery");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-highstat-AttMastery", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-highstat-AttMastery", CurrAttMastery + changevalue);
        }
    }

    public void ExpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrExp = configUtilStat.getInt(player, "Player-runstat-Exp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "Player-runstat-Exp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "Player-runstat-Exp", CurrExp + changevalue);
        }
    }

    public void MaxExpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrMaxExp = configUtilStat.getInt(player, "Player-runstat-MaxExp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "Player-runstat-MaxExp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "Player-runstat-MaxExp", CurrMaxExp + changevalue);
        }
    }

    public void StatPointChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrStatPoint = configUtilStat.getInt(player, "Player-runstat-StatPoint");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "Player-runstat-StatPoint", setvalue);
        } else {
            configUtilStat.IntSetting(player, "Player-runstat-StatPoint", CurrStatPoint + changevalue);
        }
    }

    public void StatPointUsedChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrStatPointUsed = configUtilStat.getInt(player, "Player-runstat-StatPointUsed");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "Player-runstat-StatPointUsed", setvalue);
        } else {
            configUtilStat.IntSetting(player, "Player-runstat-StatPointUsed", CurrStatPointUsed + changevalue);
        }
    }

    public void HPChange(Player player, Double setvalue, Double changevalue) {
        Double CurrHP = configUtilStat.getDouble(player, "PlayerStat-runstat-HP");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-HP", setvalue);
            PlayerChangeHPEvent event = new PlayerChangeHPEvent(player, CurrHP, setvalue, getMaxHP(player), "SETTING");
            Bukkit.getPluginManager().callEvent(event);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-HP", CurrHP + changevalue);
            PlayerChangeHPEvent event = new PlayerChangeHPEvent(player, CurrHP, changevalue, getMaxHP(player), "CHANGING");
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    public void MPChange(Player player, Double setvalue, Double changevalue) {
        Double CurrMP = configUtilStat.getDouble(player, "PlayerStat-runstat-MP");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-MP", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-MP", CurrMP + changevalue);
        }
    }

    public void ShilledChange(Player player, Double setvalue, Double changevalue) {
        Double CurrShilled = configUtilStat.getDouble(player, "PlayerStat-runstat-Shilled");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-Shilled", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-Shilled", CurrShilled + changevalue);
        }
    }

    public void BeginAtcChange(Player player, Double setvalue, Double changevalue) {
        Double CurrBeginAtc = configUtilStat.getDouble(player, "PlayerStat-runstat-beginAtc");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-beginAtc", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-beginAtc", CurrBeginAtc + changevalue);
        }
    }

    public void AddAtcChange(Player player, Double setvalue, Double changevalue) {
        Double CurrAddAtc = configUtilStat.getDouble(player, "PlayerStat-runstat-addAtc");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-addAtc", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-runstat-addAtc", CurrAddAtc + changevalue);
        }
    }


    public void StrChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrStr = configUtilStat.getInt(player, "PlayerStat-lowstat-Str");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Str", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Str", CurrStr + changevalue);
        }
    }

    public void DexChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrDex = configUtilStat.getInt(player, "PlayerStat-lowstat-Dex");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Dex", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Dex", CurrDex + changevalue);
        }
    }

    public void IntChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrInt = configUtilStat.getInt(player, "PlayerStat-lowstat-Int");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Int", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Int", CurrInt + changevalue);
        }
    }

    public void DefChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrDef = configUtilStat.getInt(player, "PlayerStat-lowstat-Def");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Def", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Def", CurrDef + changevalue);
        }
    }

    public void LucChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrLuc = configUtilStat.getInt(player, "PlayerStat-lowstat-Luc");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Luc", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-lowstat-Luc", CurrLuc + changevalue);
        }
    }

    public void CriPerChange(Player player, Double setvalue, Double changevalue) {
        Double CurrCriPer = configUtilStat.getDouble(player, "PlayerStat-lowstat-CriPer");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-CriPer", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-CriPer", CurrCriPer + changevalue);
        }
    }

    public void CriDamChange(Player player, Double setvalue, Double changevalue) {
        Double CurrCriDam = configUtilStat.getDouble(player, "PlayerStat-lowstat-CriDam");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-CriDam", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-CriDam", CurrCriDam + changevalue);
        }
    }

    public void AtcSpdChange(Player player, Double setvalue, Double changevalue) {
        Double CurrAtcSpd = configUtilStat.getDouble(player, "PlayerStat-lowstat-AtcSpd");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-AtcSpd", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-AtcSpd", CurrAtcSpd + changevalue);
        }
    }

    public void MoveSpdChange(Player player, Double setvalue, Double changevalue) {
        Double CurrMoveSpd = configUtilStat.getDouble(player, "PlayerStat-lowstat-MoveSpd");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-MoveSpd", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-MoveSpd", CurrMoveSpd + changevalue);
        }
    }

    public void IgnDefChange(Player player, Double setvalue, Double changevalue) {
        Double CurrIgnDef = configUtilStat.getDouble(player, "PlayerStat-lowstat-IgnDef");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-IgnDef", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-lowstat-IgnDef", CurrIgnDef + changevalue);
        }
    }

    public void FireAttUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrFireAttUp = configUtilStat.getInt(player, "PlayerStat-attrstat-FireAttUp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-FireAttUp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-FireAttUp", CurrFireAttUp + changevalue);
        }
    }

    public void WaterAttUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrWaterAttUp = configUtilStat.getInt(player, "PlayerStat-attrstat-WaterAttUp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-WaterAttUp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-WaterAttUp", CurrWaterAttUp + changevalue);
        }
    }

    public void EarthAttUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrEarthAttUp = configUtilStat.getInt(player, "PlayerStat-attrstat-EarthAttUp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-EarthAttUp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-EarthAttUp", CurrEarthAttUp + changevalue);
        }
    }

    public void WindAttUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrWindAttUp = configUtilStat.getInt(player, "PlayerStat-attrstat-WindAttUp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-WindAttUp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-WindAttUp", CurrWindAttUp + changevalue);
        }
    }

    public void LightAttUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrLightAttUp = configUtilStat.getInt(player, "PlayerStat-attrstat-LightAttUp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-LightAttUp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-LightAttUp", CurrLightAttUp + changevalue);
        }
    }

    public void DarkAttUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrDarkAttUp = configUtilStat.getInt(player, "PlayerStat-attrstat-DarkAttUp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-DarkAttUp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-DarkAttUp", CurrDarkAttUp + changevalue);
        }
    }

    public void PhysicsAttUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrPhysicsAttUp = configUtilStat.getInt(player, "PlayerStat-attrstat-PhysicsAttUp");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-PhysicsAttUp", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-PhysicsAttUp", CurrPhysicsAttUp + changevalue);
        }
    }

    public void FireAttResistUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrFireAttResist = configUtilStat.getInt(player, "PlayerStat-attrstat-FireAttResist");
        if(setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-FireAttResist", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-FireAttResist", CurrFireAttResist + changevalue);
        }
    }

    public void WaterAttResistUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrWaterAttResist = configUtilStat.getInt(player, "PlayerStat-attrstat-WaterAttResist");
        if (setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-WaterAttResist", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-WaterAttResist", CurrWaterAttResist + changevalue);
        }
    }

    public void EarthAttResistUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrEarthAttResist = configUtilStat.getInt(player, "PlayerStat-attrstat-EarthAttResist");
        if (setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-EarthAttResist", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-EarthAttResist", CurrEarthAttResist + changevalue);
        }
    }

    public void WindAttResistUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrWindAttResist = configUtilStat.getInt(player, "PlayerStat-attrstat-WindAttResist");
        if (setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-WindAttResist", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-WindAttResist", CurrWindAttResist + changevalue);
        }
    }

    public void LightAttResistUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrLightAttResist = configUtilStat.getInt(player, "PlayerStat-attrstat-LightAttResist");
        if (setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-LightAttResist", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-LightAttResist", CurrLightAttResist + changevalue);
        }
    }

    public void DarkAttResistUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrDarkAttResist = configUtilStat.getInt(player, "PlayerStat-attrstat-DarkAttResist");
        if (setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-DarkAttResist", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-DarkAttResist", CurrDarkAttResist + changevalue);
        }
    }

    public void PhysicsAttResistUpChange(Player player, Integer setvalue, Integer changevalue) {
        Integer CurrPhysicsAttResist = configUtilStat.getInt(player, "PlayerStat-attrstat-PhysicsAttResist");
        if (setvalue != 0) {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-PhysicsAttResist", setvalue);
        } else {
            configUtilStat.IntSetting(player, "PlayerStat-attrstat-PhysicsAttResist", CurrPhysicsAttResist + changevalue);
        }
    }

    public void MoneyChange(Player player, Double setvalue, Double changevalue) {
        Double CurrMoney = configUtilStat.getDouble(player, "PlayerStat-Money-money");
        if(setvalue != 0) {
            configUtilStat.DoubleSetting(player, "PlayerStat-Money-money", setvalue);
        } else {
            configUtilStat.DoubleSetting(player, "PlayerStat-Money-money", CurrMoney + changevalue);
        }
    }
    //변화 구문 끝


    //가져오기 시작
    public String ClassType(Player player) {
        return configUtilStat.getString(player, "Player-static-class");
    }

    public Integer getLevel(Player player) {
        return configUtilStat.getInt(player, "Player-highstat-Level");
    }

    public Double getMaxHP(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-highstat-MaxHP");
    }

    public Double getMaxMP(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-highstat-MaxMP");
    }

    public Double getAtc(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-highstat-Atc");
    }

    public Integer getAttMastery(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-highstat-AttMastery");
    }

    public Integer getExp(Player player) {
        return configUtilStat.getInt(player, "Player-runstat-Exp");
    }

    public Integer getMaxExp(Player player) {
        return configUtilStat.getInt(player, "Player-runstat-MaxExp");
    }

    public Integer getStatPoint(Player player) {
        return configUtilStat.getInt(player, "Player-runstat-StatPoint");
    }

    public Integer getStatPointUsed(Player player) {
        return configUtilStat.getInt(player, "Player-runstat-StatPointUsed");
    }

    public Double getHP(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-runstat-HP");
    }

    public Double getMP(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-runstat-MP");
    }

    public Double getShilled(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-runstat-Shilled");
    }

    public Double getBeginAtc(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-runstat-beginAtc");
    }

    public Double getAddAtc(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-runstat-addAtc");
    }

    public Integer getStr(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-lowstat-Str");
    }

    public Integer getDex(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-lowstat-Dex");
    }

    public Integer getInt(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-lowstat-Int");
    }

    public Integer getDef(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-lowstat-Def");
    }

    public Integer getLuc(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-lowstat-Luc");
    }

    public Double getCriPer(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-lowstat-CriPer");
    }

    public Double getCriDam(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-lowstat-CriDam");
    }

    public Double getAtcSpd(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-lowstat-AtcSpd");
    }

    public Double getMoveSpd(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-lowstat-MoveSpd");
    }

    public Double getIgnDef(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-lowstat-IgnDef");
    }

    public Integer getFireAttUp(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-FireAttUp");
    }

    public Integer getWaterAttUp(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-WaterAttUp");
    }

    public Integer getEarthAttUp(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-EarthAttUp");
    }

    public Integer getWindAttUp(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-WindAttUp");
    }

    public Integer getLightAttUp(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-LightAttUp");
    }

    public Integer getDarkAttUp(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-DarkAttUp");
    }

    public Integer getPhysicsAttUp(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-PhysicsAttUp");
    }

    public Integer getFireAttResist(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-FireAttResist");
    }

    public Integer getWaterAttResist(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-WaterAttResist");
    }

    public Integer getEarthAttResist(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-EarthAttResist");
    }

    public Integer getWindAttResist(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-WindAttResist");
    }

    public Integer getLightAttResist(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-LightAttResist");
    }

    public Integer getDarkAttResist(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-DarkAttResist");
    }

    public Integer getPhysicsAttResist(Player player) {
        return configUtilStat.getInt(player, "PlayerStat-attrstat-PhysicsAttResist");
    }

    public Double getMoney(Player player) {
        return configUtilStat.getDouble(player, "PlayerStat-Money-money");
    }
    //가져오기 끝





}
