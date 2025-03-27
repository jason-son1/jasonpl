package org.SJYPlugin.rPGBeta2.control.hpcontrol;

import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.entity.Player;

public class RecoverControl {

    private static final RecoverControl instance = new RecoverControl();

    public static RecoverControl getInstance() {
        return instance;
    }

    HPControl hpControl = HPControl.getInstance();
    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();

    public void Recovering(Player player, String recovertype, String valuetype, double Value) {
        double PreHealth = configUtilStat2.getHP(player);
        double MaxHealth = configUtilStat2.getMaxHP(player);
        if(recovertype.equalsIgnoreCase("CURRHEALTH")) {
            if (valuetype.equalsIgnoreCase("BASE")) {

                if (PreHealth + Value > MaxHealth) {
                    configUtilStat2.HPChange(player, MaxHealth, 0.0);

                } else {
                    configUtilStat2.HPChange(player, 0.0, Value);
                }
                hpControl.HealthSetPlayer(player);

            } else if (valuetype.equalsIgnoreCase("PERCENT")) {
                double ChangeValue = PreHealth * (Value / 100);

                if (PreHealth + ChangeValue > MaxHealth) {
                    configUtilStat2.HPChange(player, MaxHealth, 0.0);
                } else {
                    configUtilStat2.HPChange(player, 0.0, ChangeValue);
                }
                hpControl.HealthSetPlayer(player);
            }
        }
//        } else if(recovertype.equalsIgnoreCase("HEALTH")) {
//            if(valuetype.equalsIgnoreCase("BASE")) {
//
//                configUtilStat2.HPChange(player, 0.0, Value);
//                configUtilStat2.MaxHPChange(player, 0.0, Value);
//
//                hpControl.HealthSetPlayer(player);
//
//            } else if(valuetype.equalsIgnoreCase("PERCENT")) {
//
//            }
//        }
    }

}
