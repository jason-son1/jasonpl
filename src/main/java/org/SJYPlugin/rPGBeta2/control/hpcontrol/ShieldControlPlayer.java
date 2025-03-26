package org.SJYPlugin.rPGBeta2.control.hpcontrol;

import org.SJYPlugin.rPGBeta2.util.config.ConfigUtilStat2;
import org.bukkit.entity.Player;

public class ShieldControlPlayer {

    private static final ShieldControlPlayer instance = new ShieldControlPlayer();

    public static ShieldControlPlayer getInstance() {
        return instance;
    }

    ConfigUtilStat2 configUtilStat2 = ConfigUtilStat2.getInstance();

    public boolean ExistShield(Player offender) {
        if(configUtilStat2.getShilled(offender) > 0) {
            return true;
        } else {
            SetShieldZero(offender);
            return false;
        }
    }

    public double GetShield(Player offender) {
        if(ExistShield(offender)) {
            return configUtilStat2.getShilled(offender);
        } else {
            SetShieldZero(offender);
            return 0;
        }
    }

    public void UpShield(Player player, double value) {
        configUtilStat2.ShilledChange(player, (double) 0, 1*value);
    }

    public void DownShield(Player player, double value) {
        configUtilStat2.ShilledChange(player, (double) 0, -1*value);
        ExistShield(player);
    }

    public void SetShield(Player player, double value) {
        configUtilStat2.ShilledChange(player, value, (double) 0);
    }

    private void SetShieldZero(Player player) {
        configUtilStat2.ShilledChange(player, (double) 0, -1*configUtilStat2.getShilled(player));
    }




}
