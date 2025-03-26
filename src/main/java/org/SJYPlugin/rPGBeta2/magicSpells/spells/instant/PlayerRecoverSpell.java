package org.SJYPlugin.rPGBeta2.magicSpells.spells.instant;

import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.util.CastResult;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.SpellData;
import com.nisovin.magicspells.util.config.ConfigData;
import org.SJYPlugin.rPGBeta2.control.hpcontrol.RecoverControl;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlayerRecoverSpell extends InstantSpell {

    RecoverControl recoverControl = RecoverControl.getInstance();

    private final ConfigData<String> RecoverType;

    private final ConfigData<String> ValueType;

    private final ConfigData<Double> Value;

    public PlayerRecoverSpell(MagicConfig config, String spellName) {
        super(config, spellName);
        this.RecoverType = getConfigDataString("recoverType", "CURRHEALTH");  // CURRHEALTH  HEALTH
        this.ValueType = getConfigDataString("valueType", "BASE");      //BASE  PERCENT
        this.Value = getConfigDataDouble("value", 0.0);
    }

    @Override
    public CastResult cast(SpellData data) {
        Player caster;
        LivingEntity livingEntity = data.caster();
        if(livingEntity instanceof Player) {
            caster = (Player) livingEntity;
        } else {
            return new CastResult(PostCastAction.ALREADY_HANDLED, data);
        }
        recoverControl.Recovering(caster, this.RecoverType.get(data), this.ValueType.get(data), this.Value.get(data));

        return new CastResult(PostCastAction.HANDLE_NORMALLY, data);
    }
}
