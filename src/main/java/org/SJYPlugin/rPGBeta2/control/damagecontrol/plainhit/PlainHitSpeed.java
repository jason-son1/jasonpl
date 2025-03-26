package org.SJYPlugin.rPGBeta2.control.damagecontrol.plainhit;

import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlainHitSpeed {

    private static final PlainHitSpeed instance = new PlainHitSpeed();

    public static PlainHitSpeed getInstance() {
        return instance;
    }

    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();

    private final Map<UUID, Long> lastActivationTime = new HashMap<>();
    private final long basicCooldown = 1500;

    public boolean PlayerPlainHitSpeed(Player player) {
        double AtcSpeed =  configUtilStat2.getAtcSpd(player);
        long currentTime = System.currentTimeMillis();
        long playerCooldown = (long) (basicCooldown / (AtcSpeed/100));
        if (lastActivationTime.containsKey(player.getUniqueId())) {
            long lastTime = lastActivationTime.get(player.getUniqueId());
            if (currentTime - lastTime < playerCooldown) {
                return false;
            } else {
                lastActivationTime.put(player.getUniqueId(), currentTime);
                return true;
            }
        } else {
            lastActivationTime.put(player.getUniqueId(), currentTime);
            return true;
        }
    }

}
