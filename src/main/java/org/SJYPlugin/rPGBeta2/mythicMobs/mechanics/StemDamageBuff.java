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
import org.SJYPlugin.rPGBeta2.data.mobdata.buffdata.MythicMobStemDamageBuffData;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.util.*;

@MythicMechanic(author = "SJY", name = "stemdamagebuff")
public class StemDamageBuff extends Aura implements ITargetedEntitySkill {

    private static StemDamageBuff instance;

    public static StemDamageBuff getInstance() {return instance;}

    MythicMobStemDamageBuffData mythicMobStemDamageBuffData = MythicMobStemDamageBuffData.getInstance();

    private final boolean ignoreAuraOptions;

    private final Set<UUID> BuffSet;

    private final Map<String, Integer> damagebuffMap;

    public StemDamageBuff(SkillExecutor manager, File file, String skill, MythicLineConfig config) {
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
        String Stem_NORMAL = "normal";
        String Stem_NORMALSKILL = "normalskill";
        String Stem_PASSIVESKILL = "passiveskill";
        String Stem_SPECIALSKILL = "specialskill";
        String Stem_ULTIMATESKILL = "ultimateskill";

        Map<String, Integer> buffMap_sub = new HashMap<>();
        buffMap_sub.put(Stem_NORMAL, config.getInteger(Stem_NORMAL, 0));
        buffMap_sub.put(Stem_NORMALSKILL, config.getInteger(Stem_NORMALSKILL, 0));
        buffMap_sub.put(Stem_PASSIVESKILL, config.getInteger(Stem_PASSIVESKILL, 0));
        buffMap_sub.put(Stem_SPECIALSKILL, config.getInteger(Stem_SPECIALSKILL, 0));
        buffMap_sub.put(Stem_ULTIMATESKILL, config.getInteger(Stem_ULTIMATESKILL, 0));

        return buffMap_sub;
    }

    public boolean isActive(LivingEntity livingEntity) {
        return this.BuffSet.contains(livingEntity.getUniqueId());
    }

    public Map<String, Integer> getDamagebuffMap() {
        return this.damagebuffMap;
    }


    // 항상 @Self Trigger 사용권장
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if(!target.isPlayer() && target.getUniqueId() == data.getCaster().getEntity().getUniqueId()) {
            if (this.ignoreAuraOptions) {
                if (!data.getCaster().getEntity().isPlayer()) {

                }
            } else {
                new Tracker(data, target);
            }
            return SkillResult.SUCCESS;
        } else {
            return SkillResult.INVALID_TARGET;
        }
    }

    private void BuffOnAdd(LivingEntity livingEntity) {
        if(BuffSet.contains(livingEntity.getUniqueId())) {
            for(Map.Entry<String, Integer> entry : damagebuffMap.entrySet()) {
                mythicMobStemDamageBuffData.ControlBuffData(livingEntity, entry.getKey(), entry.getValue());
            }
        }
    }

    private void BuffOffRemove(LivingEntity livingEntity) {
        if(!BuffSet.contains(livingEntity.getUniqueId())) {
            for(Map.Entry<String, Integer> entry : damagebuffMap.entrySet()) {
                mythicMobStemDamageBuffData.ControlBuffData(livingEntity, entry.getKey(), -entry.getValue());
            }
        }
    }

    private class Tracker extends Aura.AuraTracker {
        public Tracker(SkillMetadata data, AbstractEntity target) {
            super((SkillCaster) StemDamageBuff.this, target, data);
            this.start();
        }

        public void auraStart() {
            super.auraStart();
            this.entity.ifPresent((e) -> {
                if(!e.isPlayer()) {
                    if(!this.skillMetadata.getCaster().getEntity().isPlayer()) {
                        if(this.skillMetadata.getCaster().getEntity().getUniqueId() == e.getUniqueId()) {
                            if(e instanceof LivingEntity) {
                                StemDamageBuff.this.BuffSet.add(e.getUniqueId());
                                StemDamageBuff.this.BuffOnAdd((LivingEntity) this.skillMetadata.getCaster().getEntity().getBukkitEntity());
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
                                StemDamageBuff.this.BuffSet.remove(e.getUniqueId());
                                StemDamageBuff.this.BuffOffRemove((LivingEntity) this.skillMetadata.getCaster().getEntity().getBukkitEntity());
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
