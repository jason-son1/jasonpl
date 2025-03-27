package org.SJYPlugin.rPGBeta2.data.damage;

import org.SJYPlugin.rPGBeta2.data.AttributeData;
import org.bukkit.entity.LivingEntity;

public class PutDamageModifiers {

    private static final PutDamageModifiers instance = new PutDamageModifiers();

    public static PutDamageModifiers getInstance() {
        return instance;
    }

    DamageData damageData = DamageData.getInstance();
    AttributeData attributeData = AttributeData.getInstance();

    public DamageModifiers putDamageModifiers(DamageModifiers damageModifiers, LivingEntity attacker, LivingEntity offender,
                                              double finalDamage, String baseType, String stemType,
                                              String rootType, String attribute, boolean isCritical) {
        damageModifiers.setAttacker(attacker);
        damageModifiers.setOffender(offender);
        if(finalDamage < 0) {
            finalDamage = 0;
        }
        damageModifiers.setFinalDamage(finalDamage);
        if(damageData.getDamgeTypeSet("DamageBaseType").contains(baseType)) {
            damageModifiers.setBaseType(baseType);
        } else {
            damageModifiers.setBaseType("NULL");
        }

        if(damageData.getDamgeTypeSet("DamageStemType").contains(stemType)) {
            damageModifiers.setStemType(stemType);
        } else {
            damageModifiers.setStemType("NULL");
        }

        if(damageData.getDamgeTypeSet("DamageRootType").contains(rootType)) {
            damageModifiers.setRootType(rootType);
        } else {
            damageModifiers.setRootType("NULL");
        }

        if(attributeData.getAttList().contains(attribute)) {
            damageModifiers.setAttribute(attribute);
        } else {
            damageModifiers.setAttribute("NULL");
        }

        damageModifiers.setCritical(isCritical);

        return damageModifiers;
    }

}
