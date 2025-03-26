package org.SJYPlugin.rPGBeta2.mythicMobs.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.auras.Aura;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import org.SJYPlugin.rPGBeta2.data.mobdata.buffdata.MythicMobRootDamageBuffData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

@MythicMechanic(author = "SJY", name = "rootdamagebuff")
public class RootDamageBuff extends Aura implements ITargetedEntitySkill {

    private static RootDamageBuff instance;

    public static RootDamageBuff getInstance() {return instance;}

    MythicMobRootDamageBuffData mythicMobRootDamageBuffData = MythicMobRootDamageBuffData.getInstance();

    private final boolean ignoreAuraOptions;

    private final Set<UUID> BuffSet;

    private final Map<String, Integer> damagebuffMap;

    public RootDamageBuff(SkillExecutor manager, File file, String skill, MythicLineConfig config) {
        super(manager, file, skill, config);
        this.ignoreAuraOptions = config.getBoolean(new String[]{"permanent", "perma", "ignoreAuraOptions", "iao"}, false);
        this.damagebuffMap = getBuffMap_sub(config);
        this.BuffSet = new HashSet<>();

        this.instance = this;
//        if(!this.ignoreAuraOptions) {
//
//        }
    }

    private Map<String, Integer> getBuffMap_sub(MythicLineConfig config) {
        String Root_NORMAL = "normal";
        String Root_RANGE = "range";
        String Root_DISTRIBUTE = "distribute";
        String Root_DOT = "dot";
        String Root_SPREAD = "spread";
        String Root_ADDITIONAL = "additional";

        Map<String, Integer> buffMap_sub = new HashMap<>();
        buffMap_sub.put(Root_NORMAL, config.getInteger(Root_NORMAL, 0));
        buffMap_sub.put(Root_RANGE, config.getInteger(Root_RANGE, 0));
        buffMap_sub.put(Root_DISTRIBUTE, config.getInteger(Root_DISTRIBUTE, 0));
        buffMap_sub.put(Root_DOT, config.getInteger(Root_DOT, 0));
        buffMap_sub.put(Root_SPREAD, config.getInteger(Root_SPREAD, 0));
        buffMap_sub.put(Root_ADDITIONAL, config.getInteger(Root_ADDITIONAL, 0));

        return buffMap_sub;
    }

    public boolean isActive(LivingEntity livingEntity) {
        return this.BuffSet.contains(livingEntity.getUniqueId());
    }

    public Map<String, Integer> getDamagebuffMap() {
        return this.damagebuffMap;
    }

    private void BuffOnAdd(LivingEntity livingEntity) {
        if(BuffSet.contains(livingEntity.getUniqueId())) {
            for(Map.Entry<String, Integer> entry : damagebuffMap.entrySet()) {
                mythicMobRootDamageBuffData.ControlBuffData(livingEntity, entry.getKey(), entry.getValue());
            }
        }
    }

    private void BuffOffRemove(LivingEntity livingEntity) {
        if(!BuffSet.contains(livingEntity.getUniqueId())) {
            for(Map.Entry<String, Integer> entry : damagebuffMap.entrySet()) {
                mythicMobRootDamageBuffData.ControlBuffData(livingEntity, entry.getKey(), -entry.getValue());
            }
        }
    }


    // 항상 @Self Trigger 사용권장
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if(!target.isPlayer() && target.getUniqueId() == data.getCaster().getEntity().getUniqueId()) {
            if (this.ignoreAuraOptions) {
                if (!data.getCaster().getEntity().isPlayer()) {

                }
            } else {
                new RootDamageBuff.Tracker(data, target);
            }
            return SkillResult.SUCCESS;
        } else {
            return SkillResult.INVALID_TARGET;
        }
    }

    private class Tracker extends Aura.AuraTracker {
        public Tracker(SkillMetadata data, AbstractEntity target) {
            super((SkillCaster) RootDamageBuff.this, target, data);
            this.start();
        }

        public void auraStart() {
            super.auraStart();
            this.entity.ifPresent((e) -> {
                if(!e.isPlayer()) {
                    if(!this.skillMetadata.getCaster().getEntity().isPlayer()) {
                        if(this.skillMetadata.getCaster().getEntity().getUniqueId() == e.getUniqueId()) {
                            if(e instanceof LivingEntity) {
                                RootDamageBuff.this.BuffSet.add(e.getUniqueId());
                                RootDamageBuff.this.BuffOnAdd((LivingEntity) this.skillMetadata.getCaster().getEntity().getBukkitEntity());
                            } else {
                            }
                        } else {

                        }
                    } else {

                    }
                } else {

                }
            });
        }

        public void auraStop() {
            super.auraStop();
            this.entity.ifPresent((e) -> {
                if(!e.isPlayer()) {
                    if(!this.skillMetadata.getCaster().getEntity().isPlayer()) {
                        if(this.skillMetadata.getCaster().getEntity().getUniqueId() == e.getUniqueId()) {
                            if(e instanceof LivingEntity) {
                                RootDamageBuff.this.BuffSet.remove(e.getUniqueId());
                                RootDamageBuff.this.BuffOffRemove((LivingEntity) this.skillMetadata.getCaster().getEntity().getBukkitEntity());
                            } else {
                            }
                        } else {

                        }
                    } else {

                    }
                } else {

                }
            });
        }

    }
}
