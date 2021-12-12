package de.ytendx.xac.checks.movement;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class NoWebCheck implements Listener {

    public NoWebCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){
        if(event.getFrom().getBlock().getType().equals(Material.WEB) && event.getTo().getBlock().getType().equals(Material.WEB)){
            if(event.getFrom().distance(event.getTo()) > 0.18){
                Notifyer.notify(event.getPlayer(), CheckType.NOWEB_A);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
        }
    }

}
