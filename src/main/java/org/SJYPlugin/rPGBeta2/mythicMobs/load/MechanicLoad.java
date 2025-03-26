package org.SJYPlugin.rPGBeta2.mythicMobs.load;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.mythic.core.skills.SkillExecutor;
import org.SJYPlugin.rPGBeta2.mythicMobs.mechanics.MythicDamageMechanic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;

public class MechanicLoad implements Listener {

    @EventHandler
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event)	{
//        PluginLogger.getLogger().info("MythicMechanicLoadEvent called for mechanic " + event.getMechanicName());

        if(event.getMechanicName().equalsIgnoreCase("CUSTOMDAMAGE"))	{
            event.register(new MythicDamageMechanic(event.getContainer().getManager(), event.getContainer().getFile(), "", event.getConfig()));
//            Bukkit.getLogger().info();
        } else if(event.getMechanicName().equalsIgnoreCase("rootdamagebuff")) {
            event.register(new MythicDamageMechanic(event.getContainer().getManager(), event.getContainer().getFile(), "", event.getConfig()));
        } else if(event.getMechanicName().equalsIgnoreCase("rootdamagebuff")) {
            event.register(new MythicDamageMechanic(event.getContainer().getManager(), event.getContainer().getFile(), "", event.getConfig()));
        }

    }

}
