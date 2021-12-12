package de.ytendx.xac.checks.player;

import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.Plugin;

public class RegenCheck implements Listener {

    public RegenCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handleRegain(EntityRegainHealthEvent event){
        if(!event.getEntity().getType().equals(EntityType.PLAYER)){
            return;
        }
        Player player = (Player) event.getEntity();

        if(event.getAmount() > 2){
            Notifyer.notify(player, CheckType.REGEN_A);
        }

    }

}
