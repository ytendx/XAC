package de.ytendx.xac.checks.combat;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class RangeCheck implements Listener {

    public RangeCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handleHit(EntityDamageByEntityEvent event){
        if(event.getDamager().getLocation().distance(event.getEntity().getLocation()) > 4.5
                && event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                && event.getDamager() instanceof Player){
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.setDamage(0);
            Notifyer.notify(((Player) event.getDamager()), CheckType.RANGE_A);
        }

        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player player = (Player) event.getDamager();
            Player armerJunge = (Player) event.getEntity();

            if(player.getEyeLocation().distance(armerJunge.getEyeLocation()) > 4.5){
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.setDamage(0);
                Notifyer.notify(((Player) event.getDamager()), CheckType.RANGE_B);
            }
        }

        if(XZDif(event.getDamager().getLocation(), event.getEntity().getLocation()) > 6 && event.getDamager() instanceof Player
                && !((Player) event.getDamager()).getGameMode().equals(GameMode.CREATIVE)){
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.setDamage(0);
            Notifyer.notify(((Player) event.getDamager()), CheckType.RANGE_C);
        }
    }

    private double difference(double f1, double f2){
        return (f1 > f2 ? f1 - f2 : f2 - f1);
    }

    private double XZDif(Location loc1, Location loc2){
        return difference(loc1.getX(), loc2.getX()) + difference(loc1.getZ(), loc2.getZ());
    }

}
