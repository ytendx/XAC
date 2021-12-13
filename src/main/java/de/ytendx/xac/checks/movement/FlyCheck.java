package de.ytendx.xac.checks.movement;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class FlyCheck implements Listener {

    private HashMap<Player, Long> timeInAir = new HashMap<Player, Long>();

    public FlyCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){
        if(!event.getPlayer().getAllowFlight()){

            if(XACMain.getServerWatchUtil().getTps() != 20){
                return;
            }

            if(event.getPlayer().getEntityId() == 100){
                return;
            }

            if(event.getPlayer().getVehicle() != null){
                return;
            }

            if(XACMain.getInstance().getDetectionAssistant().isReceivingDamage(event.getPlayer())){
                return;
            }

            if(event.getPlayer().isFlying()){
                Notifyer.notify(event.getPlayer(), CheckType.FLY_A);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
            if(event.getFrom().getY() < event.getTo().getY()){
                if(event.getTo().getY() - event.getFrom().getY() > 1.2){
                    Notifyer.notify(event.getPlayer(), CheckType.FLY_B);
                    if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
                }
            }else{
                if(event.getFrom().getY() - event.getTo().getY() > 3.5){
                    Notifyer.notify(event.getPlayer(), CheckType.FLY_C);
                    if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
                }
            }

            if(event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)
            && event.getPlayer().getLocation().getBlock().getType().equals(Material.AIR)
                    && !isNearGround(event.getPlayer().getLocation())
                    && event.getPlayer().getLocation().subtract(1, 0, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(-1, 0, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(0, 0, 1).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(0, 0, -1).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(1, 1, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(-1, 1, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(0, 1, 1).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(0, 1, -1).getBlock().getType().equals(Material.AIR)){
                if(event.getFrom().getY() <= event.getTo().getY()){
                    if(!timeInAir.containsKey(event.getPlayer())){
                        timeInAir.put(event.getPlayer(), System.currentTimeMillis());
                    }else{
                        if(System.currentTimeMillis() - timeInAir.get(event.getPlayer()) > 1500){
                            Notifyer.notify(event.getPlayer(), CheckType.FLY_D);
                            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
                        }
                    }
                }
            }else{
                if(timeInAir.containsKey(event.getPlayer())){
                    timeInAir.remove(event.getPlayer());
                }
            }

            if(event.getFrom().getY() - event.getTo().getY() == 0.125D
            && event.getTo().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)){
                Notifyer.notify(event.getPlayer(), CheckType.FLY_E);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }

            if(event.getPlayer().getFallDistance() == 0.0F
            && event.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)
            && event.getTo().toVector().distance(event.getFrom().toVector()) > 0.61D
            && !event.getPlayer().isOnGround() && XACMain.getInstance().getServerVersion() == 8){
                Notifyer.notify(event.getPlayer(), CheckType.FLY_F);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }
        }
    }

    public boolean isNearGround(Location location){
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand){
            for (double z = -expand; z <= expand; z += expand){
                if(location.clone().add(x, -0.5001, z).getBlock().getType() != Material.AIR){
                    return true;
                }
            }
        }
        return false;
    }

}
