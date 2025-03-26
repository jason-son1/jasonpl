package org.SJYPlugin.rPGBeta2.util.persistantdata;

import org.SJYPlugin.rPGBeta2.RPGBeta2;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageCausePersistantData {

    private static final DamageCausePersistantData instance = new DamageCausePersistantData();

    public static DamageCausePersistantData getInstance() {
        return instance;
    }

    private final String Custom_PVP = "CustomDamageCause_PVP";
    private final String Custom_PVE = "CustomDamageCause_PVP";

    public void addData(LivingEntity entity, String key, String damageCause) {
        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getProvidingPlugin(RPGBeta2.class), key);
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(namespacedKey, PersistentDataType.STRING, damageCause);
    }

    public boolean containsData(LivingEntity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getProvidingPlugin(RPGBeta2.class), key);
        return entity.getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING);
    }

    public String getData(LivingEntity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getProvidingPlugin(RPGBeta2.class), key);
        if (containsData(entity, key)) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            return pdc.get(namespacedKey, PersistentDataType.STRING);
        } else {
            return null;
        }
    }

    public void removeData(LivingEntity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getProvidingPlugin(RPGBeta2.class), key);
        entity.getPersistentDataContainer().remove(namespacedKey);
    }


}
