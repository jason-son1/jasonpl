package org.SJYPlugin.rPGBeta2.magicSpells.spells.buff;

import com.nisovin.magicspells.spells.BuffSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.SpellData;
import org.SJYPlugin.rPGBeta2.data.playerdata.buffdata.PlayerMobTypeDamageBuffData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class MobTypeDamageBuffSpell extends BuffSpell {

    PlayerMobTypeDamageBuffData playerMobTypeDamageBuffData = PlayerMobTypeDamageBuffData.getInstance();

    private final Set<UUID> players;

    private final Map<String, Integer> damageTypeMap;

    public MobTypeDamageBuffSpell(MagicConfig config, String spellName) {
        super(config, spellName);
        Map<String, Object> damageTypeMap = getConfigSection("damage-types").getValues(false);
        this.players = new HashSet<>();
        this.damageTypeMap = new HashMap<>();
        for(Map.Entry<String, Object> entry : damageTypeMap.entrySet()) {
            this.damageTypeMap.put(entry.getKey(), (Integer) entry.getValue());
        }
    }

    public boolean castBuff(SpellData data) {
        this.players.add(data.target().getUniqueId());
        if(data.target() instanceof Player) {
            BuffOnAdd((Player) data.target());
        }
        return true;
    }

    @Override
    public boolean isActive(LivingEntity livingEntity) {
        return this.players.contains(livingEntity.getUniqueId());
    }

    @Override
    protected void turnOffBuff(LivingEntity livingEntity) {
        this.players.remove(livingEntity.getUniqueId());
        if(livingEntity instanceof Player) {
            BuffOffRemove((Player) livingEntity);
        }
    }

    @Override
    protected void turnOff() {
        this.players.clear();
    }

    public Map<String, Integer> getDamageTypeMap() {
        return this.damageTypeMap;
    }

    private void BuffOnAdd(Player player) {
        if(players.contains(player.getUniqueId())) {
            for(Map.Entry<String, Integer> entry : this.damageTypeMap.entrySet()) {
                playerMobTypeDamageBuffData.ControlBuffData(player, entry.getKey(), entry.getValue());
                player.sendMessage("버프온" + entry.getKey() + " " + entry.getValue());
            }
        }
    }

    private void BuffOffRemove(Player player) {
        if(!players.contains(player.getUniqueId())) {
            for(Map.Entry<String, Integer> entry : this.damageTypeMap.entrySet()) {
                playerMobTypeDamageBuffData.ControlBuffData(player, entry.getKey(), -entry.getValue());
                player.sendMessage("버프오프" + entry.getKey() + " " + entry.getValue());
            }
        }
    }

}
