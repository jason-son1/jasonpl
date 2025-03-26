package org.SJYPlugin.rPGBeta2.magicSpells.spells.buff;

import com.nisovin.magicspells.spells.BuffSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.SpellData;
import org.SJYPlugin.rPGBeta2.data.playerdata.buffdata.PlayerRootDamageBuffData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class RootDamagebuffSpell extends BuffSpell {

    private static RootDamagebuffSpell instance;

    public static RootDamagebuffSpell getInstance() {
        return instance;
    }

    PlayerRootDamageBuffData playerRootDamageBuffData = PlayerRootDamageBuffData.getInstance();

    private final Set<UUID> players;

    private final Map<String, Integer> damagerootTypeMap;

    public RootDamagebuffSpell(MagicConfig config, String spellName) {
        super(config, spellName);
        Map<String, Object> ListSet = getConfigSection("damage-types").getValues(false);
        this.players = new HashSet<>();
        this.damagerootTypeMap = new HashMap<>();
        for(Map.Entry<String, Object> entry : ListSet.entrySet()) {
            damagerootTypeMap.put(entry.getKey(), (Integer) entry.getValue());
        }
        this.instance = this;
    }

    public boolean castBuff(SpellData data) {
        this.players.add(data.target().getUniqueId());
        if(data.target() instanceof Player) {
            BuffOnAdd((Player) data.target());
        }
        data.target().sendMessage("버프온" + this.damagerootTypeMap);
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
            livingEntity.sendMessage("버프오프" + this.damagerootTypeMap);
        }
//        this.damagerootTypeMap.clear();
    }

    @Override
    protected void turnOff() {
        this.players.clear();
    }

    public Set<UUID> getPlayers() {
        return this.players;
    }

    public Map<String ,Integer> getDamageRootTypeMap() {
        return this.damagerootTypeMap;
    }

    private void BuffOnAdd(Player player) {
        if(players.contains(player.getUniqueId())) {
            for(Map.Entry<String, Integer> entry : damagerootTypeMap.entrySet()) {
                playerRootDamageBuffData.ControlBuffData(player, entry.getKey(), entry.getValue());
            }
        }
    }

    private void BuffOffRemove(Player player) {
        if(!players.contains(player.getUniqueId())) {
            for(Map.Entry<String, Integer> entry : damagerootTypeMap.entrySet()) {
                playerRootDamageBuffData.ControlBuffData(player, entry.getKey(), -entry.getValue());
            }
        }
    }




}
