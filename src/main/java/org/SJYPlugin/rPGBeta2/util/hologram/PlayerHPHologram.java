package org.SJYPlugin.rPGBeta2.util.hologram;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.SJYPlugin.rPGBeta2.RPGBeta2;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerHPHologram {

    private static final PlayerHPHologram instance = new PlayerHPHologram();

    public static PlayerHPHologram getInstance() {
        return instance;
    }

    public void spawnArmorStand(Location loc, double value, long ticks) {

        Vector vector = loc.getDirection().normalize().setY(1).setX(0).setZ(0).multiply(0.2);

        Vector vector1 = loc.getDirection().normalize().setX(0).setZ(0).setY(0.15).multiply(0.85);

        ArmorStand armorStand = loc.getWorld().spawn(loc.add(vector).subtract(0, 1.2, 0), ArmorStand.class);
        armorStand.setVisible(false);

        armorStand.setVelocity(vector1);

        armorStand.setGravity(true);
        armorStand.setInvulnerable(true);

        armorStand.setCustomNameVisible(true);

        String format = String.format("%.1f", value);

        armorStand.customName(Component.text(format, NamedTextColor.GRAY));

        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(RPGBeta2.getInstance(), ticks);
    }

    public void PlayerHPHologramSpawn(Player player, double value) {
        spawnArmorStand(player.getLocation(), value, 10);
    }

}
