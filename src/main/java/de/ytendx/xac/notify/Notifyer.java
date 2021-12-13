package de.ytendx.xac.notify;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.player.NotifyPlayer;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Notifyer {

    public static List<NotifyPlayer> notifyPlayers = new CopyOnWriteArrayList<>();

    public static void initialize(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(XACMain.getInstance(), () -> {
            AtomicInteger violationsRemoved = new AtomicInteger();
            notifyPlayers.forEach(notifyPlayer -> {
                if(!notifyPlayer.getPlayer().isOnline()){
                    notifyPlayers.remove(notifyPlayer);
                    return;
                }
                if(notifyPlayer.getViolations() > 0 || notifyPlayer.getCombatTypes().size() > 0){
                    violationsRemoved.addAndGet(notifyPlayer.getViolations());
                    notifyPlayer.setCombatTypes(new HashMap<>());
                    notifyPlayer.setViolations(0);
                }
            });
            sendWarning("§7The violations for all players were reseted and all offline players were removed from cache! §8(§fCNT: §b" + violationsRemoved.get() + "§8)");
        }, 0, 20*60*2);
    }

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
        if(!XACMain.getInstance().getXACConfig().isTestServer()){
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(player.hasPermission(XACMain.getInstance().getXACConfig().getNotifyPermission())){
                    player.sendMessage(XACMain.PREFIX + "§c" + message);
                }
            });
        }else{
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendMessage(XACMain.PREFIX + "§c" + message);
            });
        }
    }

}
