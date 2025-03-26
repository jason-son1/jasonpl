package org.SJYPlugin.rPGBeta2.control.hpcontrol;

import io.lumine.mythic.api.mobs.MythicMob;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.control.MobDataControlGet;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata.MobData;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat;
import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HPControl {

    private static final HPControl instance = new HPControl();

    public static HPControl getInstance() {
        return instance;
    }

    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();
    MobDataControlGet mobDataControlGet = MobDataControlGet.getInstance();
    MobData mobData = MobData.getInstance();

    public void HealthSetPlayer(Player player) {

        ConfigUtilStat.getInstance().ConfigMapLoad(player);

        double RealMaxHealth = player.getHealthScale();

        double VirtualMaxHealth = configUtilStat2.getMaxHP(player);
        double VirtualHealth = configUtilStat2.getHP(player);

        double RatioHP = VirtualHealth / VirtualMaxHealth;

        player.setHealthScale(20);
        player.setHealth(RealMaxHealth*RatioHP);
    }

    public void HealthSetEntity_Mythic(LivingEntity entity) {

        MythicMob mythicMob = mobDataControlGet.MythicMobCheck(entity);

        if(mythicMob != null) {
            double RealMaxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

            double VirtualMaxHealth = mobData.getMobMaxHealth(entity);
            double VirtualHealth = mobData.getMobCurrHealth(entity);

            double RatioHP = VirtualHealth / VirtualMaxHealth;

            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(RealMaxHealth);
            entity.setHealth(RealMaxHealth*RatioHP);
        } else {

        }
    }




}
