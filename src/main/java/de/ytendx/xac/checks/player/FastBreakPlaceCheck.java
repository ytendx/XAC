package de.ytendx.xac.checks.player;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Locale;

public class FastBreakPlaceCheck implements Listener {

    private HashMap<Player, Long> timeCache;

    public FastBreakPlaceCheck(Plugin plugin) {
        this.timeCache = new HashMap<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handleBreak(BlockBreakEvent event){
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if(!event.getBlock().getType().isSolid()
        || event.getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("leave")){
            return;
        }
        if(timeCache.containsKey(event.getPlayer())){

            final long breakTime = System.currentTimeMillis() - timeCache.get(event.getPlayer());

            if(breakTime < 1180)
            {
                Notifyer.notify(event.getPlayer(), CheckType.FAST_BREAK_A);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.setCancelled(true);
            }
            else timeCache.put(event.getPlayer(), System.currentTimeMillis());

        }else{
            timeCache.put(event.getPlayer(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void handlePlace(BlockPlaceEvent event){
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if(!event.getBlock().getType().isSolid()){
            return;
        }

    }

}
