package org.SJYPlugin.rPGBeta2.magicSpells.spells.buff;

import com.nisovin.magicspells.spells.BuffSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.SpellData;
import org.SJYPlugin.rPGBeta2.data.playerdata.buffdata.PlayerStemDamageBuffData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class StemDamagebuffSpell extends BuffSpell {

    private static StemDamagebuffSpell instance;

    public static StemDamagebuffSpell getInstance() {
        return instance;
    }

    PlayerStemDamageBuffData playerStemDamageBuffData = PlayerStemDamageBuffData.getInstance();

    private final Set<UUID> players;

    private final Map<String, Integer> damagestemTypeMap;

    public StemDamagebuffSpell(MagicConfig config, String spellName) {
        super(config, spellName);
        Map<String, Object> ListSet = getConfigSection("damage-types").getValues(false);
        this.players = new HashSet<>();
        this.damagestemTypeMap = new HashMap<>();
        for(Map.Entry<String, Object> entry : ListSet.entrySet()) {
            damagestemTypeMap.put(entry.getKey(), (Integer) entry.getValue());
        }
        instance = this;
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

    public Set<UUID> getPlayers() {
        return this.players;
    }

    public Map<String ,Integer> getDamageStemTypeMap() {
        return this.damagestemTypeMap;
    }

    private void BuffOnAdd(Player player) {
        if(players.contains(player.getUniqueId())) {
            for(String type : damagestemTypeMap.keySet()) {
                playerStemDamageBuffData.ControlBuffData(player, type, damagestemTypeMap.get(type));
            }
        }
    }

    private void BuffOffRemove(Player player) {
        if(players.contains(player.getUniqueId())) {
            for(String type : damagestemTypeMap.keySet()) {
                playerStemDamageBuffData.ControlBuffData(player, type, -damagestemTypeMap.get(type));
            }
        }
    }





}
