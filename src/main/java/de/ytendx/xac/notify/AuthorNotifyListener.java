package de.ytendx.xac.notify;

import de.ytendx.xac.XACMain;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AuthorNotifyListener implements Listener {

    public AuthorNotifyListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, XACMain.getInstance());
    }

    @EventHandler
    public void handleJoin(PlayerJoinEvent event){
        if(event.getPlayer().getName().equals("ytendx") || event.getPlayer().getUniqueId().toString().equals("859c974c-2e32-4599-ac2e-05c7f06aead8")){
            event.getPlayer().sendMessage(XACMain.PREFIX + "§cThe server is using §4XAntiCheat §cby yourself.");
        }
    }

}
