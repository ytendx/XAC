package de.ytendx.xac.checks.movement;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import de.ytendx.xac.utils.MathUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.Locale;

public class MotionCheck implements Listener {

    public MotionCheck(Plugin plugin) {
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

        if(event.getFrom().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.SLIME_BLOCK)){
            return;
        }

        if(event.getFrom().distance(event.getTo()) >= 1.7){
            if(event.getTo().getY() <= event.getFrom().getY()){
                Notifyer.notify(event.getPlayer(), CheckType.MOTION_A);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
        }

        if(MathUtil.getAmount(event.getTo().getY() - event.getFrom().getY()) > 1.2){
            Notifyer.notify(event.getPlayer(), CheckType.MOTION_B);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        if(!event.getFrom().clone().subtract(0, 1, 0).getBlock().getType().toString().toLowerCase(Locale.ROOT).contains("ice")){
            double xdistance = event.getFrom().getX() - event.getTo().getX();
            double zdistance = event.getFrom().getZ() - event.getTo().getZ();
            double xdistance2 = event.getTo().getX() - event.getFrom().getX();
            double zdistance2 = event.getTo().getZ() - event.getFrom().getZ();

            if(MathUtil.getAmount(xdistance + zdistance) >= 1){
                Notifyer.notify(event.getPlayer(), CheckType.MOTION_C);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
            if(MathUtil.getAmount(xdistance + zdistance2) >= 1){
                Notifyer.notify(event.getPlayer(), CheckType.MOTION_C);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
            if(MathUtil.getAmount(xdistance2 + zdistance) >= 1){
                Notifyer.notify(event.getPlayer(), CheckType.MOTION_C);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
            if(MathUtil.getAmount(xdistance2 + zdistance2) >= 1){
                Notifyer.notify(event.getPlayer(), CheckType.MOTION_C);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
            if(MathUtil.getAmount(xdistance) >= 0.65){
                Notifyer.notify(event.getPlayer(), CheckType.MOTION_D);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
            if(MathUtil.getAmount(zdistance) >= 0.65){
                Notifyer.notify(event.getPlayer(), CheckType.MOTION_D);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
        }

        if(event.getTo().getY() - event.getFrom().getY() >= 0.51){
            Notifyer.notify(event.getPlayer(), CheckType.MOTION_E);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        if(event.getTo().getY() > event.getFrom().getY()
        && event.getFrom().distance(event.getTo()) > 1.155D){
            Notifyer.notify(event.getPlayer(), CheckType.MOTION_F);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        if(!(event.getTo().getY() > event.getFrom().getY())
                && (event.getFrom().distance(event.getTo()) > 2.351D)){
            Notifyer.notify(event.getPlayer(), CheckType.MOTION_G);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }

        if(event.getPlayer().getFallDistance() == 0
                && event.getTo().toVector().distance(event.getFrom().toVector()) > 0.79D && !event.getPlayer().isOnGround()
                && event.getFrom().getY() <= event.getTo().getY()){
            Notifyer.notify(event.getPlayer(), CheckType.MOTION_H);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.setCancelled(true);
        }

    }

}
