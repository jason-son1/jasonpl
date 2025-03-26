package org.SJYPlugin.rPGBeta2.util.config;

import org.SJYPlugin.rPGBeta2.data.AttributeData;
import org.bukkit.entity.Player;

import java.util.*;

public class ConfigUtilStat3 {

    private static final ConfigUtilStat3 instance = new ConfigUtilStat3();

    public static ConfigUtilStat3 getInstance() {
        return instance;
    }

    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();
    AttributeData attributeData = AttributeData.getInstance();

    public Map<String, Integer> getAttUp(Player player) {
        Map<String, Integer> AttUp = new HashMap<>();
        AttUp.put(attributeData.getAttFire(), configUtilStat2.getFireAttUp(player));
        AttUp.put(attributeData.getAttWater(), configUtilStat2.getWaterAttUp(player));
        AttUp.put(attributeData.getAttEarth(), configUtilStat2.getEarthAttUp(player));
        AttUp.put(attributeData.getAttWind(), configUtilStat2.getWindAttUp(player));
        AttUp.put(attributeData.getAttLight(), configUtilStat2.getLightAttUp(player));
        AttUp.put(attributeData.getAttDark(), configUtilStat2.getDarkAttUp(player));
        AttUp.put(attributeData.getAttPhy(), configUtilStat2.getPhysicsAttUp(player));
        return AttUp;
    }

    public Map<String, Integer> getAttResist(Player player) {
        Map<String, Integer> AttResist = new HashMap<>();
        AttResist.put(attributeData.getAttFire(), configUtilStat2.getFireAttResist(player));
        AttResist.put(attributeData.getAttWater(), configUtilStat2.getWaterAttResist(player));
        AttResist.put(attributeData.getAttEarth(), configUtilStat2.getEarthAttResist(player));
        AttResist.put(attributeData.getAttWind(), configUtilStat2.getWindAttResist(player));
        AttResist.put(attributeData.getAttLight(), configUtilStat2.getLightAttResist(player));
        AttResist.put(attributeData.getAttDark(), configUtilStat2.getDarkAttResist(player));
        AttResist.put(attributeData.getAttPhy(), configUtilStat2.getPhysicsAttResist(player));
        return AttResist;
    }

}
