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
}
