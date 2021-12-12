package de.ytendx.xac.checks.combat;

import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import de.ytendx.xac.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class KillAuraCheck implements Listener {

    private HashMap<Player, HitContainer> lastHitContainer = new HashMap<>();
    private HashMap<Player, Integer> entityAttackedLastSecond = new HashMap<>();

    private HashMap<Player, Integer> hitTicks = new HashMap<>();
    private HashMap<Player, Double> lastDist = new HashMap<>();
    private HashMap<Player, Integer> vioL = new HashMap<>();

    public KillAuraCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            entityAttackedLastSecond = new HashMap<>();
        }, 0, 20);
    }

    @EventHandler
    public void handleHit(EntityDamageByEntityEvent event){

        if(!(event.getDamager() instanceof Player)){
            return;
        }

        Player player = (Player) event.getDamager();

        if(!hitTicks.containsKey(player)) hitTicks.put(player, 0); else hitTicks.replace(player, 0);

        if((Math.round(event.getDamager().getLocation().getY()) == Math.round(event.getEntity().getLocation().getY())
        || Math.round(event.getDamager().getLocation().getY() + 1) == Math.round(event.getEntity().getLocation().getY())
        || Math.round(event.getDamager().getLocation().getY()) == Math.round(event.getEntity().getLocation().getY() + 1))
                && !(Math.round(event.getDamager().getLocation().getZ()) == Math.round(event.getEntity().getLocation().getZ()))
        && MathUtil.getAmount(event.getDamager().getLocation().getPitch()) > 75
        && event.getDamager() instanceof Player){
            Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_A);
            event.setCancelled(true);
        }

        if((Math.round(event.getDamager().getLocation().getY()) == Math.round(event.getEntity().getLocation().getY())
                || Math.round(event.getDamager().getLocation().getY() + 1) == Math.round(event.getEntity().getLocation().getY())
                || Math.round(event.getDamager().getLocation().getY()) == Math.round(event.getEntity().getLocation().getY() + 1))
                && !(Math.round(event.getDamager().getLocation().getX()) == Math.round(event.getEntity().getLocation().getX()))
                && MathUtil.getAmount(event.getDamager().getLocation().getPitch()) < -42.5
                && event.getDamager() instanceof Player){
            Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_B);
            event.setCancelled(true);
        }

        if(Math.round(((Player) event.getDamager()).getEyeLocation().getY())-1 > Math.round(event.getEntity().getLocation().getY())
        && player.getEyeLocation().getPitch() < 0){
            Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_C);
        }

        if(Math.round(((Player) event.getDamager()).getEyeLocation().getY())+1 < Math.round(event.getEntity().getLocation().getY())
                && player.getEyeLocation().getPitch() > 60){
            Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_C);
        }

        if(player.getEyeLocation().getYaw() < 100 && player.getEyeLocation().getYaw() > -100
        && event.getEntity().getLocation().getZ()-event.getEntity().getLocation().getZ() > 3
        //&& Math.round(event.getEntity().getLocation().getX()) == Math.round(event.getDamager().getLocation().getX())
        ){
            Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_D);
        }

        if(player.getEyeLocation().getYaw() > 100 && player.getEyeLocation().getYaw() < -100
                && event.getEntity().getLocation().getZ()-event.getEntity().getLocation().getZ() < 3
                //&& Math.round(event.getEntity().getLocation().getX()) == Math.round(event.getDamager().getLocation().getX())
        ){
            Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_D);
        }

        /*if(event.getDamager().getLocation().getYaw() > 50
                && event.getEntity().getLocation().getX()-event.getEntity().getLocation().getX() > 3){
            Notifyer.notify((Player) event.getDamager(), "KillAura", "E");
        }

        if(event.getDamager().getLocation().getYaw() < -50
                && event.getEntity().getLocation().getX()-event.getEntity().getLocation().getX() < 3){
            Notifyer.notify((Player) event.getDamager(), "KillAura", "E");
        }*/

        // 25

        if(Math.round(event.getEntity().getLocation().getX()) == Math.round(event.getDamager().getLocation().getX())
                && Math.round(event.getEntity().getLocation().getZ()) == Math.round(event.getDamager().getLocation().getZ())
        && player.getEyeLocation().getPitch() > 20
        && event.getDamager().getLocation().getY()+1 < event.getEntity().getLocation().getY()){
            Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_E);
        }

        // <60

        if(Math.round(event.getEntity().getLocation().getX()) == Math.round(event.getDamager().getLocation().getX())
                && Math.round(event.getEntity().getLocation().getZ()) == Math.round(event.getDamager().getLocation().getZ())
                && player.getEyeLocation().getPitch() < 60
                && event.getDamager().getLocation().getY() > event.getEntity().getLocation().getY()+1){
            Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_E);
        }

        if(((Player) event.getDamager()).getEyeLocation().getYaw() < -110 && ((Player) event.getDamager()).getEyeLocation().getYaw() > 110){
            if(event.getEntity().getLocation().getZ()-1.5 > event.getDamager().getLocation().getZ()){
                Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_F);
            }
        }

        if(((Player) event.getDamager()).getEyeLocation().getYaw() > -70 && ((Player) event.getDamager()).getEyeLocation().getYaw() < 70){
            if(event.getEntity().getLocation().getZ()+1.5 < event.getDamager().getLocation().getZ()){
                Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_F);
            }
        }

        if(((Player) event.getDamager()).getEyeLocation().getYaw() > 20 && ((Player) event.getDamager()).getEyeLocation().getYaw() < 160){
            if(event.getEntity().getLocation().getX()-1.5 > event.getDamager().getLocation().getX()){
                Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_F);
            }
        }

        if(((Player) event.getDamager()).getEyeLocation().getYaw() < -15 && ((Player) event.getDamager()).getEyeLocation().getYaw() > -160){
            if(event.getEntity().getLocation().getX()+1.5 < event.getDamager().getLocation().getX()){
                Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_F);
            }
        }

        if(lastHitContainer.containsKey((Player) event.getDamager())){
            HitContainer hitContainer = lastHitContainer.get((Player) event.getDamager());
            if(System.currentTimeMillis() - hitContainer.time < 100){
                if(!hitContainer.target.equals(event.getEntity())){
                    Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_G);
                }
            }
            if(!hitContainer.target.equals(event.getEntity())){
                if(!entityAttackedLastSecond.containsKey((Player) event.getDamager())){
                    entityAttackedLastSecond.put((Player) event.getDamager(), 1);
                }else{
                    if(entityAttackedLastSecond.get((Player) event.getDamager()) > 3){
                        Notifyer.notify((Player) event.getDamager(), CheckType.KILLAURA_H);
                    }
                    entityAttackedLastSecond.replace((Player) event.getDamager(), entityAttackedLastSecond.get((Player) event.getDamager())+1);
                }
            }
            lastHitContainer.replace((Player) event.getDamager(), new HitContainer(event.getEntity(), System.currentTimeMillis()));
        }else{
            lastHitContainer.put((Player) event.getDamager(), new HitContainer(event.getEntity(), System.currentTimeMillis()));
        }

        if(event.getDamager().getLocation().getY()-1 >= event.getEntity().getLocation().getY()
        && player.getEyeLocation().getPitch() < 0){
            Notifyer.notify(player, CheckType.KILLAURA_I);
        }


        /*event.getDamager().sendMessage("______________________________________");
        event.getDamager().sendMessage("Yaw-D: " + event.getDamager().getLocation().getYaw());
        event.getDamager().sendMessage("Yaw-E: " + event.getEntity().getLocation().getYaw());
        event.getDamager().sendMessage("Pitch-D: " + event.getDamager().getLocation().getPitch());
        event.getDamager().sendMessage("Pitch-E: " + event.getEntity().getLocation().getPitch());*/
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){

        double xzDist = XZDif(event.getFrom(), event.getTo());

        if(!hitTicks.containsKey(event.getPlayer())){
            return;
        }

        if(!lastDist.containsKey(event.getPlayer())){
            lastDist.put(event.getPlayer(), xzDist);
            return;
        }

        if(event.getPlayer().isSprinting() && hitTicks.get(event.getPlayer()) <= 2){
            double accel = Math.abs(xzDist - lastDist.get(event.getPlayer()));
            if(accel < 0.027){
                if(!vioL.containsKey(event.getPlayer())){
                    vioL.put(event.getPlayer(), 1);
                }else{
                    if(vioL.get(event.getPlayer()) >= 4){
                        Notifyer.notify(event.getPlayer(), CheckType.KILLAURA_J);
                    }
                    vioL.replace(event.getPlayer(), vioL.get(event.getPlayer())+1);
                }
            }else{
                vioL.remove(event.getPlayer());
            }
            hitTicks.remove(event.getPlayer());
        }

        lastDist.replace(event.getPlayer(), xzDist);

    }

    private double difference(double f1, double f2){
        return (f1 > f2 ? f1 - f2 : f2 - f1);
    }

    private double XZDif(Location loc1, Location loc2){
        return difference(loc1.getX(), loc2.getX()) + difference(loc1.getZ(), loc2.getZ());
    }

    public static class HitContainer{
        private Entity target;
        private long time;

        public HitContainer(Entity target, long time) {
            this.target = target;
            this.time = time;
        }

        public Entity getTarget() {
            return target;
        }

        public long getTime() {
            return time;
        }
    }

}
