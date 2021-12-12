package de.ytendx.xac.utils;

import com.google.common.collect.Lists;
import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ServerWatchUtil {

    private long startMillis;
    private int ticks;
    private int tps;

    public int getTps() {
        return tps;
    }

    public ServerWatchUtil(Plugin plugin) {
        this.ticks = 0;
        this.tps = -1;
        this.startMillis = 0;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if(startMillis + 1000 <= System.currentTimeMillis()) {
                tps = ticks;
                startMillis = System.currentTimeMillis();
                ticks = 0;
            }
            ticks++;
        }, 0, 1);
    }

}
