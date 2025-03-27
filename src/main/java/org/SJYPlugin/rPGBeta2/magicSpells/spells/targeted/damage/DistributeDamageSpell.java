package org.SJYPlugin.rPGBeta2.magicSpells.spells.targeted.damage;

import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.SpellApplyDamageEvent;
import com.nisovin.magicspells.events.SpellTargetLocationEvent;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.CastResult;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.SpellData;
import com.nisovin.magicspells.util.TargetInfo;
import com.nisovin.magicspells.util.config.ConfigData;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.DamageApplyPVEmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.DamageComputePVEmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp.DamageApplyPVPmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pvp.DamageComputePVPmain;
import org.SJYPlugin.rPGBeta2.data.playerdata.damagedata.PlayerDamageData;
import org.SJYPlugin.rPGBeta2.magicSpells.data.RangeTargetData;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;

public class DistributeDamageSpell extends TargetedSpell {

    DamageComputePVPmain damageComputePVPmain = DamageComputePVPmain.getInstance();
    DamageApplyPVPmain damageApplyPVPmain = DamageApplyPVPmain.getInstance();
    DamageComputePVEmain damageComputePVEmain = DamageComputePVEmain.getInstance();
    DamageApplyPVEmain damageApplyPVEmain = DamageApplyPVEmain.getInstance();
    PlayerDamageData playerDamageData = PlayerDamageData.getInstance();
    RangeTargetData rangeTargetData = RangeTargetData.getInstance();


    private final ConfigData<String> DamageStemType;

    private final ConfigData<String> DamageBaseType;

    private final ConfigData<Integer> DamageMag;

    private final ConfigData<String> DamageAttribute;

    private final ConfigData<Boolean> TrueDamage;

    private final ConfigData<Boolean> ForceCritical;

    private final ConfigData<Boolean> PointBlank;

    private final ConfigData<Integer> MaxTargets;

    private final ConfigData<Boolean> SpellSourceInCenter;

    private final ConfigData<Boolean> FailIfNoTargets;

    private final ConfigData<Double> VRadius;

    private final ConfigData<Double> HRadius;




    public DistributeDamageSpell(MagicConfig config, String spellName) {
        super(config, spellName);
        this.DamageStemType = getConfigDataString("damage-stem-type","NORMAL");
        this.DamageBaseType = getConfigDataString("damage-base-type", "ATTACK");
        this.DamageMag = getConfigDataInt("damage-mag", 0);
        this.DamageAttribute = getConfigDataString("damage-attribute", "PHYSICS");
        this.ForceCritical = getConfigDataBoolean("force-critical", false);
        this.TrueDamage = getConfigDataBoolean("true-damage", false);
        this.MaxTargets = getConfigDataInt("max-targets", 0);
        this.SpellSourceInCenter = getConfigDataBoolean("spell-source-in-center", false);
        this.FailIfNoTargets = getConfigDataBoolean("fail-if-no-targets", true);
        this.VRadius = getConfigDataDouble("v-radius", 0.0D);
        this.HRadius = getConfigDataDouble("h-radius", 0.0D);
        this.PointBlank = getConfigDataBoolean("point-blank", false);
    }

    @Override
    public CastResult cast(SpellData data) {
        if(PointBlank.get(data)) {
            SpellTargetLocationEvent event = new SpellTargetLocationEvent((Spell) this, data);
            if(!event.callEvent()) {
                return noTarget(data);
            }
            data = event.getSpellData();
        } else {
            TargetInfo<Location> info = getTargetedBlockLocation(data, 0.5D, 0.0D, 0.5D, false);
            if (info.noTarget()) return noTarget(info);
            data = info.spellData();
        }
//        return DistributeDamage(data) ? new CastResult(Spell.PostCastAction.HANDLE_NORMALLY, data) : noTarget(data);
        return null;
    }

//    private boolean DistributeDamage(SpellData data) {
//        EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;
//
//        Map<LivingEntity, Location> targets = rangeTargetData.getTargets(data, SpellSourceInCenter.get(data), true, FailIfNoTargets.get(data),
//                MaxTargets.get(data), VRadius.get(data), HRadius.get(data), 0, 0);
//        int targetCount = targets.size();
//
//        boolean isCritical = false;
//
//        int DistributeMag = this.DamageMag.get(data);
//
//        if(!data.target().isValid()) {
//            return false;
//        }
//
//        for(LivingEntity target : targets.keySet()) {
//            if(target.isDead() || !validTargetList.canTarget(data.caster(), target)) {
//                continue;
//            }
//            if(data.caster() instanceof Player) {
//                if(target instanceof Player) {
//                    Player offender = (Player) target;
//                    Player attacker = (Player) data.caster();
//
//                    if(this.ForceCritical.get(data)) {
//                        isCritical = true;
//                    } else {
//                        isCritical = playerDamageData.OnCritical(attacker);
//                    }
//                    double VirtualFinalDamage = damageComputePVPmain.FinalDamage((Player) attacker, offender,
//                            DamageBaseType.get(data), DistributeMag, "DISTRIBUTE", DamageStemType.get(data),
//                            DamageAttribute.get(data), isCritical);
//                    SpellApplyDamageEvent event = new SpellApplyDamageEvent((Spell) this, data.caster(), target, VirtualFinalDamage, damageCause, "");
//                    event.callEvent();
//                    damageApplyPVPmain.DamagePVP(attacker, offender, VirtualFinalDamage, DamageBaseType.get(data), DamageStemType.get(data),
//                            "DISTRIBUTE", DamageAttribute.get(data), isCritical);
//
//                    return true;
//                } else {
//                    LivingEntity offender = target;
//                    Player attacker = (Player) data.caster();
//                    if(this.ForceCritical.get(data)) {
//                        isCritical = true;
//                    } else {
//                        isCritical = playerDamageData.OnCritical(attacker);
//                    }
//                    double VirtualFinalDamage = damageComputePVEmain.FinalDamage(attacker, offender, DamageBaseType.get(data), DistributeMag,
//                            "DISTRIBUTE", DamageStemType.get(data), DamageAttribute.get(data), isCritical);
//                    SpellApplyDamageEvent event = new SpellApplyDamageEvent((Spell) this, data.caster(), target, VirtualFinalDamage, damageCause, "");
//                    event.callEvent();
//                    damageApplyPVEmain.DamagePVE_Mythic(attacker, offender, VirtualFinalDamage, DamageBaseType.get(data), DamageStemType.get(data),
//                            "DISTRIBUTE", DamageAttribute.get(data), isCritical);
//
//                    return true;
//                }
//            } else {
//                return false;
//            }
//        }
//        playSpellEffects(data);
//        return true;
//    }

}
