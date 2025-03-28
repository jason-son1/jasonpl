package org.SJYPlugin.rPGBeta2.data.attribute;

import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class AttributeModifiers {

    private String attributeType;
    private int attributeValue;
    private long timestamp;
    private UUID CauseEntity;
    private String cause;
    private int GaugeValue;

    public AttributeModifiers(String attributeType, long timestamp, LivingEntity livingEntity, String cause,
                              int ApplyGaugeValue, int attributeValue) {
        this.attributeType = attributeType;
        this.attributeValue = attributeValue;
        this.timestamp = timestamp;
        this.CauseEntity = livingEntity.getUniqueId();
        this.cause = cause;
        this.GaugeValue = ApplyGaugeValue;

    }

    public String getAttributeType() {
        return attributeType;
    }

    public int getAttributeValue() {
        return attributeValue;
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

    public void setAttributeName(String attributeType) {
        this.attributeType = attributeType;
    }

    public void setAttributeValue(int attributeValue) {
        this.attributeValue = attributeValue;
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
