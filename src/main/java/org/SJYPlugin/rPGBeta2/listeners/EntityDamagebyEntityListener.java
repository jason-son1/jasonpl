package org.SJYPlugin.rPGBeta2.listeners;

import org.SJYPlugin.rPGBeta2.control.damagecontrol.evp.PlainHitApplyEVP;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.plainhit.PlainHitSpeed;
import org.SJYPlugin.rPGBeta2.control.damagecontrol.pve.PlainHitApplyPVE;
import org.SJYPlugin.rPGBeta2.control.hpcontrol.HPControl;
import org.SJYPlugin.rPGBeta2.data.damage.DamageModifiers;
import org.SJYPlugin.rPGBeta2.util.persistantdata.DamageCausePersistantData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagebyEntityListener implements Listener {

    DamageCausePersistantData damageCausePersistantData = DamageCausePersistantData.getInstance();
    PlainHitSpeed plainHitSpeed = PlainHitSpeed.getInstance();
    HPControl hpControl = HPControl.getInstance();

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            if(event.getEntity() instanceof Player) {
                hpControl.HealthSetPlayer((Player) event.getEntity());
                PVPDamaging(event ,attacker, (Player) event.getEntity());
            } else if (event.getEntity() instanceof LivingEntity) {
                PVEDamaging(event ,attacker, (LivingEntity) event.getEntity());
            }
        } else if (event.getDamager() instanceof LivingEntity) {
            if(event.getEntity() instanceof Player) {
                hpControl.HealthSetPlayer((Player) event.getEntity());
                Player offender = (Player) event.getEntity();
                EVPDamaging(event, (LivingEntity) event.getDamager(), offender);
            } else if (event.getEntity() instanceof LivingEntity) {

            }
        }
    }

    private void PVEDamaging(EntityDamageByEntityEvent event, Player attacker, LivingEntity entity) {
        if(damageCausePersistantData.containsData(attacker, "CustomDamageCause_PVE")) {
            if(damageCausePersistantData.getData(attacker, "CustomDamageCause_PVE") != null) {
                if(damageCausePersistantData.getData(attacker, "CustomDamageCause_PVE").equalsIgnoreCase("SPELL_DAMAGE")) {
                    event.setDamage(0);
                    damageCausePersistantData.removeData(attacker, "CustomDamageCause_PVE");
                }
            } else {
                if(plainHitSpeed.PlayerPlainHitSpeed(attacker)) {
                    event.setDamage(0);
                    PlainHitApplyPVE.getInstance().PlainHitDamagePVE(new DamageModifiers(attacker,
                            entity, 0, 100, "ATTACK", "NORMAL",
                            "NORMAL", "PHYSICS", false));

                } else {
                    event.setCancelled(true);
                }
            }
        } else {
            if(plainHitSpeed.PlayerPlainHitSpeed(attacker)) {
                event.setDamage(0);
                PlainHitApplyPVE.getInstance().PlainHitDamagePVE(new DamageModifiers(attacker,
                        entity, 0, 100, "ATTACK", "NORMAL",
                        "NORMAL", "PHYSICS", false));
            } else {
                event.setCancelled(true);
            }
        }
    }

    private void PVPDamaging(EntityDamageByEntityEvent event, Player attacker, Player offender) {
        if(damageCausePersistantData.containsData(attacker, "CustomDamageCause_PVP")) {
            if(damageCausePersistantData.getData(attacker, "CustomDamageCause_PVP") != null) {
                if(damageCausePersistantData.getData(attacker, "CustomDamageCause_PVP").equalsIgnoreCase("SPELL_DAMAGE")) {
                    event.setDamage(0);
                    damageCausePersistantData.removeData(attacker, "CustomDamageCause_PVP");
                }
            } else {
                if(plainHitSpeed.PlayerPlainHitSpeed(attacker)) {
                    event.setDamage(0);
                    PlainHitApplyPVE.getInstance().PlainHitDamagePVE(new DamageModifiers(attacker,
                            offender, 0, 100, "ATTACK", "NORMAL",
                            "NORMAL", "PHYSICS", false));
                }
            }
        } else {
            if(plainHitSpeed.PlayerPlainHitSpeed(attacker)) {
                event.setDamage(0);
                PlainHitApplyPVE.getInstance().PlainHitDamagePVE(new DamageModifiers(attacker,
                        offender, 0, 100, "ATTACK", "NORMAL",
                        "NORMAL", "PHYSICS", false));
            }
        }
    }

    private void EVPDamaging(EntityDamageByEntityEvent event, LivingEntity attacker, Player entity) {
        if(damageCausePersistantData.containsData(attacker, "CustomDamageCause_EVP")) {
            if(damageCausePersistantData.getData(attacker, "CustomDamageCause_EVP") != null) {
                if(damageCausePersistantData.getData(attacker, "CustomDamageCause_EVP").equalsIgnoreCase("SKILL_DAMAGE")) {
                    event.setDamage(0);
                    damageCausePersistantData.removeData(attacker, "CustomDamageCause_EVP");
                }
            } else {
                event.setDamage(0);
                PlainHitApplyEVP.getInstance().PlainHitDamageEVP(new DamageModifiers(attacker,
                        entity, 0, 100, "ATTACK", "NORMAL",
                        "NORMAL", "PHYSICS", false));
            }
        } else {
            event.setDamage(0);
            PlainHitApplyEVP.getInstance().PlainHitDamageEVP(new DamageModifiers(attacker,
                    entity, 0, 100, "ATTACK", "NORMAL",
                    "NORMAL", "PHYSICS", false));
        }
    }


}
