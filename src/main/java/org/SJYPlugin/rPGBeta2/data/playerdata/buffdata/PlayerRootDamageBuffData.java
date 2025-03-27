package org.SJYPlugin.rPGBeta2.data.playerdata.buffdata;

import org.SJYPlugin.rPGBeta2.data.DamageData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRootDamageBuffData {

    private static final PlayerRootDamageBuffData instance = new PlayerRootDamageBuffData();

    public static PlayerRootDamageBuffData getInstance() {
        return instance;
    }

    DamageData damageData = DamageData.getInstance();

    private final Map<UUID, Map<String, Integer>> playerRootDamageBuffData = new HashMap<>();

    public void setBuffData(Player player, String damageType, int value) {
        defaultBuffSet(player);
        if(damageData.getDamgeTypeSet("DamageRootType").contains(damageType)) {
            playerRootDamageBuffData.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(damageType, value);
        }
    }

    public Map<String, Integer> getBuffData(Player player) {
        return playerRootDamageBuffData.getOrDefault(player.getUniqueId(), defaultBuffSet(player));
    }

    public Map<String, Integer> defaultBuffSet(Player player) {
        Map<String, Integer> map;
        if(playerRootDamageBuffData == null || playerRootDamageBuffData.get(player.getUniqueId()) == null ||
                playerRootDamageBuffData.get(player.getUniqueId()).isEmpty()) {
            playerRootDamageBuffData.put(player.getUniqueId(), new HashMap<>());
            for (String type : damageData.getDamgeTypeSet("DamageRootType")) {
                playerRootDamageBuffData.get(player.getUniqueId()).put(type, 0);
            }
            map = playerRootDamageBuffData.get(player.getUniqueId());
            return map;
        }
        return playerRootDamageBuffData.get(player.getUniqueId());
    }

    public void ControlBuffData(Player player, String damageType, int changevalue) {
        defaultBuffSet(player);
        int PreValue = playerRootDamageBuffData.get(player.getUniqueId()).get(damageType);
        setBuffData(player, damageType, PreValue + changevalue);
    }

    public int getBuffData(Player player, String damageType) {
        return playerRootDamageBuffData.get(player.getUniqueId()).get(damageType);
    }







}
