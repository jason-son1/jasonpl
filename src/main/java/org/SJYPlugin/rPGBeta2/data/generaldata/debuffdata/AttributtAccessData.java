package org.SJYPlugin.rPGBeta2.data.generaldata.debuffdata;

import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class AttributtAccessData {

    private String attributeName;
    private long timestamp;
    private UUID CauseEntity;
    private String cause;
    private int GaugeValue;

    public AttributtAccessData(String attributeName, long timestamp, LivingEntity livingEntity, String cause, int GaugeValue) {
        this.attributeName = attributeName;
        this.timestamp = timestamp;
        this.CauseEntity = livingEntity.getUniqueId();
        this.cause = cause;
        this.GaugeValue = GaugeValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public UUID getCauseEntity() {
        return CauseEntity;
    }

    public String getCause() {
        return cause;
    }

    public int getGaugeValue() {
        return GaugeValue;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setCauseEntity(UUID causeEntity) {
        this.CauseEntity = causeEntity;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setGaugeValue(int GaugeValue) {
        this.GaugeValue = GaugeValue;
    }


}
