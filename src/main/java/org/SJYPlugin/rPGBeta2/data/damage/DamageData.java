package org.SJYPlugin.rPGBeta2.data.damage;

import java.util.Set;

public class DamageData {

    private static final DamageData instance = new DamageData();

    private static final String DamageStemType_NormalDamage = "NORMAL";
    private static final String DamageStemType_NormalSkillDamage = "NORMALSKILL";
    private static final String DamageStemType_PassiveSkillDamage = "PASSIVESKILL";
    private static final String DamageStemType_SpecialSkillDamage = "SPECIALSKILL";
    private static final String DamageStemType_UltimateSkillDamage = "ULTIMATESKILL";

    private static final String DamageRootType_NormalSingleAttack = "NORMAL";
    private static final String DamageRootType_NormalRangeAttack = "RANGE";
    private static final String DamageRootType_DistributeAttack = "DISTRIBUTE";
    private static final String DamageRootType_DotAttack = "DOT";
    private static final String DamageRootType_SpreadAttack = "SPREAD";
    private static final String DamageRootType_AdditionalAttack = "ADDITIONAL";

    public double BaseCorrectionCONSTANT_Attack = 1;
    public double BaseCorrectionCONSTANT_HP = 0.1;
    public double BaseCorrectionCONSTANT_Def = 0.5;
    public double BaseCorrectionCONSTANT_ETC = 1.0;

    private static final String DamageBaseType_Attack = "ATTACK";
    private static final String DamageBaseType_Defense = "DEF";
    private static final String DamageBaseType_Health = "MAXHP";
    private static final String DamageBaseType_Etc = "ETC";

    private static final String DamageCauseType_SpellDamage = "SPELL_DAMAGE";
    private static final String DamageCauseType_SkillDamage = "SKILL_DAMAGE";
    private static final String DamageCauseType_WeaponDamage = "WEAPON_DAMAGE";

    private static final DamageModifiers DefaultDamageModifiers = new DamageModifiers(null, null, 0,
            0, "NULL", "NULL", "NULL", "NULL", false);

    public static final Set<String> DamageRootType = Set.of(DamageRootType_NormalSingleAttack,
            DamageRootType_NormalRangeAttack, DamageRootType_DistributeAttack, DamageRootType_DotAttack, DamageRootType_SpreadAttack,
            DamageRootType_AdditionalAttack);

    public static final Set<String> DamageStemType = Set.of(DamageStemType_NormalDamage, DamageStemType_NormalSkillDamage,
            DamageStemType_PassiveSkillDamage, DamageStemType_SpecialSkillDamage, DamageStemType_UltimateSkillDamage);


    public static final Set<String> DamageBaseType = Set.of(DamageBaseType_Attack, DamageBaseType_Defense,
            DamageBaseType_Health, DamageBaseType_Etc);

    public Set<String> getDamgeTypeSet(String type) {
        if(type.equalsIgnoreCase("DamageRootType")) {
            return DamageRootType;
        } else if(type.equalsIgnoreCase("DamageStemType")) {
            return DamageStemType;
        } else if(type.equalsIgnoreCase("DamageBaseType")) {
            return DamageBaseType;
        } else {
            return Set.of();
        }
    }

    public String getDamageType(String hightype, String lowtype) {
        Set<String> HighT = getDamgeTypeSet(hightype);
        for(String str : HighT) {
            if(str.equalsIgnoreCase(lowtype)) {
                return str;
            } else {
                return "";
            }
        }
        return "";
    }

    public DamageModifiers getDefaultDamageModifiers() {
        return DefaultDamageModifiers;
    }



    public static DamageData getInstance() {
        return instance;
    }



}
