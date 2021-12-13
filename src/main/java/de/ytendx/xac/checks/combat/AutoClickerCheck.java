package de.ytendx.xac.checks.combat;

import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class AutoClickerCheck implements Listener {

    private ConcurrentHashMap<String, Integer> clicksPerSecond;
    private ConcurrentHashMap<String, Integer> lastClicksperSecond;
    private ConcurrentHashMap<String, Integer> last2ClicksperSecond;
    private ConcurrentHashMap<Player, Long> lastClick;
    private ConcurrentHashMap<Player, Long> lastLickDiff;
    private final int MAX_CPS = 20;

    public AutoClickerCheck(Plugin plugin) {
        this.clicksPerSecond = new ConcurrentHashMap<>();
        this.lastClicksperSecond = new ConcurrentHashMap<>();
        this.last2ClicksperSecond = new ConcurrentHashMap<>();
        this.lastClick = new ConcurrentHashMap<>();
        this.lastLickDiff = new ConcurrentHashMap<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {

            for(String name : clicksPerSecond.keySet()){
                if(clicksPerSecond.get(name) > this.MAX_CPS){
                    Notifyer.notify(Bukkit.getPlayer(name), CheckType.AUTO_CLICKER_A);
                    clicksPerSecond.remove(name);
                }else{
                    if(!lastClicksperSecond.containsKey(name)){
                        lastClicksperSecond.put(name, clicksPerSecond.get(name));
                        clicksPerSecond.remove(name);
                    }else{
                        if(!last2ClicksperSecond.containsKey(name)){
                            if(lastClicksperSecond.get(name) == clicksPerSecond.get(name) && clicksPerSecond.get(name) > 10){
                                Notifyer.notify(Bukkit.getPlayer(name), CheckType.AUTO_CLICKER_C);
                            }
                            last2ClicksperSecond.put(name, lastClicksperSecond.get(name));
                            lastClicksperSecond.replace(name, clicksPerSecond.get(name));
                            clicksPerSecond.remove(name);
                        }else{
                            if(clicksPerSecond.get(name) == lastClicksperSecond.get(name)
                                    && clicksPerSecond.get(name) == last2ClicksperSecond.get(name)
                                    && clicksPerSecond.get(name) > 5)
                            {
                                Notifyer.notify(Bukkit.getPlayer(name), CheckType.AUTO_CLICKER_B);
                            }
                            if(lastClicksperSecond.get(name) == clicksPerSecond.get(name) && clicksPerSecond.get(name) > 10){
                                Notifyer.notify(Bukkit.getPlayer(name), CheckType.AUTO_CLICKER_C);
                            }
                            last2ClicksperSecond.remove(name);
                            lastClicksperSecond.remove(name);
                            clicksPerSecond.remove(name);
                        }
                    }
                }
            }

        }, 0, 20);
    }

    @EventHandler
    public void handleDamage(EntityDamageByEntityEvent event){
        if(!event.getDamager().getType().equals(EntityType.PLAYER)){
            return;
        }
        if(!clicksPerSecond.containsKey(event.getDamager().getName())){
            clicksPerSecond.put(event.getDamager().getName(), 1);
        }else{
            clicksPerSecond.put(event.getDamager().getName(), clicksPerSecond.get(event.getDamager().getName())+1);
        }
        if(lastClick.containsKey((Player) event.getDamager())){
            long lastClickMili = lastClick.get((Player) event.getDamager());
            long currentDif = System.currentTimeMillis() - lastClickMili;

            if(currentDif < 1){
                Notifyer.notify((Player) event.getDamager(), CheckType.AUTO_CLICKER_D);
            }

            if(lastLickDiff.containsKey((Player) event.getDamager())){

                if(currentDif == lastLickDiff.get((Player) event.getDamager())){
                    Notifyer.notify((Player) event.getDamager(), CheckType.AUTO_CLICKER_E);
                }

                lastLickDiff.replace((Player) event.getDamager(), currentDif);
            }else{
                lastLickDiff.put((Player) event.getDamager(), System.currentTimeMillis() - lastClickMili);
            }

            lastClick.replace((Player) event.getDamager(), System.currentTimeMillis());
        }else{
            lastClick.put((Player) event.getDamager(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void handleInteract(PlayerInteractEvent event){
        if(event.getAction().equals(Action.LEFT_CLICK_AIR)){
            if(!clicksPerSecond.containsKey(event.getPlayer().getName())){
                clicksPerSecond.put(event.getPlayer().getName(), 1);
            }else{
                clicksPerSecond.put(event.getPlayer().getName(), clicksPerSecond.get(event.getPlayer().getName())+1);
            }
        }
    }

}
