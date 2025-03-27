package org.SJYPlugin.rPGBeta2.listeners;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.api.mobs.entities.MythicEntity;
import io.lumine.mythic.bukkit.events.MythicMobSpawnEvent;
import net.kyori.adventure.text.Component;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.control.MobDataControlGet;
import org.SJYPlugin.rPGBeta2.mythicMobs.mob.mobdata.MobData;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicMobSpawnListener implements Listener {

    MobData mobData = MobData.getInstance();

    @EventHandler
    public void onMythicMobSpawn(MythicMobSpawnEvent event) {
        if(event.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) event.getEntity();
            mobData.DefaultSetting_Mythic(livingEntity);
            Bukkit.getServer().sendMessage(Component.text("확인" + mobData.getMobDef(livingEntity)));
        } else {
            Bukkit.getServer().sendMessage(Component.text("비확인"));
        }
//        MythicMob mythicMob = MobDataControlGet.getInstance().MythicMobCheck((LivingEntity) event.getEntity());
//        if(mythicMob != null) {
//            mobData.DefaultSetting_Mythic((LivingEntity) event.getEntity());
//            Bukkit.getServer().sendMessage(Component.text("확인"));
//        } else {
//            Bukkit.getServer().sendMessage(Component.text("비확인"));
//        }
    }

}
