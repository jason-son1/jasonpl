package org.SJYPlugin.rPGBeta2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class StandSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            entity.setCustomName("Stand");
            entity.setAI(false);
            entity.setPersistent(true);
            entity.setHealth(20);

        } else {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        return false;
    }

}
