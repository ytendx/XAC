package de.ytendx.xac.checks.movement;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class JesusCheck implements Listener {

    private ConcurrentHashMap<Player, Integer> onWaterCallCount;
    private ConcurrentHashMap<Player, Integer> inWaterYCount;

    public JesusCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        onWaterCallCount = new ConcurrentHashMap<>();
        inWaterYCount = new ConcurrentHashMap<>();
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){

        if(event.getFrom().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("snow")
        || event.getFrom().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("lilly")
        || event.getFrom().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("slab")
        || event.getFrom().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("carpet")){
            return;
        }

        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE) || event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)){
            return;
        }

        if(event.getFrom().getY() < event.getTo().getY() && event.getTo().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.getMaterial("WATER"))
        && !event.getTo().clone().getBlock().getType().equals(Material.getMaterial("WATER")) && XACMain.getInstance().getServerVersion() < 13){
            Notifyer.notify(event.getPlayer(), CheckType.JESUS_A);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        if(Math.round(event.getFrom().getY()) == Math.round(event.getTo().getY()) && XACMain.getInstance().getServerVersion() < 13
        && event.getTo().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.getMaterial("WATER"))){
            Notifyer.notify(event.getPlayer(), CheckType.JESUS_B);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        if(!event.getPlayer().getEyeLocation().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
                && event.getPlayer().getEyeLocation().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
                && event.getTo().distance(event.getFrom()) > 0.275){
            Notifyer.notify(event.getPlayer(), CheckType.JESUS_C);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        if(!event.getPlayer().getEyeLocation().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
                && !event.getPlayer().getLocation().clone().subtract(0, -0.2, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
                && event.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
        && (!event.getPlayer().getLocation().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("slab") || event.getPlayer().getLocation().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("stair"))
        && !event.getPlayer().isOnGround()){
            if(!onWaterCallCount.containsKey(event.getPlayer())){
                onWaterCallCount.put(event.getPlayer(), 1);
            }else{
                if(onWaterCallCount.get(event.getPlayer()) > 10){
                    Notifyer.notify(event.getPlayer(), CheckType.JESUS_D);
                    if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
                }
                onWaterCallCount.replace(event.getPlayer(), onWaterCallCount.get(event.getPlayer())+1);
            }
        }else{
            if(onWaterCallCount.containsKey(event.getPlayer())) onWaterCallCount.remove(event.getPlayer());
        }

        /*

        TODO Fix -> Beim Auftauchen false flag

        if(!event.getPlayer().getEyeLocation().clone().subtract(0, -0.5, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
        && event.getPlayer().getLocation().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
        && (
                event.getTo().getY() > event.getFrom().getY() ? event.getTo().getY() - event.getFrom().getY() : event.getFrom().getY() - event.getTo().getY()
                ) < 0.2){
            if(!inWaterYCount.containsKey(event.getPlayer())){
                inWaterYCount.put(event.getPlayer(), 1);
            }else{
                if(inWaterYCount.get(event.getPlayer()) > 5){
                    Notifyer.notify(event.getPlayer(), CheckType.JESUS_E);
                }
                inWaterYCount.replace(event.getPlayer(), inWaterYCount.get(event.getPlayer())+1);
            }
        }else{
            if(inWaterYCount.containsKey(event.getPlayer())) inWaterYCount.remove(event.getPlayer());
        }

         */

        /*if(event.getTo().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
                && event.getFrom().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")
                && Math.round(event.getFrom().getY()) == Math.round(event.getTo().getY())
        && event.getFrom().distance(event.getTo()) > 0.1
        && !event.getTo().clone().subtract(0, -1, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("water")){
            Notifyer.notify(event.getPlayer(), "Jesus", "F");
        }*/
    }

}
