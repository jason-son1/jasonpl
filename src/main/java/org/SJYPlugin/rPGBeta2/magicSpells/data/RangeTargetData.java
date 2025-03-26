package org.SJYPlugin.rPGBeta2.magicSpells.data;

import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.SpellTargetEvent;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.SpellData;
import com.nisovin.magicspells.util.Util;
import org.SJYPlugin.rPGBeta2.magicSpells.target.CustomValidTarget;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.NumberConversions;

import java.util.*;

public class RangeTargetData {

    private static final RangeTargetData instance = new RangeTargetData();

    public static RangeTargetData getInstance() {
        return instance;
    }

    CustomValidTarget customValidTarget = CustomValidTarget.getInstance();

    public Map<LivingEntity, Location> getTargets(SpellData data, boolean SpellSourceInCenter, boolean CircleShape, boolean FailIfNoTargets,
                                                  int MaxTargets, double VRadius, double HRadius, double MinVRadius, double MinHRadius) {
        Map<LivingEntity, Location> targets = new HashMap<>();
        int count = 0;
        LivingEntity caster = data.caster();
        Location location = Util.makeFinite(data.location());
        data = data.location(location);
        boolean spellSourceInCenter = SpellSourceInCenter;
        data = data.location(spellSourceInCenter ? location : ((caster == null) ? null : caster.getLocation()));
        boolean circleShape = CircleShape;
        boolean failIfNoTargets = FailIfNoTargets;
        int maxTargets = MaxTargets;

        double vRadius = VRadius;
        double hRadius = HRadius;
        double hRadiusSquared = hRadius * hRadius;
        double minVRadius = MinVRadius;
        double minHRadius = MinHRadius;
        double minHRadiusSquared = minHRadius * minHRadius;

        List<LivingEntity> entities = new ArrayList<>();

        entities.addAll(location.getWorld().getNearbyLivingEntities(location, hRadius, vRadius, hRadius));

        if (!circleShape && (minHRadius != 0.0D || minVRadius != 0.0D))
            entities.removeAll(location.getWorld().getNearbyLivingEntities(location, minHRadius, minVRadius, minHRadius));

        for (LivingEntity target : entities) {
            if(target.isDead() ||
            !customValidTarget.IsValidTarget(caster, (Entity) target)) {
                entities.remove(target);
                continue;
            }
//            if (target.isDead() ||
//                    !this.validTargetList.canTarget(caster, (Entity) target)) {
//                entities.remove(target);
//                continue;
//            }
            if (circleShape) {
                Location targetLocation = target.getLocation();
                double hDistance = NumberConversions.square(targetLocation.getX() - location.getX()) + NumberConversions.square(targetLocation.getZ() - location.getZ());
                if (hDistance > hRadiusSquared || hDistance < minHRadiusSquared) {
                    entities.remove(target);
                    continue;
                }
            }
//            SpellTargetEvent event = new SpellTargetEvent(, data, target);
//            if (!event.callEvent()) {
//                entities.remove(target);
//                continue;
//            }
            count++;
            if (maxTargets > 0 && count >= maxTargets)
                break;
            targets.put(target, target.getLocation());
        }
//        for(LivingEntity target : entities) {
//            targets.put(target, target.getLocation());
//        }
        boolean success = (count > 0 || !failIfNoTargets);
        if (success) {
            return targets;
        } else {
            return new HashMap<>();
        }
    }


}
