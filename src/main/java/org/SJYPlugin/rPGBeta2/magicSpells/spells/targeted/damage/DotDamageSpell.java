package org.SJYPlugin.rPGBeta2.magicSpells.spells.targeted.damage;

import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.SpellApplyDamageEvent;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.CastResult;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.SpellData;
import com.nisovin.magicspells.util.TargetInfo;
import com.nisovin.magicspells.util.config.ConfigData;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.dot.DotDamageApply;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.DamageApplyPVEmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.DamageComputePVEmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp.DamageApplyPVPmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp.DamageComputePVPmain;
import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class DotDamageSpell extends TargetedSpell implements TargetedEntitySpell {

    DamageComputePVPmain damageComputePVPmain = DamageComputePVPmain.getInstance();
    DamageComputePVEmain damageComputePVEmain = DamageComputePVEmain.getInstance();
    DamageApplyPVPmain damageApplyPVPmain = DamageApplyPVPmain.getInstance();
    DamageApplyPVEmain damageApplyPVEmain = DamageApplyPVEmain.getInstance();
    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();
    DotDamageApply dotDamageApply = DotDamageApply.getInstance();

    private final ConfigData<String> DamageStemType;

    private final ConfigData<String> DamageBaseType;

    private final ConfigData<Integer> DamageMag;

    private final ConfigData<String> DamageAttribute;

    private final ConfigData<Boolean> TrueDamage;

    private final ConfigData<Boolean> ForceCritical;

    private final ConfigData<Integer> Duration;

    private final ConfigData<Integer> Interval;

    private final ConfigData<String> DotName;

//    private final ConfigData<Boolean> ForceNoCritical;


    public DotDamageSpell(MagicConfig config, String spellName) {
        super(config, spellName);
        this.DamageStemType = getConfigDataString("damage-stem-type","NORMAL");
        this.DamageBaseType = getConfigDataString("damage-base-type", "ATTACK");
        this.DamageMag = getConfigDataInt("damage-mag", 0);
        this.DamageAttribute = getConfigDataString("damage-attribute", "PHYSICS");
        this.ForceCritical = getConfigDataBoolean("force-critical", false);
        this.TrueDamage = getConfigDataBoolean("true-damage", false);
        this.Duration = getConfigDataInt("duration", 0);
        this.Interval = getConfigDataInt("interval", 0);
        this.DotName = getConfigDataString("dot-name", "NULL");
    }

    @Override
    public CastResult cast(SpellData data) {
        TargetInfo<LivingEntity> info = getTargetedEntity(data);
        if (info.noTarget()) return noTarget(info);
        return castAtEntity(info.spellData());
    }

    @Override
    public CastResult castAtEntity(SpellData data) {
        return DotDamage(data);
    }

    private CastResult DotDamage(SpellData data) {
        EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;

        boolean isCritical = false;

        if(!data.target().isValid()) {
            return noTarget(data);
        }
        double VirtualFinalDamage;

        if(data.target() instanceof Player) {
            if(data.caster() instanceof Player) {
                Player offender = (Player) data.target();
                Player attacker = (Player) data.caster();
                if(this.ForceCritical.get(data)) {
                    isCritical = true;
                } else {
                    isCritical = playerDamageData.OnCritical(attacker);
                }
                //Critical ??
                VirtualFinalDamage = damageComputePVPmain.FinalDamage((Player) attacker, offender,
                        DamageBaseType.get(data), DamageMag.get(data), "DOT", DamageStemType.get(data),
                        DamageAttribute.get(data), isCritical);
                SpellApplyDamageEvent event = new SpellApplyDamageEvent((Spell) this, data.caster(), data.target(), VirtualFinalDamage, damageCause, "");
                event.callEvent();
                dotDamageApply.DotDamageApply(attacker, offender, VirtualFinalDamage, DamageBaseType.get(data), DamageStemType.get(data),
                        "DOT", DamageAttribute.get(data), isCritical, Duration.get(data), Interval.get(data), DotName.get(data));
            } else {

            }
        } else {
            if(data.caster() instanceof Player) {
                LivingEntity offender = data.target();
                Player attacker = (Player) data.caster();
                if(this.ForceCritical.get(data)) {
                    isCritical = true;
                } else {
                    isCritical = playerDamageData.OnCritical(attacker);
                }
                //Critical ??
                VirtualFinalDamage = damageComputePVEmain.FinalDamage(attacker, offender, DamageBaseType.get(data), DamageMag.get(data),
                        "DOT", DamageStemType.get(data), DamageAttribute.get(data), isCritical);
                SpellApplyDamageEvent event = new SpellApplyDamageEvent((Spell) this, data.caster(), data.target(), VirtualFinalDamage, damageCause, "");
                event.callEvent();
                dotDamageApply.DotDamageApply(attacker, offender, VirtualFinalDamage, DamageBaseType.get(data), DamageStemType.get(data),
                        "DOT", DamageAttribute.get(data), isCritical, Duration.get(data), Interval.get(data), DotName.get(data));
            } else {

            }
        }
        playSpellEffects(data);
        return new CastResult(PostCastAction.HANDLE_NORMALLY, data);
    }

}
