package de.ytendx.xac.helper;

import de.ytendx.xac.XACMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class FalseDetectionAssistant implements Listener {

    private CopyOnWriteArrayList<Player> currentDamageReceivingTargets;

    public FalseDetectionAssistant() {
        this.currentDamageReceivingTargets = new CopyOnWriteArrayList<>();
        XACMain.getInstance().getServer().getPluginManager().registerEvents(this, XACMain.getInstance());
        Bukkit.getScheduler().runTaskTimerAsynchronously(XACMain.getInstance(), () -> {
            this.currentDamageReceivingTargets = new CopyOnWriteArrayList<>();
        }, 0, 20);
    }

    public boolean isReceivingDamage(Player player){
        return currentDamageReceivingTargets.contains(player);
    }

    public void receivingDamage(Player player){
        if(!currentDamageReceivingTargets.contains(player)) currentDamageReceivingTargets.add(player);
    }

    @EventHandler
    public void handleDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player && event.getCause().toString().toLowerCase(Locale.ROOT).contains("explosion")){
            receivingDamage((Player) event.getEntity());
        }
    }
}
