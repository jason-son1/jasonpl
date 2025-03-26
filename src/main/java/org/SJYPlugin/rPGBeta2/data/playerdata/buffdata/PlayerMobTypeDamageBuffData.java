package org.SJYPlugin.rPGBeta2.data.playerdata.buffdata;

import org.SJYPlugin.rPGBeta2.data.MobTypeData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMobTypeDamageBuffData {

    private static final PlayerMobTypeDamageBuffData instance = new PlayerMobTypeDamageBuffData();

    public static PlayerMobTypeDamageBuffData getInstance() {
        return instance;
    }

    MobTypeData mobTypeData = MobTypeData.getInstance();

    private final Map<UUID, Map<String, Integer>> playerMobTypeDamageBuffData = new HashMap<>();

    public void setBuffData(Player player, String damageType, int value) {
        defaultBuffSet(player);
        if(mobTypeData.getMobTypeSet("MobType").contains(damageType)) {
            playerMobTypeDamageBuffData.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(damageType, value);
        }
    }

    public Map<String, Integer> getBuffData(Player player) {
        return playerMobTypeDamageBuffData.getOrDefault(player.getUniqueId(), defaultBuffSet(player));
    }

    public Map<String, Integer> defaultBuffSet(Player player) {
        Map<String, Integer> map;
        if(playerMobTypeDamageBuffData == null || playerMobTypeDamageBuffData.get(player.getUniqueId()) == null ||
                playerMobTypeDamageBuffData.get(player.getUniqueId()).isEmpty()) {
            playerMobTypeDamageBuffData.put(player.getUniqueId(), new HashMap<>());
            for (String type : mobTypeData.getMobTypeSet("MobType")) {
                playerMobTypeDamageBuffData.get(player.getUniqueId()).put(type, 0);
            }
            map = playerMobTypeDamageBuffData.get(player.getUniqueId());
            return map;
        }
        return playerMobTypeDamageBuffData.get(player.getUniqueId());
    }

    public void ControlBuffData(Player player, String damageType, int changevalue) {
        defaultBuffSet(player);
        int PreValue = playerMobTypeDamageBuffData.get(player.getUniqueId()).get(damageType);
        setBuffData(player, damageType, PreValue + changevalue);
    }

    public int getBuffData(UUID playerUUID, String damageType) {
        return playerMobTypeDamageBuffData.get(playerUUID).get(damageType);
    }


}
