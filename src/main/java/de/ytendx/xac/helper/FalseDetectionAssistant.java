package de.ytendx.xac.helper;

import de.ytendx.xac.XACMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class FalseDetectionAssistant implements Listener {

    private CopyOnWriteArrayList<Player> currentDamageReceivingTargets;

    public FalseDetectionAssistant() {
        this.currentDamageReceivingTargets = new CopyOnWriteArrayList<>();
        XACMain.getInstance().getServer().getPluginManager().registerEvents(this, XACMain.getInstance());
    }

    public boolean isReceivingDamage(Player player){
        return currentDamageReceivingTargets.contains(player);
    }

    public void receivingDamage(Player player){
        if(!currentDamageReceivingTargets.contains(player)) {
            currentDamageReceivingTargets.add(player);
            Bukkit.getScheduler().runTaskLaterAsynchronously(XACMain.getInstance(), () -> {
                currentDamageReceivingTargets.remove(player);
            }, 20*3);
        }
    }

    @EventHandler
    public void handleDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            receivingDamage((Player) event.getEntity());
        }
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){
        if(event.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("slime")){
            receivingDamage(event.getPlayer());
        }
        if(!event.getPlayer().getEyeLocation().clone().subtract(0, -1, 0).getBlock().getType().equals(Material.AIR)){
            receivingDamage(event.getPlayer());
        }
    }
}
