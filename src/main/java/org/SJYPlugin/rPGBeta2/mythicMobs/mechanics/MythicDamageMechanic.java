package org.SJYPlugin.rPGBeta2.mythicMobs.mechanics;


import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.damage.DamagingMechanic;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.evp.DamageApplyEVPmain;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.evp.DamageComputeEVPmain;
import org.SJYPlugin.rPGBeta2.control.hpcontrol.HPControl;
import org.SJYPlugin.rPGBeta2.data.damage.DamageData;
import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
import org.SJYPlugin.rPGBeta2.data.damage.PutDamageModifiers;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.SJYPlugin.rPGBeta2.util.persistantdata.DamageCausePersistantData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;

@MythicMechanic(author = "SJY", name = "customdamage")
public class MythicDamageMechanic extends DamagingMechanic implements ITargetedEntitySkill {

    DamageComputeEVPmain damageComputeEVPmain = DamageComputeEVPmain.getInstance();
    DamageApplyEVPmain damageApplyEVPmain = DamageApplyEVPmain.getInstance();
    DamageCausePersistantData damageCausePersistantData = DamageCausePersistantData.getInstance();
    PutDamageModifiers putDamageModifiers = PutDamageModifiers.getInstance();

    private final int DamageMag;
    private final String DamageBaseType;
    private final String DamageStemType;
    private final String DamageAttribute;
    private final String DamageRootType;
    private final boolean TrueDamage;

    public MythicDamageMechanic(SkillExecutor manager, File file, String line, MythicLineConfig config) {
        super(manager, file, line, config);
        this.DamageMag = config.getInteger("magnification", 0);
        this.DamageBaseType = config.getString("damage-base-type", "ATTACK");
        this.DamageStemType = config.getString("damage-stem-type", "NORMAL");
        this.DamageAttribute = config.getString("damage-attribute", "PHYSICS");
        this.DamageRootType = config.getString("damage-root-type", "NORMAL");
        this.TrueDamage = config.getBoolean("true-damage", false);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {

        return MythicMobDamage(data, target);
    }

    public SkillResult MythicMobDamage(SkillMetadata data, AbstractEntity target) {
        if(!target.isValid()) {
            return SkillResult.INVALID_TARGET;
        }
        if (target.isDead() || (target
                .isLiving() && target.getHealth() <= 0.0D))
            return SkillResult.INVALID_TARGET;

        DamageModifiers damageModifiers = putDamageModifiers.putDamageModifiers(DamageData.getInstance().getDefaultDamageModifiers(),
                null, null, 0,
                DamageBaseType, DamageStemType, DamageRootType, DamageAttribute, false);

        double VirtualFinalDamage;
        if(target.isLiving()) {
            LivingEntity livingEntity = (LivingEntity) target.getBukkitEntity();
            if(target.isPlayer()) {
                Player offender = (Player) livingEntity;
                LivingEntity attacker = (LivingEntity) data.getCaster().getEntity().getBukkitEntity();
                damageModifiers.setAttacker(attacker);
                damageModifiers.setOffender(offender);
                damageModifiers.setFinalDamage(damageComputeEVPmain.FinalDamage(damageModifiers));
                double RealDamage = damageApplyEVPmain.DamageEVP(damageModifiers);
                if(RealDamage > 0.0D) {
                    damageCausePersistantData.addData(attacker, "CustomDamageCause_EVP", "SKILL_DAMAGE");
                    DamageApplying(data, target, RealDamage);
                }
            } else {

            }
        }
        return SkillResult.SUCCESS;
    }

    private void DamageApplying(SkillMetadata data, AbstractEntity target, double damage) {
        doDamage(data, target, damage);
    }

}
