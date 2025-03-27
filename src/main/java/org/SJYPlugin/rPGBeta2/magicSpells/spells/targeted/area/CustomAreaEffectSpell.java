package org.SJYPlugin.rPGBeta2.magicSpells.spells.targeted.area;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.Subspell;
import com.nisovin.magicspells.events.SpellTargetEvent;
import com.nisovin.magicspells.events.SpellTargetLocationEvent;
import com.nisovin.magicspells.shaded.org.apache.commons.math4.core.jdkmath.AccurateMath;
import com.nisovin.magicspells.spelleffects.EffectPosition;
import com.nisovin.magicspells.spells.TargetedLocationSpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.*;
import com.nisovin.magicspells.util.config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CustomAreaEffectSpell extends TargetedSpell implements TargetedLocationSpell {

    private List<Subspell> spells;

    private List<String> spellNames;

    private final ConfigData<Integer> maxTargets;

    private final ConfigData<Double> cone;

    private final ConfigData<Double> vRadius;

    private final ConfigData<Double> hRadius;

    private final ConfigData<Double> minVRadius;

    private final ConfigData<Double> minHRadius;

    private final ConfigData<Double> horizontalCone;

    private final ConfigData<Boolean> pointBlank;

    private final ConfigData<Boolean> circleShape;

    private final ConfigData<Boolean> ignoreRadius;

    private final ConfigData<Boolean> passTargeting;

    private final ConfigData<Boolean> failIfNoTargets;

    private final ConfigData<Boolean> reverseProximity;

    private final ConfigData<Boolean> spellSourceInCenter;

    public CustomAreaEffectSpell(MagicConfig config, String spellName) {
        super(config, spellName);
        this.spellNames = getConfigStringList("spells", null);
        this.maxTargets = getConfigDataInt("max-targets", 0);
        this.cone = getConfigDataDouble("cone", 0.0D);
        this.horizontalCone = getConfigDataDouble("horizontal-cone", 0.0D);
        this.vRadius = getConfigDataDouble("vertical-radius", 5.0D);
        this.hRadius = getConfigDataDouble("horizontal-radius", 10.0D);
        this.minVRadius = getConfigDataDouble("min-vertical-radius", 0.0D);
        this.minHRadius = getConfigDataDouble("min-horizontal-radius", 0.0D);
        this.pointBlank = getConfigDataBoolean("point-blank", true);
        this.circleShape = getConfigDataBoolean("circle-shape", false);
        this.ignoreRadius = getConfigDataBoolean("ignore-radius", false);
        this.passTargeting = getConfigDataBoolean("pass-targeting", false);
        this.failIfNoTargets = getConfigDataBoolean("fail-if-no-targets", true);
        this.reverseProximity = getConfigDataBoolean("reverse-proximity", false);
        this.spellSourceInCenter = getConfigDataBoolean("spell-source-in-center", false);
    }

    public void initialize() {
        super.initialize();
        this.spells = new ArrayList<>();
        String prefix = "AreaEffectSpell '" + this.internalName + "' ";
        if (this.spellNames == null || this.spellNames.isEmpty()) {
            MagicSpells.error(prefix + "has no spells defined!");
            return;
        }
        for (String spellName : this.spellNames) {
            Subspell spell = initSubspell(spellName, prefix + "attempted to use invalid spell '" + prefix + "'");
            if (spell == null)
                continue;
            this.spells.add(spell);
        }
        this.spellNames.clear();
        this.spellNames = null;
    }

    public CastResult cast(SpellData data) {
        if (((Boolean)this.pointBlank.get(data)).booleanValue()) {
            SpellTargetLocationEvent event = new SpellTargetLocationEvent((Spell)this, data, data.caster().getLocation());
            if (!event.callEvent())
                return noTarget(event);
            data = event.getSpellData();
        } else {
            TargetInfo<Location> info = getTargetedBlockLocation(data, 0.5D, 0.0D, 0.5D, false);
            if (info.noTarget())
                return noTarget(info);
            data = info.spellData();
        }
        return doAoe(data) ? new CastResult(Spell.PostCastAction.HANDLE_NORMALLY, data) : noTarget(data);
    }

    public CastResult castAtLocation(SpellData data) {
        return doAoe(data) ? new CastResult(Spell.PostCastAction.HANDLE_NORMALLY, data) : noTarget(data);
    }

    private boolean doAoe(SpellData data) {
        int count = 0;
        LivingEntity caster = data.caster();
        Location location = Util.makeFinite(data.location());
        data = data.location(location);
        boolean spellSourceInCenter = ((Boolean)this.spellSourceInCenter.get(data)).booleanValue();
        data = data.location(spellSourceInCenter ? location : ((caster == null) ? null : caster.getLocation()));
        boolean circleShape = ((Boolean)this.circleShape.get(data)).booleanValue();
        boolean ignoreRadius = ((Boolean)this.ignoreRadius.get(data)).booleanValue();
        boolean passTargeting = ((Boolean)this.passTargeting.get(data)).booleanValue();
        boolean failIfNoTargets = ((Boolean)this.failIfNoTargets.get(data)).booleanValue();
        boolean reverseProximity = ((Boolean)this.reverseProximity.get(data)).booleanValue();
        int maxTargets = ((Integer)this.maxTargets.get(data)).intValue();
        double cone = ((Double)this.cone.get(data)).doubleValue();
        double horizontalCone = ((Double)this.horizontalCone.get(data)).doubleValue();
        double vRadius = Math.min(((Double)this.vRadius.get(data)).doubleValue(), MagicSpells.getGlobalRadius());
        double hRadius = Math.min(((Double)this.hRadius.get(data)).doubleValue(), MagicSpells.getGlobalRadius());
        double hRadiusSquared = hRadius * hRadius;
        double minVRadius = Math.min(((Double)this.minVRadius.get(data)).doubleValue(), MagicSpells.getGlobalRadius());
        double minHRadius = Math.min(((Double)this.minHRadius.get(data)).doubleValue(), MagicSpells.getGlobalRadius());
        double minHRadiusSquared = minHRadius * minHRadius;
        if (this.validTargetList.canTargetOnlyCaster()) {
            if (caster == null)
                return false;
            LivingEntity target = caster;
            if (!target.getWorld().equals(location.getWorld()))
                return false;
            Location targetLocation = target.getLocation();
            if (circleShape) {
                double hDistance = NumberConversions.square(targetLocation.getX() - location.getX()) + NumberConversions.square(targetLocation.getZ() - location.getZ());
                if (hDistance > hRadiusSquared || hDistance < minHRadiusSquared)
                    return false;
            } else {
                double hDistance = Math.abs(targetLocation.getX() - location.getX()) + Math.abs(targetLocation.getZ() - location.getZ());
                if (hDistance > hRadius || hDistance < minHRadius)
                    return false;
            }
            double vDistance = Math.abs(targetLocation.getY() - location.getY());
            if (vDistance > vRadius || vDistance < minVRadius)
                return false;
            SpellTargetEvent event = new SpellTargetEvent((Spell)this, data, target);
            if (!event.callEvent())
                return false;
            SpellData subData = event.getSpellData();
            target = subData.target();
            castSpells(subData, passTargeting);
            if (spellSourceInCenter) {
                playSpellEffects((Entity) caster, location, (Entity)target, subData);
            } else {
                playSpellEffects((Entity)caster, (Entity)target, subData);
            }
            return true;
        }
        List<LivingEntity> entities = new ArrayList<>();
        if (ignoreRadius) {
            Bukkit.getWorlds().forEach(world -> entities.addAll(world.getLivingEntities()));
        } else {
            entities.addAll(location.getWorld().getNearbyLivingEntities(location, hRadius, vRadius, hRadius));
        }
        if (!circleShape && (minHRadius != 0.0D || minVRadius != 0.0D))
            entities.removeAll(location.getWorld().getNearbyLivingEntities(location, minHRadius, minVRadius, minHRadius));
        for (LivingEntity target : entities) {
            if (target.isDead() ||
                    !this.validTargetList.canTarget(caster, (Entity)target))
                continue;
            if (circleShape && !ignoreRadius) {
                Location targetLocation = target.getLocation();
                double hDistance = NumberConversions.square(targetLocation.getX() - location.getX()) + NumberConversions.square(targetLocation.getZ() - location.getZ());
                if (hDistance > hRadiusSquared || hDistance < minHRadiusSquared)
                    continue;
            }
            if (horizontalCone > 0.0D && horizontalAngle(location, target.getLocation()) > horizontalCone)
                continue;
            if (cone > 0.0D) {
                Vector dir = target.getLocation().toVector().subtract(location.toVector());
                if (AccurateMath.toDegrees(AccurateMath.abs(dir.angle(location.getDirection()))) > cone)
                    continue;
            }
            SpellTargetEvent event = new SpellTargetEvent((Spell)this, data, target);
            if (!event.callEvent())
                continue;
            SpellData subData = event.getSpellData();
            target = subData.target();
            castSpells(subData, passTargeting);
            playSpellEffects(EffectPosition.TARGET, (Entity)target, subData);
            playSpellEffects(EffectPosition.END_POSITION, (Entity)target, subData);
            if (spellSourceInCenter) {
                playSpellEffects(EffectPosition.START_POSITION, location, subData);
                playSpellEffectsTrail(location, target.getLocation(), subData);
            } else if (caster != null) {
                playSpellEffects(EffectPosition.START_POSITION, (Entity)caster, subData);
                playSpellEffectsTrail(caster.getLocation(), target.getLocation(), subData);
            }
            count++;
            if (maxTargets > 0 && count >= maxTargets)
                break;
        }
        boolean success = (count > 0 || !failIfNoTargets);
        if (success) {
            playSpellEffects(EffectPosition.SPECIAL, location, data);
            if (caster != null)
                playSpellEffects(EffectPosition.CASTER, (Entity)caster, data);
        }
        return success;
    }

    private void castSpells(SpellData data, boolean passTargeting) {
        for (Subspell spell : this.spells)
            spell.subcast(data, passTargeting);
    }

    private double horizontalAngle(Location from, Location to) {
        Location startLoc = from.clone();
        Location endLoc = to.clone();
        startLoc.setY(0.0D);
        startLoc.setPitch(0.0F);
        endLoc.setY(0.0D);
        endLoc.setPitch(0.0F);
        Vector direction = endLoc.toVector().subtract(startLoc.toVector()).normalize();
        return AccurateMath.toDegrees(direction.angle(startLoc.getDirection()));
    }
}
