package de.ytendx.xac.notify;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.player.NotifyPlayer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Notifyer {

    public static List<NotifyPlayer> notifyPlayers = new CopyOnWriteArrayList<>();

    public static void notify(Player hacker, CheckType checkType){
        for(NotifyPlayer notifyPlayer : notifyPlayers){
            if(notifyPlayer.getPlayer().getName().equals(hacker.getName())){
                notifyPlayer.handleViolation(checkType);
                return;
            }
        }
        NotifyPlayer notifyPlayer = new NotifyPlayer(hacker);
        notifyPlayer.handleViolation(checkType);
        notifyPlayers.add(notifyPlayer);
    }

    public static void sendWarning(String message){
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.hasPermission("xac.notify")){
                player.sendMessage(XACMain.PREFIX + "§c" + message);
            }
        });
    }

}
