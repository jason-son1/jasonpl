package org.SJYPlugin.rPGBeta2.magicSpells.target;

import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.util.ValidTargetList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class CustomValidTarget {

    private static final CustomValidTarget instance = new CustomValidTarget();

    public static CustomValidTarget getInstance() {
        return instance;
    }

    public boolean IsValidTarget(LivingEntity caster, Entity target) {
        if(caster instanceof Player) {
            if(target instanceof Player) {
                return toPlayer((Player) target);
            } else if(target instanceof LivingEntity) {
                return toLivingEntity((LivingEntity) target);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean toPlayer(Player target) {
        return true;
    }

    private boolean toLivingEntity(LivingEntity target) {
        return true;
    }


}
