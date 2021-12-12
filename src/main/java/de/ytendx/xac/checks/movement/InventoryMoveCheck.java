package de.ytendx.xac.checks.movement;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class InventoryMoveCheck implements Listener {

    private ArrayList<Player> inventoryOpen;

    public InventoryMoveCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.inventoryOpen = new ArrayList<>();
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){
        if(inventoryOpen.contains(event.getPlayer())){
            Notifyer.notify(event.getPlayer(), CheckType.INVENTORY_MOVE_A);
            if(!XACMain.getPlugin(XACMain.class).isSilent())event.getPlayer().teleport(event.getFrom());
        }
    }

    @EventHandler
    public void handleOpen(InventoryOpenEvent event){
        if(!inventoryOpen.contains((Player) event.getPlayer())){
            inventoryOpen.add((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void handleClose(InventoryCloseEvent event){
        if(inventoryOpen.contains((Player) event.getPlayer())){
            inventoryOpen.remove((Player) event.getPlayer());
        }
    }

}
