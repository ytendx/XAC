package de.ytendx.xac.checks.player;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class IrregularBuildingCheck implements Listener {

    public IrregularBuildingCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void handlePlace(BlockPlaceEvent event){
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }

        if(event.getPlayer().getEyeLocation().distance(event.getBlock().getLocation()) >= 8){
            Notifyer.notify(event.getPlayer(), CheckType.IRREGULAR_BUILDING_A);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event){
        /*Block targetedBlock = event.getPlayer().getTargetBlock((HashSet<Byte>) null, 3);
        if(targetedBlock.getTypeId() != event.getBlock().getTypeId()){
            event.setCancelled(true);
            Notifyer.notify(event.getPlayer(), CheckType.IRREGULAR_BUILDING_B);
        }*/
    }

}
