package org.SJYPlugin.rPGBeta2.util.hologram;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.SJYPlugin.rPGBeta2.RPGBeta2;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DamageHologram {

    private static final DamageHologram instance = new DamageHologram();

    public static DamageHologram getInstance() {
        return instance;
    }

    public void spawnArmorStand_noCri(Location loc, double damage, long ticks, LivingEntity attacker) {

        Vector vector = attacker.getLocation().getDirection().normalize().setY(1).multiply(-0.2);

        Vector vector1 = attacker.getLocation().getDirection().normalize().setX(0).setZ(0).setY(0.15);

        ArmorStand armorStand = loc.getWorld().spawn(loc.add(vector).subtract(0, 1.2, 0), ArmorStand.class);
        armorStand.setVisible(false);

        armorStand.setVelocity(vector1);

        armorStand.setGravity(true);
        armorStand.setInvulnerable(true);

        armorStand.setCustomNameVisible(true);

        String format = String.format("%.1f", damage);

        armorStand.customName(Component.text(format, NamedTextColor.YELLOW));

        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(RPGBeta2.getInstance(), ticks);
    }

    public void spawnArmorStand_Cri(Location loc, double damage, long ticks, LivingEntity attacker) {

        Vector vector = attacker.getLocation().getDirection().normalize().setY(1).multiply(-0.2);

        Vector vector1 = attacker.getLocation().getDirection().normalize().setX(0).setZ(0).setY(0.15);

        ArmorStand armorStand = loc.getWorld().spawn(loc.add(vector).subtract(0, 1.2, 0), ArmorStand.class);
        armorStand.setVisible(false);

        armorStand.setVelocity(vector1);

        armorStand.setGravity(true);
        armorStand.setInvulnerable(true);

        armorStand.setCustomNameVisible(true);

        String format = String.format("%.1f", damage);

        armorStand.customName(Component.text(format, NamedTextColor.YELLOW).decoration(TextDecoration.BOLD, true));

        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(RPGBeta2.getInstance(), ticks);
    }


    public void DamageDisplay_noCri(LivingEntity livingEntity, double damage, LivingEntity attacker) {
        spawnArmorStand_noCri(livingEntity.getLocation(), damage, 12, attacker);
    }

    public void DamageDisplay_Cri(LivingEntity livingEntity, double damage, LivingEntity attacker) {
        spawnArmorStand_Cri(livingEntity.getLocation(), damage, 12, attacker);
    }



}
