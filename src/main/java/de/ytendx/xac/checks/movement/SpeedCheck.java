package de.ytendx.xac.checks.movement;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import de.ytendx.xac.utils.MathUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import java.util.Locale;

public class SpeedCheck implements Listener {

    public SpeedCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){
        if(event.getPlayer().getAllowFlight()){
            return;
        }

        if(XACMain.getServerWatchUtil().getTps() != 20){
            return;
        }

        if(XACMain.getInstance().getDetectionAssistant().isReceivingDamage(event.getPlayer())){
            return;
        }
        if(event.getFrom().clone().subtract(0, 1, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("slime")
        || event.getTo().clone().subtract(0, 1, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("slime")
                || event.getFrom().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("slime")
                || event.getTo().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("slime")){
            return;
        }

        if(event.getFrom().clone().subtract(0, 1, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("ice")){
            return;
        }

        double vectorDistance = event.getTo().toVector().setY(0.0D).distance(event.getFrom().toVector().setY(0.0D));

        if(event.getTo().getY() == event.getFrom().getY()){
            if(event.getFrom().distance(event.getTo()) > 0.475){
                Notifyer.notify(event.getPlayer(), CheckType.SPEED_A);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
        }

        if(event.getPlayer().getFoodLevel() < 8){
            if(event.getTo().getY() == event.getFrom().getY()){
                if(event.getFrom().distance(event.getTo()) > 0.45){
                    Notifyer.notify(event.getPlayer(), CheckType.SPEED_B);
                    if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
                }
            }
        }

        /*if(event.getPlayer().getFoodLevel() < 6){
            if(event.getFrom().distance(event.getTo()) > 0.7){
                Notifyer.notify(event.getPlayer(), "Speed/NoSlow", "C");
            }
        }*/

        if(MathUtil.getAmount(event.getTo().getY() - event.getFrom().getY()) > 1.2){
            Notifyer.notify(event.getPlayer(), CheckType.SPEED_C);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        if(vectorDistance >= 0.616D && event.getFrom().getY() < event.getTo().getY()
        && !event.getPlayer().getLocation().getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("stair")
        && !event.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("stair")){
            Notifyer.notify(event.getPlayer(), CheckType.SPEED_D);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        Player player = event.getPlayer();
        Vector velocity = player.getVelocity();
        // Check if the player is moving "up" baust, ankust, drauf läufst


    }

}
