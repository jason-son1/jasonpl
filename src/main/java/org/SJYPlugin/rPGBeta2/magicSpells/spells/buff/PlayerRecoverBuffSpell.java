package org.SJYPlugin.rPGBeta2.magicSpells.spells.buff;

import com.nisovin.magicspells.spells.BuffSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.SpellData;
import com.nisovin.magicspells.util.config.ConfigData;
import org.bukkit.entity.LivingEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerRecoverBuffSpell extends BuffSpell {

    private static PlayerRecoverBuffSpell instance;

    public static PlayerRecoverBuffSpell getInstance() {return instance;}

    private final Set<UUID> players;

    private final ConfigData<String> RecoverType;

    private final ConfigData<String> ValueType;

    private final ConfigData<Double> Value;


    public PlayerRecoverBuffSpell(MagicConfig config, String spellName) {
        super(config, spellName);
        this.players = new HashSet<>();
        this.RecoverType = getConfigDataString("recoverType", "CURRHEALTH");  // CURRHEALTH  HEALTH
        this.ValueType = getConfigDataString("valueType", "BASE");      //BASE  PERCENT
        this.Value = getConfigDataDouble("value", 0.0);
        this.instance = this;
    }

    public boolean castBuff(SpellData data) {
        this.players.add(data.target().getUniqueId());
        return true;
    }

    @Override
    public boolean isActive(LivingEntity livingEntity) {
        return false;
    }

    @Override
    protected void turnOffBuff(LivingEntity livingEntity) {

    }

    @Override
    protected void turnOff() {

    }
}
