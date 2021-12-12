package de.ytendx.xac.checks.player;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.Notifyer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.Plugin;

public class ChestStealerCheck implements Listener {

    public ChestStealerCheck(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCursor() == null){
            if(!XACMain.getPlugin(XACMain.class).isSilent()) e.setCancelled(true);
            Notifyer.notify((Player) e.getWhoClicked(), CheckType.CHEST_STEALER_A);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(!e.getPlayer().getItemInHand().equals(e.getItemDrop().getItemStack())){
            if(e.getPlayer().getOpenInventory() == null){
                if(!XACMain.getPlugin(XACMain.class).isSilent())e.setCancelled(true);
                Notifyer.notify(e.getPlayer(), CheckType.CHEST_STEALER_B);
            }
        }
    }

}
