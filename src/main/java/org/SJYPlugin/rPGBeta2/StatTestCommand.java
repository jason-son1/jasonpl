package org.SJYPlugin.rPGBeta2;

import net.md_5.bungee.api.chat.BaseComponent;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StatTestCommand implements CommandExecutor, TabExecutor {

    ConfigUtilStat configUtilStat = new ConfigUtilStat();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length < 2) {
                configUtilStat.Allreload();
                return true;
            }

            if(args.length == 2) {
                Player target = player.getServer().getPlayer(args[0]);
                if(target == null) {
                    player.sendMessage("Player not found.");
                    return true;
                }
                String path = args[1];
                if(configUtilStat.contains(target, path)){
                    Double value = configUtilStat.getDouble(target, path);
                    player.sendMessage(path + " 의 값은 " + value + " 입니다.");
                } else {
                    player.sendMessage("path를 찾지못함.");
                }
            }
            else if(args.length == 3) {
                Player target = player.getServer().getPlayer(args[0]);
                if(target == null) {
                    player.sendMessage("Player not found.");
                    return true;
                }
                String path = args[1];
                String value = args[2];
                configUtilStat.DoubleSetting(target, path, value);
                player.sendMessage(path + " 의 값을 " + value + " 로 설정하였습니다.");
            }
            else {
                return true;
            }


        } else {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        Player target = sender.getServer().getPlayer(args[0]);

        if(args.length == 1) {
            List<String> players = new ArrayList<>();
            for(Player player : sender.getServer().getOnlinePlayers()) {
                players.add(player.getName());
            }
            return players;
        }

        if(args.length == 2) {
            List<String> paths = new ArrayList<>();
            for(String path : configUtilStat.getAllKeys(target)) {
                paths.add(path);
            }
            return paths;
        }

        return new ArrayList<>();
    }
}
