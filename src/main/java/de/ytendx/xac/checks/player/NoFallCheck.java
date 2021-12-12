package de.ytendx.xac.checks.player;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class NoFallCheck implements Listener {

    public NoFallCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){
        // EMPTY YET
    }

    public boolean isNearGround(Location location){
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand){
            for (double z = -expand; z <= expand; z += expand){
                if(location.clone().add(x, -0.5001, z).getBlock().getType() != Material.AIR){
                    return true;
                }
            }
        }
        return false;
    }
}
