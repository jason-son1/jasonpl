package org.SJYPlugin.rPGBeta2.util.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class ConfigUtilStat {

    private final static ConfigUtilStat instance = new ConfigUtilStat();

    private static final Map<UUID, YamlConfiguration> mainConfigMap = new HashMap<UUID, YamlConfiguration>();


    private final File FileLocation_1 = new File("plugins/rPGBeta2");
    private final File FileLocation_2 = new File("plugins/rPGBeta2/Status");

    public void setupFirst(Player player) {
        File file = new File("plugins/rPGBeta2/Status", player.getUniqueId() + "-stat.yml");
        YamlConfiguration subconfig = YamlConfiguration.loadConfiguration(file);
        subconfig.options().parseComments(true);

        if (!file.exists()) {
            try{
                if (!FileLocation_1.exists()) {
                    FileLocation_1.mkdir();
                }
                if (!FileLocation_2.exists()) {
                    FileLocation_2.mkdir();
                }
                file.createNewFile();

                subconfig.set("PlayerName", player.getName());
                subconfig.set("PlayerUUID", player.getUniqueId().toString());

                subconfig.set("Player-static-class", "None");

                subconfig.set("Player-highstat-Level", 0);
                subconfig.set("PlayerStat-highstat-MaxHP", 20);
                subconfig.set("PlayerStat-highstat-MaxMP", 20);
                subconfig.set("PlayerStat-highstat-Atc", 0);
                subconfig.set("PlayerStat-highstat-AttMastery", 0);


                subconfig.set("Player-runstat-Exp", 0);
                subconfig.set("Player-runstat-MaxExp", 100);
                subconfig.set("Player-runstat-StatPoint", 0);
                subconfig.set("Player-runstat-StatPointUsed", 0);
                subconfig.set("PlayerStat-runstat-HP", 20);
                subconfig.set("PlayerStat-runstat-MP", 20);
                subconfig.set("PlayerStat-runstat-Shilled", 0);
                subconfig.set("PlayerStat-runstat-beginAtc", 0);
                subconfig.set("PlayerStat-runstat-addAtc", 0);


                subconfig.set("PlayerStat-lowstat-Str", 0);
                subconfig.set("PlayerStat-lowstat-Dex", 0);
                subconfig.set("PlayerStat-lowstat-Int", 0);
                subconfig.set("PlayerStat-lowstat-Def", 0);
                subconfig.set("PlayerStat-lowstat-Luc", 0);
                subconfig.set("PlayerStat-lowstat-CriPer", 0);
                subconfig.set("PlayerStat-lowstat-CriDam", 100);
                subconfig.set("PlayerStat-lowstat-AtcSpd", 100);
                subconfig.set("PlayerStat-lowstat-MoveSpd", 100);
                subconfig.set("PlayerStat-lowstat-IgnDef", 0);


                subconfig.set("PlayerStat-attrstat-FireAttUp", 0);
                subconfig.set("PlayerStat-attrstat-WaterAttUp", 0);
                subconfig.set("PlayerStat-attrstat-EarthAttUp", 0);
                subconfig.set("PlayerStat-attrstat-WindAttUp", 0);
                subconfig.set("PlayerStat-attrstat-LightAttUp", 0);
                subconfig.set("PlayerStat-attrstat-DarkAttUp", 0);
                subconfig.set("PlayerStat-attrstat-PhysicsAttUp", 0);

                subconfig.set("PlayerStat-attrstat-FireAttResist", 0);
                subconfig.set("PlayerStat-attrstat-WaterAttResist", 0);
                subconfig.set("PlayerStat-attrstat-EarthAttResist", 0);
                subconfig.set("PlayerStat-attrstat-LightAttResist", 0);
                subconfig.set("PlayerStat-attrstat-WindAttResist", 0);
                subconfig.set("PlayerStat-attrstat-DarkAttResist", 0);
                subconfig.set("PlayerStat-attrstat-PhysicsAttResist", 0);




                subconfig.set("PlayerStat-Money-money", 0);

                subconfig.save(file);

            } catch (Exception e) {
                System.out.println("에러: 스텟 파일 생성부분");
                throw new RuntimeException(e);
            }
        }
    }

    public void ConfigMapLoad(Player player) {
        try {
            File EachConfigfile = new File("plugins/rPGBeta2/Status", player.getUniqueId() + "-stat.yml");
            YamlConfiguration eachconfig = new YamlConfiguration();
            try {
                eachconfig.load(EachConfigfile);
                eachconfig.options().parseComments(true);
                mainConfigMap.put(player.getUniqueId(), eachconfig);
            } catch (Exception e) {
                System.out.println("에러: 스텟 파일 로드부분");
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println("에러: 스텟 파일 로드부분");
            throw new RuntimeException(e);
        }
    }

    public YamlConfiguration getStatConfig(Player player) {
        return mainConfigMap.get(player.getUniqueId());
    }

    public void Allreload() {
        try {
            for(Player player : Bukkit.getOnlinePlayers()) {
               ConfigMapLoad(player);
            }
        } catch (Exception e) {
            System.out.println("에러: 스텟 파일 리로드 부분");
            throw new RuntimeException(e);
        }
    }


    public boolean contains(Player player, String path) {
        return mainConfigMap.get(player.getUniqueId()).contains(path);
    }

    public String getString(Player player, String path) {
        return mainConfigMap.get(player.getUniqueId()).getString(path);
    }

    public Double getDouble(Player player, String path) {
        return mainConfigMap.get(player.getUniqueId()).getDouble(path);
    }

    public int getInt(Player player, String path) {
        return mainConfigMap.get(player.getUniqueId()).getInt(path);
    }

    public void set(Player player, String path, Object value) {
        YamlConfiguration tempconfig = new YamlConfiguration();
        try {
            File tempConfigfile = new File("plugins/rPGBeta2/Status", player.getUniqueId() + "-stat.yml");
            try {
                tempconfig.load(tempConfigfile);
                tempconfig.set(path, value);
                tempconfig.save(tempConfigfile);
                mainConfigMap.put(player.getUniqueId(), tempconfig);
            } catch (Exception e) {
                System.out.println("에러: 스텟 파일 set부분");
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void DoubleSetting(Player player, String path, Object value) {
        Double value1 = 0.0;

        if (value instanceof Double) {
            value1 = (Double) value;
        } else if (value instanceof String) {
            try {
                value1 = Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                Bukkit.getLogger().warning("Invalid double format at: " + path);
            }
        } else {
            Bukkit.getLogger().warning("Unexpected value type at: " + path);
        }
        set(player, path, value1);
    }

    public void StringSetting(Player player, String path, Object value) {
        String value1 = "";
        try {
            value1 = String.valueOf(value);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid number format.");
        }
        set(player, path, value1);
    }

    public void IntSetting(Player player, String path, Object value) {
        Integer value1 = 0;
        try {
            value1 = Integer.valueOf((String) value);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid number format.");
        }
        set(player, path, value1);
    }



    public Set<String> getAllKeys(Player player) {
        return mainConfigMap.get(player.getUniqueId()).getKeys(false);
    }

    public static ConfigUtilStat getInstance() {
        return instance;
    }




}