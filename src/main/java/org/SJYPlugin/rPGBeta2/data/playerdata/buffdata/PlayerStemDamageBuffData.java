package org.SJYPlugin.rPGBeta2.data.playerdata.buffdata;

import org.SJYPlugin.rPGBeta2.data.DamageData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStemDamageBuffData {

    private static final PlayerStemDamageBuffData instance = new PlayerStemDamageBuffData();

    public static PlayerStemDamageBuffData getInstance() {
        return instance;
    }

    DamageData damageData = DamageData.getInstance();

    private final Map<UUID, Map<String, Integer>> playerStemDamageBuffData = new HashMap<>();

    public void setBuffData(Player player, String damageType, int value) {
        defaultBuffSet(player);
        if(damageData.getDamgeTypeSet("DamageStemType").contains(damageType)) {
            playerStemDamageBuffData.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(damageType, value);
        }
    }

    public Map<String, Integer> getBuffData(Player player) {
        return playerStemDamageBuffData.getOrDefault(player.getUniqueId(), defaultBuffSet(player));
    }

    public Map<String, Integer> defaultBuffSet(Player player) {
        Map<String, Integer> map;
        if(playerStemDamageBuffData == null || playerStemDamageBuffData.get(player.getUniqueId()) == null ||
                playerStemDamageBuffData.get(player.getUniqueId()).isEmpty()) {
            playerStemDamageBuffData.put(player.getUniqueId(), new HashMap<>());
            for (String type : damageData.getDamgeTypeSet("DamageStemType")) {
                playerStemDamageBuffData.get(player.getUniqueId()).put(type, 0);
            }
            map = playerStemDamageBuffData.get(player.getUniqueId());
            return map;
        }
        return playerStemDamageBuffData.get(player.getUniqueId());
    }

    public void ControlBuffData(Player player, String damageType, int changevalue) {
        defaultBuffSet(player);
        int PreValue = playerStemDamageBuffData.get(player.getUniqueId()).get(damageType);
        setBuffData(player, damageType, PreValue + changevalue);
    }

    public int getBuffData(Player player, String damageType) {
        return playerStemDamageBuffData.get(player.getUniqueId()).get(damageType);
    }




}
