package org.SJYPlugin.rPGBeta2.data;

import java.util.Set;

public class MobTypeData {

    private static final MobTypeData instance = new MobTypeData();

    public static MobTypeData getInstance() {
        return instance;
    }

    private static final String MobType_Normal = "NORMAL";
    private static final String MobType_Elite = "ELITE";
    private static final String MobType_Boss = "BOSS";
    private static final String MobType_Ultimate = "ULTIMATE";

    private static final String PlayerType_Normal = "NORMAL";

    public static final Set<String> MobType = Set.of(MobType_Normal, MobType_Elite, MobType_Boss, MobType_Ultimate);

    public static final Set<String> PlayerType = Set.of(PlayerType_Normal);

    public Set<String> getMobTypeSet(String type) {
        if(type.equalsIgnoreCase("MobType")) {
            return MobType;
        } else if(type.equalsIgnoreCase("PlayerType")) {
            return PlayerType;
        } else {
            return Set.of();
        }
    }

    public String getMobType(String hightype, String lowtype) {
        Set<String> HighT = getMobTypeSet(hightype);
        for(String str : HighT) {
            if(str.equalsIgnoreCase(lowtype)) {
                return str;
            } else {
                continue;
            }
        }
        return null;
    }

    public boolean isMobType(String type) {
        return MobType.contains(type);
    }

}
