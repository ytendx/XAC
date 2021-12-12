package de.ytendx.xac.notify.player;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class NotifyPlayer {

    private Player player;
    private HashMap<CheckType, Integer> combatTypes;
    private int violations;

    public Player getPlayer() {
        return player;
    }

    public HashMap<CheckType, Integer> getCombatTypes() {
        return combatTypes;
    }

    public NotifyPlayer(Player player) {
        this.player = player;
        this.combatTypes = new HashMap<>();
    }

    public void handleViolation(CheckType checkType){
        violations++;
        if(!combatTypes.containsKey(checkType)){
            combatTypes.put(checkType, 1);
        }else{
            combatTypes.replace(checkType, combatTypes.get(checkType)+1);
        }

        int currentCheckViolations = combatTypes.get(checkType);
        notify(XACMain.PREFIX + "The player §c" + player.getName() + " §7failed §3" + checkType.getName()
                + " §8(§bVL: §3" + currentCheckViolations + "§7/§3" + checkType.getMaxVL() +
                (checkType.isExperimental() ? " §8[§cE§8]" : "") + "§8)");
        if(currentCheckViolations > checkType.getMaxVL()) {
            combatTypes.remove(checkType);
            punish();
        }
    }

    public void punish(){
        if(XACMain.getInstance().getXACConfig().isKickCheater()) Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(), "kick " + player.getName() + " " + XACMain.getInstance().getXACConfig().getKickMessage()
        );
    }

    public static void notify(String message){
        if(!XACMain.getInstance().getXACConfig().isTestServer()){
            Bukkit.getOnlinePlayers().stream().filter(player1 -> player1.hasPermission(XACMain.getInstance().getXACConfig().getNotifyPermission()))
                    .forEach(player1 -> player1.sendMessage(message));
            if(XACMain.getInstance().getXACConfig().isLogViolationsInConsole()) Bukkit.getConsoleSender().sendMessage(message);
        }else{
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendMessage(message));
            if(XACMain.getInstance().getXACConfig().isLogViolationsInConsole()) Bukkit.getConsoleSender().sendMessage(message);
        }
    }
}
