package de.ytendx.xac.notify.player;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.type.CheckType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashMap;

@Getter
public class NotifyPlayer {

    private Player player;
    @Setter
    private HashMap<CheckType, Integer> combatTypes;
    @Setter
    private int violations;

    public Player getPlayer() {
        return player;
    }

    public NotifyPlayer(Player player) {
        this.player = player;
        this.combatTypes = new HashMap<>();
    }

    public void handleViolation(CheckType checkType){

        if(checkType.isExperimental() && XACMain.getInstance().getXACConfig().isIgnoreExperimentalFlags()){
            return;
        }

        violations++;
        if(!combatTypes.containsKey(checkType)){
            combatTypes.put(checkType, 1);
        }else{
            combatTypes.replace(checkType, combatTypes.get(checkType)+1);
        }

        if(violations > 50){
            notify(XACMain.PREFIX + "The player §c" + player.getName() + " §7failed hacking §e50 §7Times and got kicked.");
            punish(false);
            violations = 0;
            return;
        }

        int currentCheckViolations = combatTypes.get(checkType);
        notify(XACMain.PREFIX + "The player §c" + player.getName() + " §7failed §3" + checkType.getName()
                + " §8(§bVL: §3" + currentCheckViolations + "§7/§3" + checkType.getMaxVL() +
                (checkType.isExperimental() ? " §8[§cE§8]" : "") + "§8)");
        if(currentCheckViolations > checkType.getMaxVL()) {
            combatTypes.remove(checkType);
            punish(true);
        }
    }

    public void punish(boolean anounce){
        if(XACMain.getInstance().getXACConfig().isKickCheater()) Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(), "kick " + player.getName() + " " + XACMain.getInstance().getXACConfig().getKickMessage()
        );
        if(anounce){
            notify(XACMain.PREFIX + "The player §c" + player.getName() + " §7was §ekicked §7by the anti-cheat.");
        }
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
