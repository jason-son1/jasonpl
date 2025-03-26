package org.SJYPlugin.rPGBeta2.data;

import java.util.Arrays;
import java.util.List;

public class ClassData {

    private static final ClassData instance = new ClassData();

    public static ClassData getInstance() {
        return instance;
    }

    private static final String Class_Knight = "KNIGHT";
    private static final String Class_Wizard = "WIZARD";
    private static final String Class_Assassin = "ASSASSIN";
    private static final String Class_Archer = "ARCHER";
    private static final String Class_Bard = "BARD";

    public List<String> ClassList() {
        return Arrays.asList(Class_Knight, Class_Wizard, Class_Assassin, Class_Archer, Class_Bard);
    }

}
