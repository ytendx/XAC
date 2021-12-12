package de.ytendx.xac.checks.combat;

import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import de.ytendx.xac.utils.VectorUtil;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AimCheck implements Listener {

    private HashMap<Player, Integer> notVectorPacket;
    private HashMap<Player, List<AimContainer>> aimings;
    private HashMap<Player, Integer> invalidVerboseCount;

    public AimCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.notVectorPacket = new HashMap<>();
        this.aimings = new HashMap<>();
        this.invalidVerboseCount = new HashMap<>();
    }

    @EventHandler
    public void handleHit(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)){
            return;
        }

        Player player = (Player) event.getDamager();

        // AIM A

        if(VectorUtil.getDot((Player) event.getDamager(), event.getEntity()) > 0.63D){
            notVectorPacket.put((Player) event.getDamager(), 1);
        }

        if(!VectorUtil.getLookingAt((Player) event.getDamager(), (LivingEntity) event.getEntity())){
            if(notVectorPacket.containsKey(((Player) event.getDamager()).getPlayer())){
                if(notVectorPacket.get(((Player) event.getDamager()).getPlayer()) > 2){
                    Notifyer.notify(((Player) event.getDamager()).getPlayer(), CheckType.AIM_A);
                }
                notVectorPacket.replace(((Player) event.getDamager()).getPlayer(), notVectorPacket.get(((Player) event.getDamager()).getPlayer())+1);
            }else{
                notVectorPacket.put(((Player) event.getDamager()).getPlayer(), 1);
            }
        }else{
            if(notVectorPacket.containsKey(((Player) event.getDamager()).getPlayer())){
                notVectorPacket.remove(((Player) event.getDamager()).getPlayer());
            }
        }

        // AIM B

        if(!aimings.containsKey(player)){
            aimings.put(player, new CopyOnWriteArrayList<>());
            aimings.get(player).add(new AimContainer(player.getEyeLocation(), System.currentTimeMillis()));
        }else{
            List<AimContainer> list = aimings.get(player);

            if(list.size() > 5){
                float lastYaw = 0;
                float lastPitch = 0;

                int sameAmount = 0;

                float lastYawDif = 0;
                float lastPitchDif = 0;
                for (AimContainer container : list){
                    if(lastPitch == 0 && lastYaw == 0){
                        lastYaw = container.yaw;
                        lastPitch = container.pitch;
                        continue;
                    }
                    float yawDifference = difference(lastYaw, container.yaw);
                    float pitchDifference = difference(lastPitch, container.pitch);
                    if(lastPitchDif == 0 && lastYawDif == 0){
                        lastPitchDif = pitchDifference;
                        lastYawDif = yawDifference;
                        continue;
                    }
                    if((int) lastPitchDif ==  (int) pitchDifference || lastYawDif == yawDifference) {
                        sameAmount++;
                    }

                    float deltaPitch = Math.abs(container.pitch - lastPitch);
                    float deltaYaw = Math.abs(container.yaw - lastYaw);

                    if(
                            (deltaYaw > 1.1 && deltaPitch == 0 && container.pitch < 89 && container.yaw != 0 && container.pitch != 0)
                            || (deltaYaw % .5 == 0 && deltaYaw != 0 && container.pitch != 0)
                            || (deltaYaw == container.yaw && container.pitch < 90 && deltaYaw > .03 && deltaYaw != 0)
                    ){
                        if(!invalidVerboseCount.containsKey(player)) invalidVerboseCount.put(player, 1);
                        else invalidVerboseCount.replace(player, invalidVerboseCount.get(player)+1);
                        Notifyer.notify(player, CheckType.AIM_C);
                    }else{
                        if(invalidVerboseCount.containsKey(player)) if(invalidVerboseCount.get(player) > 0)
                            invalidVerboseCount.replace(player, invalidVerboseCount.get(player)-1);
                    }

                    lastPitchDif = pitchDifference;
                    lastYawDif = yawDifference;
                    lastYaw = container.yaw;
                    lastPitch = container.pitch;
                }
                if(sameAmount > 0){
                    Notifyer.notify(player, CheckType.AIM_B);
                    aimings.remove(player);
                }
            }

            if(aimings.containsKey(player)) aimings.get(player).add(new AimContainer(player.getEyeLocation(), System.currentTimeMillis()));
        }

    }

    private float difference(float f1, float f2){
        return (f1 > f2 ? f1 - f2 : f2 - f1);
    }

    public static class AimContainer{

        private double x, y, z;
        private float yaw, pitch;
        private long time;

        public AimContainer(Location location, long time) {
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.yaw = location.getYaw();
            this.pitch = location.getPitch();
            this.time = time;
        }
    }

}
