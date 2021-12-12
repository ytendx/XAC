package de.ytendx.xac.checks.combat;

import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class VelocityCheck implements Listener {

    private Plugin plugin;

    public VelocityCheck(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handleDamage(EntityDamageEvent event){
        if(!event.getEntityType().equals(EntityType.PLAYER) || !event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
            return;
        }
        Location from = event.getEntity().getLocation().clone();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {

            Location to = event.getEntity().getLocation().clone();

            if(from.subtract(1, 0, 0).getBlock().getType().equals(Material.AIR)
                    && from.subtract(-1, 0, 0).getBlock().getType().equals(Material.AIR)
                    && from.subtract(0, 0, 1).getBlock().getType().equals(Material.AIR)
                    && from.subtract(0, 0, -1).getBlock().getType().equals(Material.AIR)
                    && from.subtract(1, -1, 0).getBlock().getType().equals(Material.AIR)
                    && from.subtract(-1, -1, 0).getBlock().getType().equals(Material.AIR)
                    && from.subtract(0, -1, 1).getBlock().getType().equals(Material.AIR)
                    && from.subtract(0, -1, -1).getBlock().getType().equals(Material.AIR)){
                if (from.distance(to) <=0.01) {
                    Notifyer.notify(((Player) event.getEntity()), CheckType.VELOCITY_A);
                }

                if(from.getX() == to.getX() || from.getZ() == to.getZ()){
                    Notifyer.notify(((Player) event.getEntity()), CheckType.VELOCITY_B);
                }

                if(
                        (from.getZ() > to.getZ() ? from.getZ() - to.getZ() : to.getZ() - from.getZ()) < 0.01
                                && (from.getX() > to.getX() ? from.getX() - to.getX() : to.getX() - from.getX()) < 0.01
                ){
                    Notifyer.notify(((Player) event.getEntity()), CheckType.VELOCITY_C);
                }
            }

        }, 10);
    }

}
