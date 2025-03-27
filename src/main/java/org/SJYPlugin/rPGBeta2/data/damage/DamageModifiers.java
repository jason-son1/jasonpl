package org.SJYPlugin.rPGBeta2.data.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageModifiers {

    private LivingEntity attacker;
    private LivingEntity offender;
    private double finalDamage;
    private int magnification;
    private String baseType;
    private String stemType;
    private String rootType;
    private String attribute;
    private boolean isCritical;
    private long timestamp;

    public DamageModifiers(LivingEntity attacker, LivingEntity offender, double finalDamage, int magnification,
                           String baseType, String stemType, String rootType, String attribute, boolean isCritical) {
        this.attacker = attacker;
        this.offender = offender;
        this.baseType = baseType;
        this.stemType = stemType;
        this.rootType = rootType;
        this.attribute = attribute;
        this.isCritical = isCritical;
        this.timestamp = System.currentTimeMillis();
        this.finalDamage = finalDamage;
        this.magnification = magnification;
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public Player getAttackerPlayer() {
        if(this.attacker instanceof Player) {
            return (Player) this.attacker;
        }
        return null;
    }

    public LivingEntity getOffender() {
        return offender;
    }

    public Player getOffenderPlayer() {
        if(this.offender instanceof Player) {
            return (Player) this.offender;
        }
        return null;
    }

    public double getFinalDamage() {
        return finalDamage;
    }

    public int getMagnification() {
        return magnification;
    }

    public String getBaseType() {
        return baseType;
    }

    public String getStemType() {
        return stemType;
    }

    public String getRootType() {
        return rootType;
    }

    public String getAttribute() {
        return attribute;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isPVP() {
        if(this.attacker instanceof Player && this.offender instanceof Player) {
            return true;
        }
        return false;
    }

    public boolean isPVE() {
        if(this.attacker instanceof Player && !(this.offender instanceof Player)) {
            return true;
        }
        return false;
    }

    public boolean isEVP() {
        if(!(this.attacker instanceof Player) && this.offender instanceof Player) {
            return true;
        }
        return false;
    }



    public void setAttacker(LivingEntity attacker) {
        this.attacker = attacker;
    }

    public void setOffender(LivingEntity offender) {
        this.offender = offender;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public void setStemType(String stemType) {
        this.stemType = stemType;
    }

    public void setRootType(String rootType) {
        this.rootType = rootType;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setCritical(boolean isCritical) {
        this.isCritical = isCritical;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setFinalDamage(double finalDamage) {
        this.finalDamage = finalDamage;
    }

    public void setMagnification(int magnification) {
        this.magnification = magnification;
    }

}
