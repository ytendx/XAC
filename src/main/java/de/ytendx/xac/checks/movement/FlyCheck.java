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
    private HashMap<Player, Location> lastLocation = new HashMap<>();
    private Plugin plugin;

    public FlyCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
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
            && !event.getPlayer().isOnGround()){
                Notifyer.notify(event.getPlayer(), CheckType.FLY_F);
                if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
            }

            /*if(event.getFrom().distance(event.getTo()) < 0.01 && event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(1, 0, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(-1, 0, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(0, 0, 1).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(0, 0, -1).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(1, 1, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(-1, 1, 0).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(0, 1, 1).getBlock().getType().equals(Material.AIR)
                    && event.getPlayer().getLocation().subtract(0, 1, -1).getBlock().getType().equals(Material.AIR)){
                Notifyer.notify(event.getPlayer(), "Fly", "E");
            }*/

            /*if(event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)
            && event.getPlayer().getLocation().subtract(0, 2, 0).getBlock().getType().equals(Material.AIR)
            && event.getPlayer().getLocation().subtract(0, 3, 0).getBlock().getType().equals(Material.AIR)
            && event.getPlayer().getLocation().subtract(0, 4, 0).getBlock().getType().equals(Material.AIR)
            && event.getPlayer().getLocation().getBlock().getType().equals(Material.AIR)){
                if(MathUtil.getAmount(event.getFrom().getX() - event.getTo().getX()) < 1
                && MathUtil.getAmount(event.getFrom().getZ() - event.getTo().getZ()) < 1
                && event.getPlayer().getLocation().subtract(1, 0, 0).getBlock().getType().equals(Material.AIR)
                        && event.getPlayer().getLocation().subtract(-1, 0, 0).getBlock().getType().equals(Material.AIR)
                        && event.getPlayer().getLocation().subtract(0, 0, 1).getBlock().getType().equals(Material.AIR)
                        && event.getPlayer().getLocation().subtract(0, 0, -1).getBlock().getType().equals(Material.AIR)){
                    Notifyer.notify(event.getPlayer(), "Fly", "F");
                    event.getPlayer().teleport(event.getFrom());
                }
            }*/
        }
    }

    /*public void startTypeESchedule(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, () -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                if(player.getAllowFlight()){
                    continue;
                }
                if (player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
                    if (player.getLocation().subtract(0, 2, 0).getBlock().getType().equals(Material.AIR) &&
                            player.getLocation().subtract(0, 3, 0).getBlock().getType().equals(Material.AIR) &&
                            player.getLocation().subtract(0, 4, 0).getBlock().getType().equals(Material.AIR) &&
                            player.getLocation().subtract(0, 5, 0).getBlock().getType().equals(Material.AIR) &&
                            player.getLocation().subtract(0, 6, 0).getBlock().getType().equals(Material.AIR) &&
                            player.getLocation().subtract(0, 7, 0).getBlock().getType().equals(Material.AIR) &&
                            player.getLocation().subtract(0, 8, 0).getBlock().getType().equals(Material.AIR)) {
                        lastLocation.put(player, player.getLocation().clone());
                        if (timeinair.containsKey(player)) {
                            long time = System.currentTimeMillis() - timeinair.get(player);
                            if (time >= 3000) {
                                Notifyer.notify(player, "Fly", "D");
                                player.teleport(lastLocation.get(player));
                                lastLocation.remove(player);
                                timeinair.remove(player);
                            }
                        } else {
                            timeinair.put(player, System.currentTimeMillis());
                        }
                    }
                }
            }
        }, 0,5);

    }*/

}
