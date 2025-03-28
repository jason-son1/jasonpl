package org.SJYPlugin.rPGBeta2.data.attribute;

import java.util.Set;

public class AttributeData {

    private static final AttributeData instance = new AttributeData();

    public static AttributeData getInstance() {
        return instance;
    }

    private static final String Att_Fire = "FIRE";
    private static final String Att_Water = "WATER";
    private static final String Att_Earth = "EARTH";
    private static final String Att_Wind = "WIND";
    private static final String Att_Light = "LIGHT";
    private static final String Att_Dark = "DARK";

    private static final String Att_Physics = "PHYSICS";

    public String getAttFire() {
        return Att_Fire;
    }

    public String getAttWater() {
        return Att_Water;
    }

    public String getAttEarth() {
        return Att_Earth;
    }

    public String getAttWind() {
        return Att_Wind;
    }

    public String getAttLight() {
        return Att_Light;
    }

    public String getAttDark() {
        return Att_Dark;
    }

    public String getAttPhy() {
        return Att_Physics;
    }

    public Set<String> getAttList() {
        return Set.of(Att_Fire, Att_Water, Att_Earth, Att_Wind, Att_Light, Att_Dark, Att_Physics);
    }





}
